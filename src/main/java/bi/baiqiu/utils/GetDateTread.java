package bi.baiqiu.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.taobao.api.domain.Order;
import bi.baiqiu.mapper.BiTradeGoodsMapper;
import bi.baiqiu.mapper.BiTradeOrdersMapper;
import bi.baiqiu.mapper2.SysTradeBeanMapper;
import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo2.SysTradeBean;
import bi.baiqiu.pojo.test.BorderBean;
import bi.baiqiu.pojo.test.TradeTemplate;

public class GetDateTread {
Logger log=Logger.getLogger(this.getClass());
	private int pageNum;
	private int pageSize;
	private SqlSessionTemplate sqlSessionTemplate;
	private TaskExecutor taskExecutor;
	private  DataSourceTransactionManager dtx;
	private String startDate;
	private String endDate;
	private SqlSessionTemplate sqlSessionTemplateSys;
	private DruidDataSource dataSource;
	public GetDateTread(TaskExecutor taskExecutor) {
		this.setTaskExecutor(taskExecutor);
	}

	public GetDateTread(String startDate,String endDate,int pageNum, int pageSize, SqlSessionTemplate sqlSessionTemplate,SqlSessionTemplate sqlSessionTemplateSys,DataSourceTransactionManager dtx) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.sqlSessionTemplateSys=sqlSessionTemplateSys;
		this.dtx=dtx;
		this.startDate=startDate;
		this.endDate=endDate;
	}
	public GetDateTread(String startDate,String endDate,int pageNum, int pageSize, SqlSessionTemplate sqlSessionTemplate,SqlSessionTemplate sqlSessionTemplateSys,DataSourceTransactionManager dtx,DruidDataSource dataSource) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.sqlSessionTemplateSys=sqlSessionTemplateSys;
		this.dtx=dtx;
		this.startDate=startDate;
		this.endDate=endDate;
		this.dataSource=dataSource;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public DataSourceTransactionManager getDtx() {
		return dtx;
	}

	public void setDtx(DataSourceTransactionManager dtx) {
		this.dtx = dtx;
	}
	public class RunGetDateTread implements Runnable {
		@Override
		public void run() {
			Date d0 = new Date();
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = dtx.getTransaction(def);	
			SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			SqlSession session2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			SqlSession session3 = sqlSessionTemplateSys.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			
			BiTradeOrdersMapper orderMapper = session.getMapper(BiTradeOrdersMapper.class);
			BiTradeGoodsMapper goodsMapper = session2.getMapper(BiTradeGoodsMapper.class);
			SysTradeBeanMapper sysMapper = session3.getMapper(SysTradeBeanMapper.class);
			try {
				Date d1 = new Date();
				List<SysTradeBean> list = sysMapper.selectByPage(new Page<SysTradeBean>(pageNum, pageSize));
				Date d2 = new Date();
				log.info(Thread.currentThread().getId()+"-----------Runquery--end---------------------" + (d2.getTime() - d1.getTime()));
				Date d3 = new Date();
				int commit = 1;
//				创建临时表
				for (SysTradeBean sys : list) {
					BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);
					TradeTemplate tr = border.getTrade_fullinfo_get_response().getTrade();
					orderMapper.insertBys(tr);
					for (Order order : tr.getOrders().getOrder()) {
						goodsMapper.insertBys(order);
					}
					commit++;
					if (commit % 2000 == 0 || commit == list.size()) {
						forEachCom(session);
						forEachCom(session2);
					}
				}
				Date d4 = new Date();
				dtx.commit(status);
				Date d5 = new Date();
				log.info(Thread.currentThread().getId()+"-----------Runinsert--end---------------------" + (d4.getTime() - d3.getTime()));
				log.info(Thread.currentThread().getId()+"-------------end---------------------" + (d5.getTime() - d0.getTime()));
			} catch (Exception e) {
				log.error(e);
				session.rollback();
				session2.rollback();
				session3.rollback();
				dtx.rollback(status);
			} finally {
				session.close();
				session2.close();
				session3.close();
			}
		}

	}
	
	/**分页保存
	 * 	@author Jared
	 *	@time 2017年8月22日上午9:49:37
	 */
	public class RunGetDateTreadPagingTime implements Runnable {
		@Override
		public void run() {
			Date d0 = new Date();
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = dtx.getTransaction(def);	
			SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			SqlSession session2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			SqlSession session3 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			
			BiTradeOrdersMapper orderMapper = session.getMapper(BiTradeOrdersMapper.class);
			BiTradeGoodsMapper goodsMapper = session2.getMapper(BiTradeGoodsMapper.class);
			SysTradeBeanMapper sysMapper = session3.getMapper(SysTradeBeanMapper.class);
			try {
				Date d1 = new Date();
				ForEachGetAndSave(pageNum, pageSize, session, session2, goodsMapper, orderMapper, status);
				dtx.commit(status);
				Date d5 = new Date();
				//数据转入正式表
				log.info(Thread.currentThread().getId()+"-------------end---------------------" + (d5.getTime() - d0.getTime()));
			} catch (Exception e) {
				e.printStackTrace();
				session.rollback();
				session2.rollback();
				session3.rollback();
				dtx.rollback(status);
			} finally {
				session.close();
				session2.close();
				session3.close();
			}
		}

	}
	
	/**不页保存
	 * 	@author Jared
	 *	@time 2017年8月22日上午9:49:37
	 */
	public class RunGetDateTreadSave implements Runnable {
		@Override
		public void run() {
			Date d0 = new Date();
			SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			SqlSession session2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			BiTradeOrdersMapper orderMapper = session.getMapper(BiTradeOrdersMapper.class);
			BiTradeGoodsMapper goodsMapper = session2.getMapper(BiTradeGoodsMapper.class);
			try {
				Date d1 = new Date();
				GetAndSave(pageNum, pageSize, session, session2, goodsMapper, orderMapper, null);
				Date d5 = new Date();
				//数据转入正式表
				//dtx.commit(status);
				Date d6 = new Date();
				log.info(Thread.currentThread().getId()+"-------------end---------------------" + (d5.getTime() - d0.getTime()));
				log.info(Thread.currentThread().getId()+"-----------dtx--commit--end---------------------" + (d6.getTime() - d5.getTime()));

			} catch (Exception e) {
				e.printStackTrace();
				session.rollback();
				session2.rollback();
			} finally {
				session.close();
				session2.close();
				log.info(Thread.currentThread().getId()+":执行完成");
			}
		}

	}
	/**分页保存
	 * 	@author Jared
	 *	@time 2017年8月22日上午9:49:37
	 */
	public class RunGetDateTreadPagingSave implements Runnable {
		@Override
		public void run() {
			Date d0 = new Date();
			SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			SqlSession session2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			BiTradeOrdersMapper orderMapper = session.getMapper(BiTradeOrdersMapper.class);
			BiTradeGoodsMapper goodsMapper = session2.getMapper(BiTradeGoodsMapper.class);
			try {
				Date d1 = new Date();
				ForEachGetAndSave(pageNum, pageSize, session, session2, goodsMapper, orderMapper, null);
				Date d5 = new Date();
				//数据转入正式表
				//dtx.commit(status);
				Date d6 = new Date();
				log.info(Thread.currentThread().getId()+"-------------end---------------------" + (d5.getTime() - d0.getTime()));
				log.info(Thread.currentThread().getId()+"-----------dtx--commit--end---------------------" + (d6.getTime() - d5.getTime()));

			} catch (Exception e) {
				e.printStackTrace();
				session.rollback();
				session2.rollback();
			} finally {
				session.close();
				session2.close();
				log.info(Thread.currentThread().getId()+":执行完成");
			}
		}

	}
	public class RunGetDateTreadTemp implements Runnable {
		@Override
		public void run() {
			Date d0 = new Date();
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status = dtx.getTransaction(def);	
			SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			SqlSession session2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			
			BiTradeOrdersMapper orderMapper = session.getMapper(BiTradeOrdersMapper.class);
			BiTradeGoodsMapper goodsMapper = session2.getMapper(BiTradeGoodsMapper.class);
			
			try {
				Page<SysTradeBean> page = new Page<>(pageNum, pageSize);
				Date d1 = new Date();
				List<SysTradeBean> list = getSysInfo(pageNum,pageSize);
				Date d2 = new Date();
				log.info(Thread.currentThread().getId()+"-----------Runquery--end---------------------" + (d2.getTime() - d1.getTime()));
				Date d3 = new Date();
				int commit = 1;
//				创建临时表
				goodsMapper.createTempTable();
				orderMapper.createTempTable();
				//插入临时表数据
				for (SysTradeBean sys : list) {
					BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);
					TradeTemplate tr = border.getTrade_fullinfo_get_response().getTrade();
					log.info(sys.getJdpResponse());
					orderMapper.insertTempTableBys(tr);
					for (Order order : tr.getOrders().getOrder()) {
						goodsMapper.insertTempTableBys(order);
					}
					commit++;
					if (commit % 2000 == 0 || commit == list.size()) {
						forEachCom(session);
						forEachCom(session2);
					}
					
				}
				goodsMapper.getDateFromTempTable();
				orderMapper.getDateFromTempTable();
				log.info("==============临时表的条数："+orderMapper.getTempCount()+"========================");
				Date d4 = new Date();
				//数据转入正式表
				log.info(Thread.currentThread().getId()+"-----------Runinsert--end---------------------" + (d4.getTime() - d3.getTime()));
			} catch (Exception e) {
				e.printStackTrace();
				session.rollback();
				session2.rollback();
				dtx.rollback(status);
			} finally {
				session.close();
				session2.close();
			}
		}

	}
	/**commit出错时50毫秒后再次提交
	 * @param session
	 * @throws InterruptedException
	 */
	public synchronized void forEachCom(SqlSession session) throws InterruptedException{
		try {
			session.commit();
		} catch (Exception e) {
			Thread.sleep(50);
			forEachCom(session);
		}
	}
	
	public synchronized void forEachSaveOrders(BiTradeOrdersMapper orderMapper) throws InterruptedException{
		try {
			orderMapper.getDateFromTempTable();
		} catch (Exception e) {
			Thread.sleep(50);
			forEachSaveOrders(orderMapper);
			e.printStackTrace();
		}
	}
	public synchronized void forEachSaveGoods(BiTradeOrdersMapper orderMapper) throws InterruptedException{
		try {
			orderMapper.getDateFromTempTable();
		} catch (Exception e) {
			Thread.sleep(50);
			forEachSaveGoods(orderMapper);
			e.printStackTrace();
		}
	}
	/**超出一定数量分批进行查询保存
	 * @param pageNum
	 * @param pageSize
	 * @param session
	 * @param session2
	 * @param sysMapper
	 * @param goodsMapper
	 * @param orderMapper
	 * @throws Exception
	 */
	public   void  GetAndSave(int pageNum,int pageSize,SqlSession session,SqlSession session2,BiTradeGoodsMapper goodsMapper,BiTradeOrdersMapper orderMapper,TransactionStatus status) throws Exception{
			
		log.info("pageNum:"+pageNum+"pageSize"+pageSize+"+++++++++++++++++++++++++++++");

	
			List<SysTradeBean>list = getSysInfo(pageNum,pageSize);
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status1 = dtx.getTransaction(def);
			BiTradeOrdersMapper orderMapper1 = session.getMapper(BiTradeOrdersMapper.class);
			BiTradeGoodsMapper goodsMapper1 = session2.getMapper(BiTradeGoodsMapper.class);
			log.info("解析出了对象："+list.size());
			int i=0;
			for (SysTradeBean sys : list) {
				i++;
				if(i==list.size()){
					log.info(sys.getTid());
				}
				BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);
				TradeTemplate tr = border.getTrade_fullinfo_get_response().getTrade();
				orderMapper1.insertBysBefore2017(tr);
				for (Order order : tr.getOrders().getOrder()) {
					goodsMapper1.insertBysBefore2017(order);
				}
			}
			forEachCom(session);
			forEachCom(session2);
			dtx.commit(status1);
			session2.clearCache();
			session.clearCache();
		
	}
	
	/**超出一定数量分批进行查询保存
	 * @param pageNum
	 * @param pageSize
	 * @param session
	 * @param session2
	 * @param sysMapper
	 * @param goodsMapper
	 * @param orderMapper
	 * @throws Exception
	 */
	public   void  ForEachGetAndSave(int pageNum,int pageSize,SqlSession session,SqlSession session2,BiTradeGoodsMapper goodsMapper,BiTradeOrdersMapper orderMapper,TransactionStatus status) throws Exception{
			
		log.info("pageNum:"+pageNum+"pageSize"+pageSize+"+++++++++++++++++++++++++++++");
		if(pageSize>4000){
			
			int dichotomySize=pageSize/2;
			
			ForEachGetAndSave(pageNum, dichotomySize, session, session2, goodsMapper, orderMapper,status);
			
			ForEachGetAndSave(pageNum+dichotomySize, dichotomySize, session, session2, goodsMapper, orderMapper,status);
			log.info("pageNum:"+pageNum+"pageSize"+dichotomySize+"+++++++++++++++++++++++++++++");
		}else{
	
			List<SysTradeBean>list = getSysInfo(pageNum,pageSize);
			DataSourceTransactionManager dtx=new DataSourceTransactionManager(dataSource);
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			TransactionStatus status1 = dtx.getTransaction(def);
			BiTradeOrdersMapper orderMapper1 = session.getMapper(BiTradeOrdersMapper.class);
			BiTradeGoodsMapper goodsMapper1 = session2.getMapper(BiTradeGoodsMapper.class);
			log.info("解析出了对象："+list.size());
			int i=0;
			for (SysTradeBean sys : list) {
				i++;
				if(i==list.size()){
					log.info(sys.getTid());
				}
				BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);
				TradeTemplate tr = border.getTrade_fullinfo_get_response().getTrade();
				orderMapper1.insertBysBefore2017(tr);
				for (Order order : tr.getOrders().getOrder()) {
					goodsMapper1.insertBysBefore2017(order);
				}
			}
			forEachCom(session);
			forEachCom(session2);
			dtx.commit(status1);
			session2.clearCache();
			session.clearCache();
		}
		
	}
	public synchronized  List<SysTradeBean> getSysInfo(int pageNum,int pageSize){
		List<SysTradeBean> list=new ArrayList<SysTradeBean>();
		SqlSession session3 = sqlSessionTemplateSys.getSqlSessionFactory().openSession(ExecutorType.SIMPLE, true);
		try {
		
			SysTradeBeanMapper sysMapper = session3.getMapper(SysTradeBeanMapper.class);
			list = sysMapper.selectByModifyToPage(startDate,endDate,pageNum,pageSize);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			session3.close();
		}
		return list;
	}
}
