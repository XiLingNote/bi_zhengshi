package bi.baiqiu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import bi.baiqiu.mapper.GoalMonthSaleMapper;
import bi.baiqiu.pojo.GoalMonthsale;

@Transactional
@Service
public class GoalMonthSaleServiceImpl {
	@Autowired
	private GoalMonthSaleMapper saleMapper;
	
	public List<GoalMonthsale> queryGoalMonthsale(GoalMonthsale GoalMonthsale){
		return saleMapper.queryGoalMonthSale(GoalMonthsale);
		
	}

	/**
	 * 批量删除
	 * 
	 * @param idarray
	 * @return
	 */
	public int deleteGoalMonthsaleByBatch(int[] idarray) {
		return saleMapper.deleteGoalMonthSaleByBatch(idarray);

	}

	/**
	 * 批量增加
	 * 
	 * @param goals
	 * @return
	 */
	public int insertByBatch(List<GoalMonthsale> goals) {
		return saleMapper.insertByBatch(goals);
	}

	/**
	 * 更新
	 * 
	 * @param GoalMonthsale
	 * @return
	 */
	public int insertOrupdateGoalMonthSale(GoalMonthsale goalMonthsale) throws Exception {
		if(goalMonthsale.getId()!=null){
			saleMapper.updateGoalMonthSale(goalMonthsale);
			return Integer.valueOf(goalMonthsale.getId());
		}
		else{
			saleMapper.insertOne(goalMonthsale);
			return goalMonthsale.getId();
		}
	}

	/**
	 * 批量更新
	 * 
	 * @param GoalMonthsale
	 * @return
	 */
	public int updateGoalMonthsaleByBatch(List<GoalMonthsale> GoalMonthsale) {
		return saleMapper.updateGoalMonthSaleBybatch(GoalMonthsale);
	}

	/**
	 * 批量更新和插入
	 * 
	 * @param GoalMonthsale
	 * @return
	 */
	public void insertAndupdateGoalMonthsaleByBatch(List<GoalMonthsale> GoalMonthsales) throws Exception {
		List<GoalMonthsale> insertGoalMonthsales = new ArrayList<GoalMonthsale>();
		List<GoalMonthsale> updateGoalMonthsales = new ArrayList<GoalMonthsale>();

		for (GoalMonthsale goal : GoalMonthsales) {
			if (goal.getId() == null)
				insertGoalMonthsales.add(goal);
			else
				updateGoalMonthsales.add(goal);
		}

		if (insertGoalMonthsales.size() > 0)
			saleMapper.insertByBatch(insertGoalMonthsales);
		if (updateGoalMonthsales.size() > 0)
			saleMapper.updateGoalMonthSaleBybatch(updateGoalMonthsales);

	}

}
