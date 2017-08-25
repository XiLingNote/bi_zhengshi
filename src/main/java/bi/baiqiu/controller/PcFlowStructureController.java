package bi.baiqiu.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taobao.api.domain.Order;

import bi.baiqiu.mapper.BiTradeGoodsMapper;
import bi.baiqiu.mapper.BiTradeOrdersMapper;
import bi.baiqiu.mapper2.SysTradeBeanMapper;
import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.PcFlowStructureBean;
import bi.baiqiu.pojo2.SysTradeBean;
import bi.baiqiu.pojo.User;
import bi.baiqiu.pojo.test.BorderBean;
import bi.baiqiu.pojo.test.TradeTemplate;
import bi.baiqiu.service.impl.PcFlowStructureServiceImpl;
import bi.baiqiu.utils.DateUtils;
import bi.baiqiu.utils.GetDateTread;
import bi.baiqiu.utils.ImportExcelUtils;
import bi.baiqiu.utils.GetDateTread.RunGetDateTread;
import bi.baiqiu.utils.GetDateTread.RunGetDateTreadPagingSave;
import bi.baiqiu.utils.GetDateTread.RunGetDateTreadSave;

/**
 * pc流量结构控制层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("pcFlowStructure")
public class PcFlowStructureController extends BaseController {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private PcFlowStructureServiceImpl pcFlowStructureServiceImpl;
	@Autowired
	@Qualifier("sqlSessionTemplate1")
	private SqlSessionTemplate sqlSessionTemplateSys;
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired 
	@Qualifier("dataSource")
	DruidDataSource dataSource; 
	@Autowired
	private DataSourceTransactionManager dataSourceTransactionManager;
	// @Autowired
	// private SysTradeBeanMapper sysDao;

	/**
	 * 删除
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deletePcFlowStructure.do")
	public void deletePcFlowStructure(PcFlowStructureBean bean, Integer pageNum, Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		User user = getLoginUser();
		try {
			WriteObject(response, deleteSuccess + pcFlowStructureServiceImpl.deletePcFlowStructure(bean, user));
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, deleteSuccess);
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
	@RequestMapping(value = "queryPcFlowStructureByPage.do")
	public void queryByPage(PcFlowStructureBean bean, Integer pageNum, Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		User user = getLoginUser();
		try {
			response.setCharacterEncoding("utf-8");
			bean.setShopId(user.getShopId().toString());
			Gson gson = new GsonBuilder().serializeNulls().create();
			Page<PcFlowStructureBean> page = pcFlowStructureServiceImpl.queryByPage(bean, pageNum, pageSize);
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
	@RequestMapping(value = "updatePcFlowStructure.do")
	public void updatePcFlowStructure(PcFlowStructureBean bean, Integer pageNum, Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			WriteObject(response, saveSuccess + pcFlowStructureServiceImpl.updatePcFlowStructure(bean));
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
	@RequestMapping(value = "deletePcFlowStructureByBatch.do")
	public void deletePcFlowStructureByBatchList(@RequestParam(value = "idArray[]") int[] idArray,
			HttpServletRequest request, HttpServletResponse response) {
		User user = getLoginUser();
		PcFlowStructureBean bean = new PcFlowStructureBean();
		try {
			bean.setIdArray(idArray);
			// 不可删除两月前的数据
			bean.setDeleteHistory(user.isHasDeleteHistoryAuthority());
			int a = pcFlowStructureServiceImpl.deletePcFlowStructureByBatchList(bean);
			WriteObject(response, deleteSuccess + a);
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, deleteErroy);
		}
	}

	/**
	 * pc流量结构上传
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "uploadPcFlowStructure.do", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadPcFlowStructure(HttpServletRequest request, HttpServletResponse response) {
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
			List<PcFlowStructureBean> pcFlowStructures;
			in = file.getInputStream();
			listob = new ImportExcelUtils().getBankListByExcel(in, file.getOriginalFilename());
			pcFlowStructures = new ArrayList<PcFlowStructureBean>();
			String msg = "";
			for (int i = 0; i < listob.size(); i++) {
				List<Object> lo = listob.get(i);
				if (lo.size() > 0 && DateUtils.isDateType(String.valueOf(lo.get(0)))) {
					PcFlowStructureBean pcFlowBean = new PcFlowStructureBean(String.valueOf(lo.get(0)),
							String.valueOf(lo.get(1)), String.valueOf(lo.get(2)), String.valueOf(lo.get(3)),
							String.valueOf(lo.get(4)), String.valueOf(lo.get(5)), String.valueOf(lo.get(6)),
							String.valueOf(lo.get(7)), String.valueOf(lo.get(8)), String.valueOf(lo.get(9)),
							String.valueOf(lo.get(10)), String.valueOf(lo.get(11)), String.valueOf(lo.get(12)),
							String.valueOf(lo.get(13)), String.valueOf(lo.get(14)), String.valueOf(lo.get(15)),
							String.valueOf(lo.get(16)), String.valueOf(lo.get(17)), String.valueOf(lo.get(18)),
							String.valueOf(lo.get(19)), String.valueOf(lo.get(20)), String.valueOf(lo.get(21)),
							String.valueOf(lo.get(22)), String.valueOf(lo.get(23)), String.valueOf(lo.get(24)),
							String.valueOf(lo.get(25)), String.valueOf(lo.get(26)), String.valueOf(lo.get(27)),
							Double.valueOf(
									String.valueOf(lo.get(28)).equals(null) || String.valueOf(lo.get(28)).equals("")
											? "0"
											: String.valueOf(lo.get(28))),
							user.getShopId().toString());
					if (!user.isHasDeleteHistoryAuthority() && DateUtils.compareMonthsAgo(String.valueOf(lo.get(0)))) {
						msg = noDeleteHistoryAuthority;
					} else {
						pcFlowStructures.add(pcFlowBean);
					}
				}
				lo.clear();
			}
			if (pcFlowStructures.size() > 0) {
				WriteObject(response, pcFlowStructureServiceImpl.insertByBatchLarge(pcFlowStructures));
			} else {
				WriteObject(response, noData + msg);
				return;
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

	/**
	 * 批量删除
	 * 
	 * @param bean
	 * @param pageNum
	 * @param pageSize
	 * @param request
	 * @param response
	 */
	@Transactional
	@RequestMapping(value = "test.do")
	public String test(HttpServletRequest request, HttpServletResponse response) {
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		SqlSession session2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		SqlSession session3 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			Date date = new Date(117, 3, 18);
			for (int i = 0; i < 2; i++) {
				String startDate = DateUtils.dateToString(new Date(117, 3, 17 + i));
				String endDate = DateUtils.dateToString(new Date(117, 3, 18 + i));
				test3(startDate, endDate, session, session2, session3);
			}
			Date d5 = new Date();

			Date d6 = new Date();
			log.info("-----test3 Thread------runTime---------------------" + (d6.getTime() - d5.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, deleteErroy);
		}
		return "redirect:/page/showlogin.do";
	}

	@Transactional
	@RequestMapping(value = "test2.do")
	public String test2(HttpServletRequest request, HttpServletResponse response) {
		try {
	
			Date d1 = new Date();
			//test();
			Date d2 = new Date();
			log.info("-----test Thread------runTime---------------------" + (d2.getTime() - d1.getTime()));
			Date d3 = new Date();
			String startDate="2017-01-01";
			String endDate="2017-01-06";
			String startAdd=startDate;
			do{
				startDate=startAdd;
				startAdd=DateUtils.stringDateAddDay(startAdd, 1);
				//test222(startDate, startAdd);
				test22(startDate, startAdd);
				Thread.sleep(20);
			}
			while(DateUtils.stringToDate(startAdd).compareTo(DateUtils.stringToDate(endDate))<0);
			Date d4 = new Date();
			log.error("-----test2 Thread------runTime---------------------" + (d3.getTime() - d4.getTime()));

			log.info("-----test2 Thread------runTime---------------------" + (d3.getTime() - d4.getTime()));
			Date d5 = new Date();
		
			Date d6 = new Date();
			log.info("-----test3 Thread------runTime---------------------" + (d6.getTime() - d5.getTime()));
			
		} catch (Exception e) {
			e.printStackTrace();
			WriteObject(response, deleteErroy);
		}
		return "redirect:/page/showlogin.do";
	}

	public void test() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = dataSourceTransactionManager.getTransaction(def);

		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		SqlSession session2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		SqlSession session3 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		SysTradeBeanMapper sysMapper = session3.getMapper(SysTradeBeanMapper.class);
		BiTradeOrdersMapper orderMapper = session.getMapper(BiTradeOrdersMapper.class);
		BiTradeGoodsMapper goodsMapper = session2.getMapper(BiTradeGoodsMapper.class);
		try {
			Page<SysTradeBean> page = new Page<>(0, 6000);
			Date d1 = new Date();
			log.info("-----test------query--begin---------------------");
			List<SysTradeBean> list = sysMapper.selectByPage(page);
			Date d2 = new Date();
			log.info("------test-----query--end---------------------" + (d2.getTime() - d1.getTime()));
			Date d3 = new Date();
			log.info("-----test------insert--begin---------------------");

			int commit = 1;
			for (SysTradeBean sys : list) {
				BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);
				TradeTemplate tr = border.getTrade_fullinfo_get_response().getTrade();
				orderMapper.insertBys(tr);
				for (Order order : tr.getOrders().getOrder()) {
					goodsMapper.insertBys(order);
				}
				commit++;
				if (commit % 2000 == 0 || commit == list.size()) {
					dataSourceTransactionManager.commit(status);
					Date d5 = new Date();
					log.info("---test--------commit--end---------------------" + commit + "--"
							+ (d5.getTime() - d3.getTime()));
				}
			}
			Date d4 = new Date();
			log.info("-------test----insert--end---------------------" + (d4.getTime() - d3.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			session2.rollback();
			session3.rollback();
		} finally {
			session.close();
			session2.close();
			session3.close();
		}

	}
