package bi.baiqiu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bi.baiqiu.service.StoreSaleService;
import bi.baiqiu.utils.GsonUtils;
import bi.baiqiu.utils.PinYin4jUtils;

@Controller
@RequestMapping("store")
public class StoreSalesController {

	@Autowired
	private StoreSaleService storeSaleService;

	@ResponseBody
	@RequestMapping("month")
	public String monthSales(Model model, HttpServletRequest request) {
		String amountType = request.getParameter("amountType");
		String department = request.getParameter("department");
		Map<String, Object> map = storeSaleService.queryThisMonth(amountType,
				department);
		String json = GsonUtils.gson.toJson(map);
		return json;
	}
	
	
	
	
	
	
	/**   
	* @Function: StoreSalesController.java
	* @Description: 该函数的功能描述
	*
	* @param:参数描述
	* @return：返回结果描述
	* @throws：异常描述
	*
	* @version: v1.0.0
	* @author: FengCheng
	* @date: 2017年7月21日 上午9:56:39 
	*
	* Modification History:
	* Date         Author          Version            Description
	*---------------------------------------------------------*
	* 2017年7月21日     FengCheng           v1.0.0               修改原因
	*/
	
	@ResponseBody
	@RequestMapping("storeSalesDemonstrate")
	public String storeSalesDemonstrate(Model model, HttpServletRequest request) {
//		String amountType = request.getParameter("amountType");
//		String department = request.getParameter("department");
		//cpuqijian
		String storeName  =  request.getParameter("storeName");
		Map<String, Object> map = storeSaleService.storeSalesDemonstrate(storeName);
		String json = GsonUtils.gson.toJson(map);
		return json;
	}


	/**
	 * @param date
	 *            日期
	 * @param type
	 *            Gmv、Alipay
	 * @return
	 */
	@ResponseBody
	@RequestMapping("daily")
	public String daliySales(
			@RequestParam(value = "amountType", required = false) String amountType,
			@RequestParam(value = "endStr", required = false) String endStr,
			@RequestParam(value = "dateType", required = false) String dateType) {
		Map<String, Object> map = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		if (StringUtils.isBlank(amountType)) {
			amountType = "GMV";
		}

		if (StringUtils.isNotBlank(endStr)) {
			try {
				date = format.parse(endStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		map = storeSaleService.queryByThisDay(dateType, date, amountType);
		String json = GsonUtils.gson.toJson(map);
		return json;
	}

	// datetype：y m d, date 日期, store 店铺名称 请求参数
	@ResponseBody
	@RequestMapping("salesTrend")
	public String salesTrend(
			@RequestParam(value = "dateType", required = false) String dateType,
			@RequestParam(value = "beginStr", required = false) String beginStr,
			@RequestParam(value = "endStr", required = false) String endStr,
			@RequestParam(value = "store", required = false) String store) {
		Map<String, Object> map = null;

		if (StringUtils.isBlank(store)) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		Date begin = null;
		Date end = null;

		Integer beginYear = null;
		Integer endYear = null;

		if (StringUtils.isNotBlank(beginStr)) {
			try {
				begin = format.parse(beginStr);
				beginYear = begin.getYear();
			} catch (ParseException e) {
				throw new RuntimeException("开始时间转换异常");
			}
		}

		if (StringUtils.isNotBlank(endStr)) {
			try {
				end = format.parse(endStr);
				endYear = end.getYear();
			} catch (ParseException e) {
				throw new RuntimeException("结束时间转换异常");
			}
		}

		if (begin != null && end != null) {

			// 按日期查找
			if ("DAY".equals(dateType)) {

				map = storeSaleService.getDayOfMonth(store, begin, end);
				// 以月为单位进行查询
			} else if ("MONTH".equals(dateType)) {

				map = storeSaleService.getMonthofYear(store, begin, end);
			} else if ("YEAR".equals(dateType)) {

				map = storeSaleService.getYear(store, beginYear + 1900,
						endYear + 1900);
			} else {
				return null;
			}

		} else {// 缺少时间参数
			try {
				String encode = URLEncoder.encode("请选择起止时间", "utf-8");
				return encode;
			} catch (UnsupportedEncodingException e1) {

			}
		}

		String s = GsonUtils.gson.toJson(map);
		return GsonUtils.gson.toJson(map);

	}

	@ResponseBody
	@RequestMapping(value = "name", produces = "text/html;charset=UTF-8")
	public String name(Model model, HttpServletRequest request) {

		Set<String> set = storeSaleService.queryStoreName();
		String json = GsonUtils.gson.toJson(set);
		return json;
	}

}
