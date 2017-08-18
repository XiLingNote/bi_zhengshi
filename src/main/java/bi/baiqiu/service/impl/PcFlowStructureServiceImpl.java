package bi.baiqiu.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.abel533.entity.Example;
import com.taobao.api.domain.Order;

import bi.baiqiu.controller.BaseController;
import bi.baiqiu.mapper.BiTradeGoodsMapper;
import bi.baiqiu.mapper.BiTradeOrdersMapper;
import bi.baiqiu.mapper.PcFlowStructureTreasureMapper;
import bi.baiqiu.mapper.SysTradeBeanMapper;
import bi.baiqiu.page.PageHelper;
import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.PcFlowStructureBean;
import bi.baiqiu.pojo.SysTradeBean;
import bi.baiqiu.pojo.User;
import bi.baiqiu.pojo.test.BorderBean;
import bi.baiqiu.pojo.test.Trade22;
import bi.baiqiu.utils.GetDateTread;
import bi.baiqiu.utils.GetDateTread.RunGetDateTread;

@Service
@Transactional
public class PcFlowStructureServiceImpl {
	Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private PcFlowStructureTreasureMapper pcFlowStructureTreasureDao;
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	SysTradeBeanMapper sysDao;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	public String insertByBatchLarge(List<PcFlowStructureBean> beans) {
		
		Date startDate=new Date();
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		
		PcFlowStructureTreasureMapper mapper = session.getMapper(PcFlowStructureTreasureMapper.class);
		String msgEnd="";
		Date d1 = new Date();
		//test2();
		Date d2 = new Date();
		log.info("-----test2 Thread------runTime---------------------" + (d2.getTime() - d1.getTime()));
		Date d3 = new Date();
			test();
		Date d4 = new Date();
		log.info("-----test Thread------runTime---------------------" + (d3.getTime() - d4.getTime()));
		
		//test3();
		try {
			for (int i = 0; i < beans.size(); i++) {
				mapper.insertOne(beans.get(i));
				if (i % 4000 == 0 || i == beans.size() - 1) {
					// 手动每4000个一提交，提交后无法回滚
					msgEnd=beans.get(i).getDate();
					session.commit();
					// 清理缓存，防止溢出
					session.clearCache();
				}
			}
			log.info("----------------------------insertTime"+(new Date().getTime()-startDate.getTime()));
			return BaseController.commintSuccess+beans.size();
			
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			return BaseController.commintErroy+msgEnd;
		} finally {
			session.close();
		}
		
	}
public void test(){
	
	SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	SqlSession session2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	try {
		Page<SysTradeBean> page = new Page<>(0, 100);
		Date d1 = new Date();
		log.info("-----------query--begin---------------------");
		List<SysTradeBean> list = sysDao.selectByPage(page);
		Date d2 = new Date();
		log.info("-----------query--end---------------------" + (d2.getTime() - d1.getTime()));
		Date d3 = new Date();
		log.info("-----------insert--begin---------------------");
		BiTradeOrdersMapper orderMapper = session.getMapper(BiTradeOrdersMapper.class);
		BiTradeGoodsMapper goodsMapper = session2.getMapper(BiTradeGoodsMapper.class);
		int commit=1;
		for (SysTradeBean sys : list) {
			BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);
			Trade22 tr=border.getTrade_fullinfo_get_response().getTrade();
			orderMapper.insertBys(tr);
			for(Order order:tr.getOrders().getOrder()){
				goodsMapper.insertBys(order);
			}
			commit++;
			if(commit%2000==0||commit==list.size())
			{
				session2.commit();
				 session2.clearCache();
				Date d5 = new Date();
				log.info("-----------commit--end---------------------"+commit+"--"+ (d5.getTime() - d3.getTime()));
			}
		}
		Date d4 = new Date();
		log.info("-----------insert--end---------------------" + (d4.getTime() - d3.getTime()));
	} catch (Exception e) {
		e.printStackTrace();
		session.rollback();
		session2.rollback();
	}
	finally {
		session.close();
		session2.close();
	}

}

	/**
	 * 分页查询方法s
	 * 
	 * @param bean
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<PcFlowStructureBean> queryByPage(PcFlowStructureBean bean, Integer pageNum, Integer pageSize)
			throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		pcFlowStructureTreasureDao.queryByPage(bean);
		return PageHelper.endPage();

	}

	/**
	 * 批量增加
	 * 
	 * @param list
	 * @return
	 */
	public int insertByBatch(List<PcFlowStructureBean> list) {

		return pcFlowStructureTreasureDao.insertByBatch(list);

	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	public int insertPcFlowStructure(PcFlowStructureBean bean) {

		return pcFlowStructureTreasureDao.insert(bean);
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	public int updatePcFlowStructure(PcFlowStructureBean bean) {

		return pcFlowStructureTreasureDao.updateByPrimaryKey(bean);
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 * @return
	 */
	public int deletePcFlowStructure(PcFlowStructureBean bean ,User user) {

	Example example=new Example(PcFlowStructureBean.class);
	example.createCriteria().andEqualTo("date", bean.getDate()).andEqualTo("shopId", user.getShopId());
		return pcFlowStructureTreasureDao.deleteByExample(example);
	}

	/**
	 * 批量删除
	 * 
	 * @param idArray
	 * @return
	 */
	public int deletePcFlowStructureByBatch(int[] idArray) {
		return pcFlowStructureTreasureDao.deleteByBatch(idArray);
	}
	/**
	 * 批量删除
	 * 
	 * @param idArray
	 * @return
	 */
	public int deletePcFlowStructureByBatchList(PcFlowStructureBean bean) {
		return pcFlowStructureTreasureDao.deleteByBatchList(bean);
	}
}
