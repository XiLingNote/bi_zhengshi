package bi.baiqiu.mapper;


import java.util.List;

import com.github.abel533.mapper.Mapper;

import bi.baiqiu.pojo.GoalMonthsale;

public interface GoalMonthSaleMapper extends Mapper<GoalMonthsale>{
	/**查询
	 * @return
	 */
	public List<GoalMonthsale> queryGoalMonthSale(GoalMonthsale GoalMonthSale);
	/**查询
	 * @return
	 */
	public int insertOne(GoalMonthsale GoalMonthSale);
	/**批量增加
	 * @param GoalMonthSales
	 * @return
	 */
	public int insertByBatch(List<GoalMonthsale> GoalMonthSales);
	/**增加
	 * @param GoalMonthSales
	 * @return
	 */
	public int insertGoalMonthSale(GoalMonthsale GoalMonthSales);
	/**批量删除
	 * @param idArray
	 * @return
	 */
	public int deleteGoalMonthSaleByBatch(int [] idArray);
	
	/**更新
	 * @param GoalMonthSale
	 * @return
	 */
	public int updateGoalMonthSale( GoalMonthsale GoalMonthSale);
	
	/**批量修改
	 * @param GoalMonthSales
	 * @return
	 */
	public int updateGoalMonthSaleBybatch(List<GoalMonthsale> GoalMonthSales);
}
