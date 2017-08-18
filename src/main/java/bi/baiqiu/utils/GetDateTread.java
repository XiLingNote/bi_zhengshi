package bi.baiqiu.utils;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSON;
import com.taobao.api.domain.Order;

import bi.baiqiu.mapper.BiTradeGoodsMapper;
import bi.baiqiu.mapper.BiTradeOrdersMapper;
import bi.baiqiu.mapper.SysTradeBeanMapper;
import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.SysTradeBean;
import bi.baiqiu.pojo.test.BorderBean;
import bi.baiqiu.pojo.test.Trade22;

public class GetDateTread {
Logger log=Logger.getLogger(this.getClass());
	private int pageNum;
	private int pageSize;
	private SqlSessionTemplate sqlSessionTemplate;
	private TaskExecutor taskExecutor;
	private DataSourceTransactionManager dtx;
	public GetDateTread(TaskExecutor taskExecutor) {
		this.setTaskExecutor(taskExecutor);
	}

	public GetDateTread(int pageNum, int pageSize, SqlSessionTemplate sqlSessionTemplate,DataSourceTransactionManager dtx) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.sqlSessionTemplate = sqlSessionTemplate;
		this.setDtx(dtx);
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
				Page<SysTradeBean> page = new Page<>(pageNum, pageSize);
				Date d1 = new Date();
				log.info(Thread.currentThread().getId()+"-----------Runquery--begin---------------------");
				List<SysTradeBean> list = sysMapper.selectByPage(page);
				Date d2 = new Date();
				log.info(Thread.currentThread().getId()+"-----------Runquery--end---------------------" + (d2.getTime() - d1.getTime()));
				Date d3 = new Date();
				log.info(Thread.currentThread().getId()+"-----------Runinsert--begin---------------------");
				int commit = 1;
			
				for (SysTradeBean sys : list) {
					BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);
					Trade22 tr = border.getTrade_fullinfo_get_response().getTrade();
					orderMapper.insertBys(tr);
					for (Order order : tr.getOrders().getOrder()) {
						goodsMapper.insertBys(order);
					}
					commit++;
					if (commit % 2000 == 0 || commit == list.size()) {
						forEachCom(session);
						forEachCom(session2);
						
						Date d5 = new Date();	
						log.info("-----------Runcommit--end---------------------" + commit + "--"
								+ (d5.getTime() - d3.getTime()));
					log.info(list.get(commit-1));
					}
					
				}
				Date d4 = new Date();
				dtx.commit(status);
				log.info(Thread.currentThread().getId()+"-----------Runinsert--end---------------------" + (d4.getTime() - d3.getTime()));
			
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
	public synchronized void forEachCom(SqlSession session) throws InterruptedException{
		try {
			session.commit();
		} catch (Exception e) {
			Thread.sleep(50);
			forEachCom(session);
		}
	}

}
