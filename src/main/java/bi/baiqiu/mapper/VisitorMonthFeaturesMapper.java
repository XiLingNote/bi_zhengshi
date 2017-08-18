package bi.baiqiu.mapper;

import java.util.List;
import com.github.abel533.mapper.Mapper;

import bi.baiqiu.pojo.VisitorMonthFeaturesBean;

/**
 * 粉丝特征月报
 * 
 * @author Administrator
 *
 */
public interface VisitorMonthFeaturesMapper extends Mapper<VisitorMonthFeaturesBean> {
	/**
	 * 分页查询默认十条记录
	 * 
	 * @param bean
	 * @param page
	 * @return
	 */
	public List<VisitorMonthFeaturesBean> queryByPage(VisitorMonthFeaturesBean bean);

	/**
	 * 批量增加
	 * 
	 * @param pcFlowStructureBeans
	 * @return
	 */
	public int insertByBatch(List<VisitorMonthFeaturesBean> Beans);
	/**
	 * 批量单条增加
	 * 
	 * @param pcFlowStructureBeans
	 * @return
	 */
	public int insertOne(VisitorMonthFeaturesBean Bean);
	/**
	 * 批量删除
	 * 
	 * @param idArray
	 * @return
	 */
	public int deleteByBatch(int[] idArray);
	/**批量删除权限判定
	 * @param 
	 * @return
	 */
	public int deleteByBatchList(VisitorMonthFeaturesBean bean);
}
