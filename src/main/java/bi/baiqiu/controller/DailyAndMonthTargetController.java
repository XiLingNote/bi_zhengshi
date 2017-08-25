package bi.baiqiu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bi.baiqiu.mapper.DailyTargetMapper;
import bi.baiqiu.mapper.GoalMonthAlipMapper;
import bi.baiqiu.mapper.GoalMonthSaleMapper;
import bi.baiqiu.page.PageBean;
import bi.baiqiu.pojo.DailyTarget;
import bi.baiqiu.pojo.GoalMonthAlip;
import bi.baiqiu.pojo.GoalMonthsale;
import bi.baiqiu.pojo.User;
import bi.baiqiu.service.impl.DailyTargetServiceImpl;
import bi.baiqiu.service.impl.GoalMonthAlipServiceImpl;
import bi.baiqiu.service.impl.GoalMonthSaleServiceImpl;
import bi.baiqiu.service.impl.RedisServiceImpl;
import bi.baiqiu.utils.DateUtils;
import bi.baiqiu.utils.ExportExcelUtils;
import bi.baiqiu.utils.ImportExcelUtils;
import bi.baiqiu.utils.UtilTool;

import com.google.common.reflect.TypeToken;

@Controller
@RequestMapping("target")
public class DailyAndMonthTargetController extends BaseController {
	Logger log = Logger.getLogger(DailyAndMonthTargetController.class);

	@Autowired
	private DailyTargetMapper dailyTargetDao;
	@Autowired
	private DailyTargetServiceImpl dailyTargetService;
	@Autowired
	private GoalMonthAlipServiceImpl goalMonthAlipServiceImp;
	@Autowired
	private GoalMonthSaleServiceImpl goalMonthSaleServiceImp;

	@Autowired
	private GoalMonthAlipMapper goalMonthAlipDao;
	@Autowired
	private GoalMonthSaleMapper goalMonthSaleDao;
	@Autowired
	private RedisServiceImpl redisServiceImpl;

	@RequestMapping(value = "getDailyMonthTargetSum.do")
	public void getDailyMonthTargetSum(DailyTarget dailyTarget, PageBean page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = getLoginUser();
		dailyTarget.setShopId(user.getShopId());
		WriteObject(response, dailyTargetService.getDailyMonthTargetSum(dailyTarget));
	}

