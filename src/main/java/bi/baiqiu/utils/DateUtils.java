package bi.baiqiu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtils {

	public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat BYDAY = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
	public static final SimpleDateFormat DAY = new SimpleDateFormat("dd", Locale.getDefault());
	public static final SimpleDateFormat BYMONTH = new SimpleDateFormat("yyyyMM", Locale.getDefault());
	public static final SimpleDateFormat MONTH = new SimpleDateFormat("MM", Locale.getDefault());
	public static final SimpleDateFormat BYYEAR = new SimpleDateFormat("yyyy", Locale.getDefault());
	public static SimpleDateFormat YMDSin1 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat YMDSin2 = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat MMYsin = new SimpleDateFormat("mmm-yy");

	private static Calendar calendar = Calendar.getInstance();
	// @Fields today : 不可以直接取，设定private
	private static Date today;
	private static Date yesterday;

	public static Date getToday() {
		today = new Date();
		return today;
	}

	public static Date getYesterday() {
		calendar.setTime(getToday());
		calendar.add(calendar.DATE, -1);// 把日期往前增加一天.整数往后推,负数往前移动
		yesterday = calendar.getTime(); // 这个时间就是表示昨天
		return yesterday;
	}

	/**
	 * @Function: DateUtils.java
	 * @Description: 该函数的功能描述 获取指定数字的日期集合，如section为7，则返回从昨天算起的前七天的日期
	 *
	 * @param:参数描述 指定时间区间
	 * @return：返回结果描述 String类型的日期集合
	 * @throws：异常描述
	 *
	 * @version: v1.0.0
	 * @author: FengCheng
	 * @date: 2017年7月24日 上午11:44:13
	 *
	 *        Modification History: Date Author Version Description
	 *        ---------------------------------------------------------*
	 *        2017年7月24日 FengCheng v1.0.0 修改原因
	 */

	public static List<String> getRecentlyDate(int section) {
		getToday();
		Date date;
		String thatDay;
		List<String> recentlyDate = new ArrayList<String>();
		section = section - 2 * section;// 参数正转负
		for (int i = section; i < 0; i++) {
			calendar.setTime(today);
			calendar.add(calendar.DATE, i);
			date = calendar.getTime();
			thatDay = getyyyyMMdd(date);
			recentlyDate.add(thatDay);
		}
		return recentlyDate;
	}

	public static int getdd() {
		return calendar.get(Calendar.DATE);
	}

	/**
	 * @return 0-11
	 */
	public static int getMM() {
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * @return 2017
	 */
	public static int getyyyy() {

		return calendar.get(Calendar.YEAR);
	}

	public static String getyyyyMMdd(Date date) {

		return BYDAY.format(date);
	}

	public static String dd(Date date) {

		return DAY.format(date);
	}

	public static String getyyyyMM(Date date) {

		return BYMONTH.format(date);
	}

	public static String getMM(Date date) {

		return MONTH.format(date);
	}

	/**
	 * @param date
	 * @return 117 1900 开始 2017 :117
	 */
	public static String getyyyy(Date date) {

		return BYYEAR.format(date);
	}

	public static Boolean isTheEndOfMonth(Date date) {
		int day = date.getDate();
		int month = date.getMonth();
		int year = date.getYear();
		int month2 = new Date(year, month, day + 1).getMonth();

		return !(month == month2);
	}

	/**
	 * @param date
	 * @return true :是第一天
	 */
	public static Boolean isTheBeginOfMonth(Date date) {
		int day = date.getDate();
		return day == 1;
	}

	/**
	 * @param date
	 * @return true :不是最后一天
	 */
	public static boolean isTheEndOfYear(Date date) {
		int day = date.getDate();
		int month = date.getYear();
		int year = date.getYear();
		int year2 = new Date(year, month + 1, day).getYear();

		return !(year == year2);
	}

	public static boolean sameyyyyMM(Date begin, Date end) {
		String getyyyyMM = DateUtils.getyyyyMM(begin);
		String getyyyyMM2 = DateUtils.getyyyyMM(end);
		return getyyyyMM.equals(getyyyyMM2);

	}

	@SuppressWarnings("deprecation")
	public static String getLastyyyyMM(Date date) {
		int y = date.getYear() + 1900;

		int m = date.getMonth();
		String mm = (m > 9) ? "" + m : "0" + m;
		int d = date.getDate();
		return "" + y + mm;
	}

	/**
	 * 验证字符串是否是正确的日期格式;严格验证
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isDateType(String date) {
		try {
			YMDSin2.setLenient(false);
			YMDSin2.parse(date);
			return true;
		} catch (ParseException e) {
			YMDSin1.setLenient(false);
			try {
				YMDSin1.parse(date);
				return true;
			} catch (ParseException e1) {
			}
		}
		return false;
	}

	/**
	 * 传入日期大于当前日期的两个月前比较
	 * 
	 * @param date
	 * @return 日期在两个月之前返回true
	 * @throws ParseException
	 */
	public static boolean compareMonthsAgo(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());// 设置当前日期
		calendar.add(Calendar.MONTH, -2);// 月份减一
		if (calendar.getTime().getTime() > YMDSin1.parse(date).getTime()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据字符串转为日期
	 * 
	 * @param Date
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String date) {
		try {
			return YMDSin1.parse(date);
		} catch (ParseException e) {
			try {
				return YMDSin2.parse(date);
			} catch (ParseException e1) {
				try {
					return MMYsin.parse(date);
				} catch (ParseException e2) {
					return null;
				}
			}
		}
	}
}
