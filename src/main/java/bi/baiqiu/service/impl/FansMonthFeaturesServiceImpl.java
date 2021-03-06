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

import bi.baiqiu.controller.BaseController;
import bi.baiqiu.mapper.FansMonthFeaturesMapper;
import bi.baiqiu.page.PageHelper;
import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.FansMonthFeaturesBean;

@Service
@Transactional
public class FansMonthFeaturesServiceImpl {
	Logger log=Logger.getLogger(this.getClass());
	@Autowired
	private FansMonthFeaturesMapper fansMonthFeaturesDao;
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	/**大数据批量增加
	 * @param beans
	 * @return
	 */
	public String insertByBatchLarge(List<FansMonthFeaturesBean> beans) {
		Date startDate=new Date();
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		
		FansMonthFeaturesMapper mapper = session.getMapper(FansMonthFeaturesMapper.class);
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
	public Page<FansMonthFeaturesBean> queryByPage(FansMonthFeaturesBean bean, Integer pageNum, Integer pageSize)
			throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		fansMonthFeaturesDao.queryByPage(bean);
		return PageHelper.endPage();

	}

	/**
	 * 批量增加
	 * 
	 * @param list
	 * @return
	 */
	public int insertByBatch(List<FansMonthFeaturesBean> list) {

		return fansMonthFeaturesDao.insertByBatch(list);

	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	public int insertFansMonthFeatures(FansMonthFeaturesBean bean) {

		return fansMonthFeaturesDao.insert(bean);
	}

	/**
	 * 更新
	 * 
	 * @param bean
	 * @return
	 */
	public int updateFansMonthFeatures(FansMonthFeaturesBean bean) {

		return fansMonthFeaturesDao.updateByPrimaryKey(bean);
	}

	/**
	 * 删除
	 * 
	 * @param bean
	 * @return
	 */
	public int deleteFansMonthFeatures(FansMonthFeaturesBean bean) {

		return fansMonthFeaturesDao.deleteByPrimaryKey(bean);
	}

	/**
	 * 批量删除
	 * 
	 * @param idArray
	 * @return
	 */
	public int deleteFansMonthFeaturesByBatch(FansMonthFeaturesBean bean) {

		return fansMonthFeaturesDao.deleteByBatchList(bean);
	}

}
