package bi.baiqiu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.FansDailyBean;
import bi.baiqiu.pojo.User;
import bi.baiqiu.service.impl.FansDailyServiceImpl;
import bi.baiqiu.utils.DateUtils;
import bi.baiqiu.utils.ImportExcelUtils;
import bi.baiqiu.utils.UtilTool;

/**
 * 粉丝日报
 * 
 * @author Administrator
 *-
 */
@Controller
@RequestMapping("fansDaily")
public class FansDailyController extends BaseController {
	@Autowired
	private FansDailyServiceImpl fansDailyServiceImpl;

	/**
	 * 分页查询
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryFansDailyByPage.do")
	public void queryByPage(FansDailyBean bean, Integer pageNum, Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		User user=getLoginUser();
		try {
			response.setCharacterEncoding("utf-8");
			bean.setShopId(user.getShopId());
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page<FansDailyBean> page = fansDailyServiceImpl.queryByPage(bean, pageNum, pageSize);
			String json = "{\"total\":" + page.getTotal() + ",\"rows\":" + gson.toJson(page.getResult()) + "}";
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, systemErroy);
		}
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "updateFansDaily.do")
	public void updateFansDaily(FansDailyBean bean, HttpServletRequest request, HttpServletResponse response) {
		try {
			WriteObject(response, saveSuccess + fansDailyServiceImpl.updateFansDaliy(bean));
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response,savesErroy);
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteFansDailyByBatch.do")
	public void deleteFansDailyByBatch(@RequestParam(value = "idArray[]") int[] idArray, HttpServletRequest request,
			HttpServletResponse response) {
		User user = getLoginUser();
		FansDailyBean bean = new FansDailyBean();
		bean.setIdArray(idArray);
		// 不可删除两月前的数据
		bean.setDeleteHistory(user.isHasDeleteHistoryAuthority());
		try {
			int a = fansDailyServiceImpl.deleteFansDaliyByBatch(bean);
			WriteObject(response, deleteSuccess + a);
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, deleteErroy);
		}
	}

	/**
	 * 粉丝日报
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "uploadFansDaily.do", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadFansDaily(HttpServletRequest request, HttpServletResponse response) {
		User user = getLoginUser();
		// 上传文件用
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		InputStream in = null;
		List<List<Object>> listob = null;
		// MultipartFile file = multipartRequest.getFile("upfile"); jquery 提交的文件
		MultipartFile file = multipartRequest.getFile("txt_file");// bootstrap获取流的方式
		try {
			if (file == null || file.isEmpty()) {
				throw new Exception(noData);
			}
			List<FansDailyBean> fansDailys;
			in = file.getInputStream();
			listob = new ImportExcelUtils().getBankListByExcel(in, file.getOriginalFilename());
			fansDailys = new ArrayList<FansDailyBean>();
			String msg = "";
			String formatmsg = erroyFormate;
			for (int i = 0; i < listob.size(); i++) {
				List<Object> lo = listob.get(i);
				if (lo.size() > 0 && DateUtils.isDateType(String.valueOf(lo.get(0)))) {
					try {
						Integer i1=UtilTool.convertToInteger(String.valueOf(lo.get(1)));
						Integer i2=UtilTool.convertToInteger(String.valueOf(lo.get(2)));
						Integer i3=UtilTool.convertToInteger(String.valueOf(lo.get(3)));
						Integer i4=UtilTool.convertToInteger(String.valueOf(lo.get(4)));
						Integer i5=UtilTool.convertToInteger(String.valueOf(lo.get(5)));
						FansDailyBean fansDailyBean = new FansDailyBean(String.valueOf(lo.get(0)),
								i1,
								i2,
								i3,
								i4,
								i5,
								UtilTool.convertToInteger(String.valueOf(lo.get(6))), user.getShopId());
						if (!user.isHasDeleteHistoryAuthority()
								&& DateUtils.compareMonthsAgo(String.valueOf(lo.get(0)))) {
							msg = noDeleteHistoryAuthority;
						} else {
							fansDailys.add(fansDailyBean);
						}
					} catch (Exception e) {
						formatmsg = formatmsg + " " + String.valueOf(lo.get(0));
						e.printStackTrace();
					}
				}

			}

			if (fansDailys.size() > 0) {
				if (formatmsg.length() != erroyFormate.length()) {
					msg=formatmsg;
				}
				WriteObject(response, fansDailyServiceImpl.insertByBatchLarge(fansDailys) + msg);
			}
			else {
				WriteObject(response, noData + msg);
				return;
			}
		} catch (Exception e) {
			WriteObject(response, savesErroy);
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