	/**
	 * 分页查询日计划
	 * 
	 * @param dailyTarget
	 * @param rowBounds
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "queryDailyTarget.do")
	public void queryDailyTarget(DailyTarget dailyTarget, PageBean page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date date = new Date();

		response.setCharacterEncoding("utf-8");
		List<DailyTarget> dailyTargets = dailyTargetDao.queryDailyTarget(dailyTarget, page);
		Gson gson = new GsonBuilder().serializeNulls().create();
		String json = "{\"total\":" + page.getTotal() + ",\"rows\":" + gson.toJson(dailyTargets) + "}";
		response.getWriter().print(json);
		Date date1 = new Date();
		log.info("--------------------生成时间-------------------------------" + (date1.getTime() - date.getTime()));
	}

	/**
	 * 不分页
	 * 
	 * @param dailyTarget
	 * @param rowBounds
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "queryDailyTargetNopage.do", method = RequestMethod.POST)
	public void queryDailyTargetNoPage(DailyTarget dailyTarget, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user=getLoginUser();
		dailyTarget.setShopId(user.getShopId());
		List<DailyTarget> dailyTargets = dailyTargetDao.queryDailyTarget(dailyTarget, new PageBean().setMeToDefault());
		WriteObject(response, dailyTargets);
	}

	/**
	 * 插入单条日计划把日期添加唯一索引
	 * @param dailyTarget
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "insertDailyTarget.do", method = RequestMethod.POST)
	public void insertDailyTarget(DailyTarget dailyTarget, HttpServletRequest request, HttpServletResponse response) {
		try {
			dailyTargetService.insertDailyTarget(dailyTarget);
		} catch (Exception e) {
			WriteMsg(response, savesErroy);
			e.printStackTrace();
		}

	}

	/**
	 * 插入多条日计划把日期作为唯一索引，错误则提示
	 * 
	 * @param dailyTarget
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "insertDailyTargetByBatch.do", method = RequestMethod.POST)
	public void insertDailyTargetByBatch(List<DailyTarget> dailyTargetList, HttpServletRequest request,
			HttpServletResponse response) {
		User user=getLoginUser();
		try {
			 dailyTargetService.insertDailyTargetByBatch(dailyTargetList,user);
			WriteMsg(response, saveSuccess);
			dailyTargetService.SaveRedis(user);
		} catch (Exception e) {
			WriteMsg(response, savesErroy);
			e.printStackTrace();
		}

	}

	/**
	 * 插入或者更新日计划
	 * 
	 * @param dailyTarget
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "insertOrUpdateDailyTargetByBatch.do", method = RequestMethod.POST)
	public void insertOrUpdateDailyTargetByBatch(String dailyTargets, HttpServletRequest request,
			HttpServletResponse response) {
		Gson gson = new Gson();

		try {
			if (dailyTargets != null) {
				@SuppressWarnings("serial")
				List<DailyTarget> list = gson.fromJson(dailyTargets, new TypeToken<List<DailyTarget>>() {
				}.getType());
				dailyTargetService.insertOrUpdateDailyTargetByBatch(list);
			}
			WriteMsg(response, saveSuccess);
		} catch (Exception e) {
			WriteMsg(response, savesErroy);
			e.printStackTrace();
		}

	}

	/**
	 * 更新日计划
	 * 
	 * @param dailyTarget
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "updateDailyTarget.do", method = RequestMethod.POST)
	public void updateDailyTarget(DailyTarget dailyTarget, HttpServletRequest request, HttpServletResponse response) {
		try {
			dailyTargetDao.updateDaliypTarget(dailyTarget);
		} catch (Exception e) {
			WriteMsg(response, savesErroy);
			e.printStackTrace();
		}
	}

	/**
	 * 删除日计划
	 * 
	 * @param dailyTarget
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteDailyTargetByBatch.do", method = RequestMethod.POST)
	public void deleteDailyTarget(@RequestParam(value = "idArray[]") int[] idArray, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user=getLoginUser();
			dailyTargetService.deleteDailyTargetByBatch(idArray);
			WriteMsg(response, deleteSuccess);
			dailyTargetService.SaveRedis(user);
		} catch (Exception e) {
			e.printStackTrace();
			WriteMsg(response, deleteErroy);

		}
	}

	/**
	 * 日计划上传
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "uploadDailyTarget.do", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadDailyTarget(HttpServletRequest request, HttpServletResponse response) {
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
			List<DailyTarget> dailyTargets;
			in = file.getInputStream();
			listob = new ImportExcelUtils().getBankListByExcel(in, file.getOriginalFilename());
			dailyTargets = new ArrayList<DailyTarget>();
			String formatmsg = erroyFormate;
			for (int i = 0; i < listob.size(); i++) {
				DailyTarget dailyTarget = new DailyTarget();
				List<Object> lo = listob.get(i);
				try {
					if (lo.size() > 0 && DateUtils.isDateType(String.valueOf(lo.get(0)))) {
						dailyTarget.setDate(String.valueOf(lo.get(0)));// 日期
						String payment=lo.get(1).toString();
						dailyTarget.setPayment(UtilTool.convertToBigInteger(payment).add(new BigInteger("100")));// 计划量
						dailyTarget.setShopId(user.getShopId());
						dailyTargets.add(dailyTarget);
					}
				} catch (Exception e) {
					formatmsg = formatmsg + " " + String.valueOf(lo.get(0));
					e.printStackTrace();
				}
			}
			if (dailyTargets.size() > 0){
				if (formatmsg.length() == erroyFormate.length()) {
					formatmsg="";
				}
				WriteObject(response, dailyTargetService.insertDailyTargetByBatch(dailyTargets,user)+formatmsg);
			}
			else{
				WriteObject(response, noData);
				return;
			}
		} catch (NullPointerException e4) {
			WriteObject(response, analysisExcelErroy);
		} catch (DataAccessException e3) {
			WriteObject(response, noUpdateHistoryAuthority);
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
		//保存到redis
		dailyTargetService.SaveRedis(user);
	}

	/**
	 * 日计划导出
	 * 
	 * @param dailyTarget
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "exportDailyTarget.do", method = RequestMethod.POST)
	public void exportDailyTarget(DailyTarget dailyTarget, PageBean page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("--------------------导出-------------------------------");
		ExportExcelUtils util = new ExportExcelUtils();
		page.setMeToDefault();
		Date date = new Date();
		String path = "C:";
		String fileName = date.getTime() + ".xlsx";
		util.init_Excel(path, fileName, new String[] { "id", "店铺", "时间", "目标计划数" });
		String[] dailyfiles = new String[] { "id", "storename", "date", "payment" };// 对象的属性名字
		List<Object> dailys = new ArrayList<>();
		dailys.addAll(dailyTargetDao.queryDailyTarget(dailyTarget, page));
		util.write_data_Excel(dailys, dailyfiles);
		util.write_excel_disk();
		Date date1 = new Date();

		util.downLoad(path + fileName, response, false);
		Date date2 = new Date();
		log.info("--------------------生成时间-------------------------------" + (date1.getTime() - date.getTime()));
		log.info("--------------------导出时间-------------------------------" + (date2.getTime() - date1.getTime()));

	}

	/**
	 * 日计划导出
	 * 
	 * @param dailyTarget
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "downloadDailyTargetTemplate.do")
	public void exportDailyTargetTemp(DailyTarget dailyTarget, PageBean page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExportExcelUtils util = new ExportExcelUtils();
		util.downLoad("C:DailyTargetTemplate.xlsx", response, false);
	}

	/**
	 * alipay查询
	 * 
	 * @param goalMonthAlip
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "queryGoalMonthAlip.do")
	public void queryGoalMonthAlip(GoalMonthAlip goalMonthAlip, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WriteObject(response, goalMonthAlipServiceImp.queryGoalMonthAlip(goalMonthAlip));
	}

	/**
	 * list新增或者保存
	 * 
	 * @param goalMonthAlips
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "insertAndupdateGoalMonthAlipByBatch.do", method = RequestMethod.POST)
	public void insertAndupdateGoalMonthAlipByBatch(GoalMonthAlip goalMonthAlip, HttpServletRequest request,
			HttpServletResponse response) {
		List<GoalMonthAlip> goalMonthAlips = goalMonthAlip.getGoalMonthAlips();
		try {
			if (goalMonthAlips != null) {
				goalMonthAlipServiceImp.insertAndupdateGoalMonthAlipByBatch(goalMonthAlips);
				WriteMsg(response, saveSuccess);
			} else {
				WriteMsg(response, noData);
			}
		} catch (Exception e) {
			WriteMsg(response, savesErroy);
			e.printStackTrace();
		}
	}

	/**
	 * 新增或者保存
	 * 
	 * @param goalMonthAlips
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "insertOrUpdateGoalMonthAlip.do", method = RequestMethod.POST)
	public void insertOrUpdateGoalMonthAlip(GoalMonthAlip goalMonthAlip, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user=getLoginUser();
			if (goalMonthAlip != null) {
				
				int a=goalMonthAlipServiceImp.insertOrupdateGoalMonthAlip(goalMonthAlip);
				
				if(a>0){
					redisServiceImpl.saveAlipayMonthTargetToRedis(user, goalMonthAlipDao.selectByPrimaryKey(a));
				}
				WriteMsg(response, saveSuccess);
			} else {
				WriteMsg(response, noData);
			}
		} catch (Exception e) {
			WriteMsg(response, savesErroy);
			e.printStackTrace();
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param goalMonthAlip
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteGoalMonthAlipBybatch.do", method = RequestMethod.POST)
	public void deleteGoalMonthAlip(List<GoalMonthAlip> goalMonthAlips, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		goalMonthAlipDao.updateGoalMonthAlipBybatch(goalMonthAlips);
	}

	/**
	 * GMV月度计划
	 * 
	 * @param goalMonthsale
	 * @param request
	 * @param response
	 */

