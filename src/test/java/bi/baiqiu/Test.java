package bi.baiqiu;

import bi.baiqiu.utils.DateUtils;

public class Test{
	
	public static void main(String[] args) {
		String startDate="2017-01-01";
		String endDate="2017-01-04";
		String 	startAdd=startDate;
		do{
			startAdd=DateUtils.stringDateAddDay(startAdd, 1);
			System.out.println(startAdd);
		}
		while(DateUtils.stringToDate(startAdd).compareTo(DateUtils.stringToDate(endDate))<0);
		}
	
	
}