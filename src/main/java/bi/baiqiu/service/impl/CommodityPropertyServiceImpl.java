package bi.baiqiu.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bi.baiqiu.controller.BaseController;
import bi.baiqiu.mapper.CommodityPropertyMapper;
import bi.baiqiu.page.PageHelper;
import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.CommodityPropertyBean;

@Service
@Transactional
public class CommodityPropertyServiceImpl {
	Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private CommodityPropertyMapper commodityPropertyDao;
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	public String insertByBatchLarge(List<CommodityPropertyBean> beans) {
		Date startDate=new Date();
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		
		CommodityPropertyMapper mapper = session.getMapper(CommodityPropertyMapper.class);
		try {
			for (int i = 0; i < beans.size(); i++) {
				mapper.insertOne(beans.get(i));
				if (i % 4000 == 0 || i == beans.size() - 1) {
					// 手动每4000个一提交，提交后无法回滚
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
			return BaseController.savesErroy;
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
	public Page<CommodityPropertyBean> queryByPage(CommodityPropertyBean bean, Integer pageNum, Integer pageSize)
			throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		commodityPropertyDao.queryByPage(bean);
		return PageHelper.endPage();

	}

	/**
	 * 查询特征和属性
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String[]>> queryFeatureAndProperty(Integer shopId) throws Exception {
		return commodityPropertyDao.queryFeatureAndProperty(shopId);
	}
	/**
	 * 查询特征和属性
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String[]>> queryFeatureAndProperty2(Integer shopId,String feature) throws Exception {
		return commodityPropertyDao.queryFeatureAndProperty2(shopId,feature);
	}
	/**
	 * 批量增加
	 * 
	 * @param list
	 * @return
	 */
	public int insertByBatch(List<CommodityPropertyBean> list) {

		return commodityPropertyDao.insertByBatch(list);

	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	public int insertCommodityProperty(CommodityPropertyBean bean) {

		return commodityPropertyDao.insert(bean);
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	public int updateCommodityProperty(CommodityPropertyBean bean) {

		return commodityPropertyDao.updateByPrimaryKeySelective(bean);
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 * @return
	 */
	public int deleteCommodityProperty(CommodityPropertyBean bean) {

		return commodityPropertyDao.deleteByPrimaryKey(bean);
	}

	/**
	 * 批量删除
	 * 
	 * @param idArray
	 * @return
	 */
	public int deleteCommodityPropertyByBatch(int[] idArray) {

		return commodityPropertyDao.deleteByBatch(idArray);
	}

}
