package bi.baiqiu.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;

import bi.baiqiu.pojo.PcFlowStructureBean;

/**
 * pc流量结构操作
 * @author Administrator
 *
 */
public interface PcFlowStructureTreasureMapper  extends Mapper<PcFlowStructureBean>{
	/**分页查询默认十条记录
	 * @param bean
	 * @param page
	 * @return
	 */
	public List<PcFlowStructureBean> queryByPage(PcFlowStructureBean bean);
	
	
	/**批量增加
	 * @param pcFlowStructureBeans
	 * @return
	 */
	public int insertByBatch(List<PcFlowStructureBean> pcFlowStructureBeans);
	
	/**批量增加
	 * @param pcFlowStructureBeans
	 * @returnr
	 */
	public int insertOne(PcFlowStructureBean pcFlowStructureBean);
	/**批量删除
	 * @param idArray
	 * @return
	 */
	public int deleteByBatch(int[]idArray);
	
	/**权限判定删除
	 * @param bean
	 * @return
	 */
	public int deleteByBatchList(PcFlowStructureBean bean);
}
