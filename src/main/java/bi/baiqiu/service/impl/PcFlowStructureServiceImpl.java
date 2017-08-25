package bi.baiqiu.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.abel533.entity.Example;

import bi.baiqiu.controller.BaseController;
import bi.baiqiu.mapper.PcFlowStructureTreasureMapper;
import bi.baiqiu.page.PageHelper;
import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.PcFlowStructureBean;
import bi.baiqiu.pojo.User;

@Service
@Transactional
public class PcFlowStructureServiceImpl {
	Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private PcFlowStructureTreasureMapper pcFlowStructureTreasureDao;
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	public String insertByBatchLarge(List<PcFlowStructureBean> beans) {
		Date startDate=new Date();
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		
		PcFlowStructureTreasureMapper mapper = session.getMapper(PcFlowStructureTreasureMapper.class);
		String msgEnd="";
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