//	外部分页进行保存
	public void test222(String startDate, String endDate) throws InterruptedException {
		SqlSession session3 = sqlSessionTemplateSys.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);

		SysTradeBean bean = new SysTradeBean();
		bean.setStartDate(startDate);
		bean.setEndDate(endDate);
		int count = 100;
		try {
			// 总记录数使用真实数据
			SysTradeBeanMapper sysMapper = session3.getMapper(SysTradeBeanMapper.class);
			count = sysMapper.selectDateCount(bean);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session3.close();
		}
			GetDateTread gd = new GetDateTread(startDate, endDate, 0, count, sqlSessionTemplate,
					sqlSessionTemplateSys, dataSourceTransactionManager);
			log.info("拉取："+startDate+"--"+endDate+"的"+count+"条数据");
			RunGetDateTread ru = gd.new RunGetDateTread();
			taskExecutor.execute(ru);
			Thread.sleep(10);
	}

	public void test22(String startDate, String endDate) throws InterruptedException {
		SqlSession session3 = sqlSessionTemplateSys.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);

		SysTradeBean bean = new SysTradeBean();
		bean.setStartDate(startDate);
		bean.setEndDate(endDate);
		int count = 100;
		try {
			// 总记录数使用真实数据
			SysTradeBeanMapper sysMapper = session3.getMapper(SysTradeBeanMapper.class);
			count = sysMapper.selectDateCount(bean);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session3.close();
		}
			GetDateTread gd = new GetDateTread(startDate, endDate, 0, count, sqlSessionTemplate,
					sqlSessionTemplateSys, new DataSourceTransactionManager(dataSource),dataSource);
			log.info("拉取："+startDate+"--"+endDate+"的"+count+"条数据");
			RunGetDateTreadPagingSave ru = gd.new RunGetDateTreadPagingSave();
			taskExecutor.execute(ru);
			Thread.sleep(10);
	}
	
	public void test2(String startDate, String endDate) throws InterruptedException {
		SqlSession session3 = sqlSessionTemplateSys.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);

		SysTradeBean bean = new SysTradeBean();
		bean.setStartDate(startDate);
		bean.setEndDate(endDate);
		int count = 100;
		try {
			// 总记录数使用真实数据
			SysTradeBeanMapper sysMapper = session3.getMapper(SysTradeBeanMapper.class);
			count = sysMapper.selectDateCount(bean);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session3.close();
		}
		// 需要线程
		int needThread = 1;
		// 每个线程拉取的记录数
		int size = count / needThread;
		int limit = 0;
		for (int i = 0; i < count / size; i++) {
			if (limit >= count)
				continue;
			GetDateTread gd = new GetDateTread(startDate, endDate, limit, size, sqlSessionTemplate,
					sqlSessionTemplateSys, dataSourceTransactionManager);
			RunGetDateTreadPagingSave ru = gd.new RunGetDateTreadPagingSave();
			taskExecutor.execute(ru);
			limit = (i + 1) * size;
			Thread.sleep(10);
		}
	}

	public void test3(String startDate, String endDate, SqlSession session, SqlSession session2, SqlSession session3) {
		BiTradeOrdersMapper orderMapper = session.getMapper(BiTradeOrdersMapper.class);
		BiTradeGoodsMapper goodsMapper = session2.getMapper(BiTradeGoodsMapper.class);
		SysTradeBeanMapper sysMapper = session3.getMapper(SysTradeBeanMapper.class);
		try {
			Date d1 = new Date();
			log.info(Thread.currentThread().getId() + "------test3-----query--begin---------------------");
			List<SysTradeBean> list = sysMapper.selectByModifyToPage(startDate, endDate, null, null);
			Date d2 = new Date();
			log.info(Thread.currentThread().getId() + "------test3-----query--end---------------------"
					+ (d2.getTime() - d1.getTime()));
			Date d3 = new Date();
			log.info(Thread.currentThread().getId() + "---test3--------insert--begin---------------------");
			int commit = 1;
			for (SysTradeBean sys : list) {
				BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);
				TradeTemplate tr = border.getTrade_fullinfo_get_response().getTrade();
				orderMapper.insertBys(tr);
				for (Order order : tr.getOrders().getOrder()) {
					goodsMapper.insertBys(order);
				}
				commit++;
				if (commit % 2000 == 0 || commit == list.size()) {
					session2.commit();
					session2.clearCache();
					session.commit();
					session.clearCache();
					Date d5 = new Date();
					log.info("-------test3----commit--end---------------------" + commit + "--"
							+ (d5.getTime() - d3.getTime()));
				}
			}
			Date d4 = new Date();
			log.info(Thread.currentThread().getId() + "---test3--------insert--end---------------------"
					+ (d4.getTime() - d3.getTime()));
		} catch (Exception e) {
			log.error(e);
			session.rollback();
			session2.rollback();
			session3.rollback();

		} finally {
			session.close();
			session2.close();
			session3.close();
		}

	}
}
