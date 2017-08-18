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

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.CommodityPropertyBean;
import bi.baiqiu.pojo.User;
import bi.baiqiu.service.impl.CommodityPropertyServiceImpl;
import bi.baiqiu.utils.ImportExcelUtils;

/**
 *  访客特征 CommodityProperty
 * 	@author Jared
 *	@time 2017年8月4日上午9:21:15
 */
@Controller
@RequestMapping("commodityProperty")
public class CommodityPropertyController extends BaseController {
	@Autowired
	private CommodityPropertyServiceImpl commodityPropertyServiceImpl;

	/**
	 * 分页查询
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryCommodityPropertyByPage.do")
	public void queryByPage(CommodityPropertyBean bean, Integer pageNum, Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		User user = getLoginUser();
		try {
			response.setCharacterEncoding("utf-8");
			bean.setShopId(user.getShopId());
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page<CommodityPropertyBean> page = commodityPropertyServiceImpl.queryByPage(bean, pageNum, pageSize);
			String json = "{\"total\":" + page.getTotal() + ",\"rows\":" + gson.toJson(page.getResult()) + "}";
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, systemErroy);
		}
	}

	/**
	 * 获取特征和属性
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryFeatureAndProperty.do")
	public void queryFeatureAndProperty(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = getLoginUser();
			String write = JSONUtils
					.toJSONString(commodityPropertyServiceImpl.queryFeatureAndProperty(user.getShopId()));
			WriteObject(response, write);

		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, systemErroy);
		}
	}

	/**
	 * @param request
	 * @param response
	 * @param feature
	 */
	@RequestMapping(value = "queryFeatureAndProperty2.do")
	public void queryFeatureAndProperty2(HttpServletRequest request, HttpServletResponse response, String feature) { try {
			User user = getLoginUser();
			String write = JSONUtils
					.toJSONString(commodityPropertyServiceImpl.queryFeatureAndProperty2(user.getShopId(), feature));
			WriteObject(response, write);

		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, systemErroy);
		}
	}

	/**
	 * 行内操作
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "postCommodityProper.do")
	public void queryByPage(CommodityPropertyBean bean, String action, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (bean==null||bean.getId() == null||StringUtils.isEmpty(action)){
				WriteObject(response, dataCheckError);
				return;	
			}
			if (action.equals("delete")) {
				int a = commodityPropertyServiceImpl.deleteCommodityProperty(bean);
				if (a > 0)
					WriteObject(response, deleteSuccess + a);
				else
					WriteObject(response, deleteErroy);
				return;
			} else if (action.equals("edit")) {
//				数据校验超出数据库数据长度
				if(bean.getGoodsId().length()>18||bean.getSku().length()>20||bean.getSpu().length()>20||bean.getProperty().length()>100||bean.getFeature().length()>100){
					WriteObject(response, dataCheckError);
					return ;
				}
				int a = commodityPropertyServiceImpl.updateCommodityProperty(bean);
				if (a > 0)
					WriteMsg(response, saveSuccess);
				else
					WriteMsg(response, savesErroy);
			}else{
				WriteObject(response, dataCheckError);
			}
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
	@RequestMapping(value = "updateCommodityProperty.do")
	public void updateCommodityProperty(CommodityPropertyBean bean, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			WriteObject(response, saveSuccess + commodityPropertyServiceImpl.updateCommodityProperty(bean));
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
	@RequestMapping(value = "deleteCommodityPropertyByBatch.do")
	public void deleteCommodityPropertyByBatch(@RequestParam(value = "idArray[]") int[] idArray,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			int a = commodityPropertyServiceImpl.deleteCommodityPropertyByBatch(idArray);
			if (a > 0)
				WriteMsg(response, deleteSuccess + a);
			else {
				WriteMsg(response, deleteErroy);
			}
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, deleteErroy);
		}
	}

	/**
	 * 粉丝特征
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "uploadCommodityProperty.do", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadCommodityProperty(HttpServletRequest request, HttpServletResponse response) {
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
			List<CommodityPropertyBean> commodityPropertys;
			in = file.getInputStream();
			listob = new ImportExcelUtils().getBankListByExcel(in, file.getOriginalFilename());
			commodityPropertys = new ArrayList<CommodityPropertyBean>();
			for (int i = 0; i < listob.size(); i++) {
				List<Object> lo = listob.get(i);
				if (lo.size() > 0 && String.valueOf(lo.get(0)) != null && String.valueOf(lo.get(0)) != "") {
					CommodityPropertyBean commodityPropertyBean = new CommodityPropertyBean(String.valueOf(lo.get(0)),
							String.valueOf(lo.get(1)), String.valueOf(lo.get(2)), String.valueOf(lo.get(3)),
							String.valueOf(lo.get(4)), user.getShopId());
					commodityPropertys.add(commodityPropertyBean);
				}
			}
			if (commodityPropertys.size() > 0)
				WriteObject(response, commodityPropertyServiceImpl.insertByBatchLarge(commodityPropertys));
			else {
				WriteObject(response, noData);
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
