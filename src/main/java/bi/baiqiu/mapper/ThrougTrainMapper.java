package bi.baiqiu.mapper;

import java.util.List;
import com.github.abel533.mapper.Mapper;

import bi.baiqiu.pojo.ThrougTrainBean;
/**
 * 直通车
 * 
 * @author Administrator
 *
 */
public interface ThrougTrainMapper extends Mapper<ThrougTrainBean> {
	/**
	 * 分页查询默认十条记录
	 * 
	 * @param bean
	 * @param page
	 * @return
	 */
	public List<ThrougTrainBean> queryByPage(ThrougTrainBean bean);

	/**
	 * 批量增加
	 * 
	 * @param pcFlowStructureBeans
	 * @return
	 */
	public int insertByBatch(List<ThrougTrainBean> Beans);
	/**
	 * 批量增加
	 * 
	 * @param pcFlowStructureBeans
	 * @return
	 */
	public int insertOne(ThrougTrainBean Bean);
	/**批量删除
	 * @param idArray
	 * @return
	 */
	public int deleteByBatch(int []idArray);
	/**批量删除权限判定
	 * @param 
	 * @return
	 */
	public int deleteByBatchList(ThrougTrainBean bean);
}
