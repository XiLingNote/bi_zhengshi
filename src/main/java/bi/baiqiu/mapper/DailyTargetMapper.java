package bi.baiqiu.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;

import bi.baiqiu.page.PageBean;
import bi.baiqiu.pojo.DailyTarget;

public interface DailyTargetMapper extends Mapper<DailyTarget> {
	
	public List<Map<String, String>>getDailyMonthTargetNum(DailyTarget daily);
	
	/**分页查询
	 * @param daily
	 * @param page
	 * @return
	 */
	public List<DailyTarget> queryDailyTarget(DailyTarget daily,PageBean page);
	/**批量增加日计划
	 * @param list
	 * @return
	 */
	public int insertByBatch(List<DailyTarget> list);
	
	/**批量删除日计划
	 * @param arrary
	 * @return
	 */
	public int batchDeleteDailyTarget(int [] arrary); 
	/**更新
	 * @param daily
	 * @return
	 */
	public int updateDaliypTarget(DailyTarget daily);
	/**更新批量
	 * @param daily
	 * @return
	 */
	public int updateDaliypTargetByBatch(List<DailyTarget> list);
}
