package bi.baiqiu.utils;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.alibaba.druid.util.StringUtils;

/**工具类
 * 	@author Jared
 *	@time 2017年8月11日下午2:34:53
 */
public class UtilTool {

	/**将字符串转为小数点
	 * @param change
	 * @return
	 */
	public static String convertToDecimal(String change){
		if(StringUtils.isEmpty(change)){
		return "0";	
		}else if(change.contains("%"))
		{
			BigDecimal big=new BigDecimal(change.replace("%",""));
		if(big.compareTo(BigDecimal.ZERO)>0)	
			return String.valueOf(big.multiply(new BigDecimal("0.01")));	
		else
			return "0";
		}else{
			return change;
		}
	}
	/**字符串转为Integer 舍去小数位
	 * @param a
	 * @return
	 */
	public static Integer convertToInteger(String a)throws Exception{
		if(a.length()>1){
			return Integer.valueOf(a.substring(0,a.lastIndexOf('.')));
		}else if(a.length()<1){
			return 0;
		}
		else{
			return Integer.valueOf(a);
		}
	}
	/**字符串转为BigInteger 舍去小数位
	 * @param a
	 * @return
	 */
	public static BigInteger convertToBigInteger(String a)throws Exception{
		if(a.length()>1){
			return new BigInteger(a.substring(0,a.lastIndexOf('.')));
		}else if(a.length()<1){
			return new BigInteger("0");
		}
		else{
			return new BigInteger(a);
		}
	}
}
