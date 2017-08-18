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
import bi.baiqiu.pojo.ThrougTrainBean;
import bi.baiqiu.pojo.User;
import bi.baiqiu.service.impl.ThrougTrainServiceImpl;
import bi.baiqiu.utils.DateUtils;
import bi.baiqiu.utils.ImportExcelUtils;

/**
 * 直通车
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("througTrain")
public class ThrougTrainController extends BaseController {
	@Autowired
	private ThrougTrainServiceImpl througTrainServiceImpl;

	/**
	 * 删除
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param requThrougTrain
	 *            response
	 */
	@RequestMapping(value = "deleteThrougTrain.do")
	public void deleteThrougTrain(ThrougTrainBean bean, Integer pageNum, Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			WriteObject(response, deleteSuccess + througTrainServiceImpl.deleteThrougTrain(bean));
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, deleteErroy);
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryThrougTrainByPage.do")
	public void queryByPage(ThrougTrainBean bean, Integer pageNum, Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		User user = getLoginUser();
		try {
			response.setCharacterEncoding("utf-8");
			bean.setShopId(user.getShopId());
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page<ThrougTrainBean> page = througTrainServiceImpl.queryByPage(bean, pageNum, pageSize);
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
	@RequestMapping(value = "updateThrougTrain.do")
	public void updateThrougTrain(ThrougTrainBean bean, Integer pageNum, Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			WriteObject(response, saveSuccess + througTrainServiceImpl.updateThrougTrain(bean));
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, savesErroy);
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
	@RequestMapping(value = "deleteThrougTrainByBatch.do")
	public void deleteThrougTrainByBatch(@RequestParam(value = "idArray[]") int[] idArray, HttpServletRequest request,
			HttpServletResponse response) {
		User user=getLoginUser();
		ThrougTrainBean bean= new ThrougTrainBean();
		bean.setIdArray(idArray);
		//不可删除两月前的数据
		bean.setDeleteHistory(user.isHasDeleteHistoryAuthority());

		try {
			int a = througTrainServiceImpl.deleteThrougTrainByBatch(bean);
			WriteObject(response, deleteSuccess + a);
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, deleteErroy);
		}
	}

	/**
	 * 直通车
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "uploadThrougTrain.do", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadThrougTrain(HttpServletRequest request, HttpServletResponse response) {
		User user = getLoginUser();
		// 上传文件用
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		InputStream in = null;
		List<List<Object>> listob = null;
		// MultipartFile file = multipartRequest.getFile("upfile"); jquery 提交的文件
		MultipartFile file = multipartRequest.getFile("txt_file");// bootstrap获取流的方式
		try {
			if (file == null || file.isEmpty()) {
				throw new Exception("文件不存在！");
			}
			List<ThrougTrainBean> througTrainBeans;
			in = file.getInputStream();
			listob = new ImportExcelUtils().getBankListByExcel(in, file.getOriginalFilename());
			througTrainBeans = new ArrayList<ThrougTrainBean>();
			String msg="";
			for (int i = 0; i < listob.size(); i++) {
				List<Object> lo = listob.get(i);
				if (lo.size() > 0&&DateUtils.isDateType(String.valueOf(lo.get(0)))) {
						ThrougTrainBean througTrainBean = new ThrougTrainBean(String.valueOf(lo.get(0)),
								String.valueOf(lo.get(1)), String.valueOf(lo.get(2)), String.valueOf(lo.get(3)),
								String.valueOf(lo.get(4)), String.valueOf(lo.get(5)), String.valueOf(lo.get(6)),
								String.valueOf(lo.get(7)), String.valueOf(lo.get(8)), String.valueOf(lo.get(9)),
								String.valueOf(lo.get(10)), user.getShopId());
						if (!user.isHasDeleteHistoryAuthority()
								&&DateUtils.compareMonthsAgo(String.valueOf(lo.get(0)))) {
								msg=noDeleteHistoryAuthority;
						} else {
							througTrainBeans.add(througTrainBean);
						}
				}
			}
			if (througTrainBeans.size() > 0)
				WriteObject(response, througTrainServiceImpl.insertByBatchLarge(througTrainBeans));
			else{
				WriteObject(response, noData+msg);
				return ;
			}
		} catch (NullPointerException e4) {
			WriteObject(response, analysisExcelErroy);
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