	@RequestMapping(value = "queryGoalMonthSale.do")
	public void queryGoalMonthSale(GoalMonthsale goalMonthSale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<GoalMonthsale> goalMonthsales = goalMonthSaleServiceImp.queryGoalMonthsale(goalMonthSale);
		WriteObject(response, goalMonthsales);
	}

	/**
	 * 新增或者保存
	 * 
	 * @param goalMonthAlips
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "insertOrupdateGoalMonthSale.do", method = RequestMethod.POST)
	public void insertOrupdateGoalMonthSale(GoalMonthsale goalMonthSale, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			User user=getLoginUser();
			if (goalMonthSale != null) {
				int a=goalMonthSaleServiceImp.insertOrupdateGoalMonthSale(goalMonthSale);
				WriteMsg(response, saveSuccess);
				if(a>0){
					redisServiceImpl.saveGmvMonthTargetToRedis(user, goalMonthSaleDao.selectByPrimaryKey(a));
				}
			} else {
				WriteMsg(response, noData);
			}
		} catch (Exception e) {
			WriteMsg(response, savesErroy);
			e.printStackTrace();
		}
	}

	/*	*//**
			 * 新增或者保存
			 * 
			 * @param goalMonthAlips
			 * @param request
			 * @param response
			 *//*
			 * @RequestMapping(value = "insertOrUpdateGoalMonthSale.do", method
			 * = RequestMethod.POST) public void
			 * insertOrUpdateGoalMonthSale(GoalMonthsale goalMonthSale,
			 * HttpServletRequest request, HttpServletResponse response) { try {
			 * if (goalMonthSale != null) {
			 * goalMonthSaleServiceImp.insertOrupdateGoalMonthSale(goalMonthSale
			 * ); WriteMsg(response, "保存成功"); } else { WriteMsg(response,
			 * "数据为空"); } } catch (Exception e) { WriteMsg(response, "保存失败");
			 * e.printStackTrace(); } }
			 */
	@RequestMapping(value = "deleteGoalMonthSale", method = RequestMethod.POST)
	public void deleteGoalMonthSale(GoalMonthsale goalMonthsale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		goalMonthSaleDao.delete(goalMonthsale);
	}
}
