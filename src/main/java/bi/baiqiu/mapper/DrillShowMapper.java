package bi.baiqiu.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import bi.baiqiu.pojo.DrillShowBean;

/**
 * 钻展
 * 
 * @author Administrator
 *
 */
public interface DrillShowMapper extends Mapper<DrillShowBean> {
	/**
	 * 分页查询默认十条记录
	 * 
	 * @param bean
	 * @param page
	 * @return
	 */
	public List<DrillShowBean> queryByPage(DrillShowBean bean);

	/**
	 * 批量增加
	 * 
	 * @param pcFlowStructureBeans
	 * @return
	 */
	public int insertByBatch(List<DrillShowBean> Beans);
	/**
	 * 批量增加
	 * 
	 * @param pcFlowStructureBeans
	 * @return
	 */
	public int insertOne(DrillShowBean Bean);
	/**批量删除
	 * @param idArray
	 * @return
	 */
	public int deleteByBatch(int []idArray);
	/**批量删除权限判定
	 * @param 
	 * @return
	 */
	public int deleteByBatchList(DrillShowBean bean);
}
