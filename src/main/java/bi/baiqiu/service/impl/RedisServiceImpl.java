package bi.baiqiu.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import bi.baiqiu.pojo.DailyTarget;
import bi.baiqiu.pojo.GoalMonthAlip;
import bi.baiqiu.pojo.GoalMonthsale;
import bi.baiqiu.pojo.RedisPojo;
import bi.baiqiu.pojo.User;
import bi.baiqiu.utils.GsonUtils;

@Service
public class RedisServiceImpl {
	@Autowired
	JedisPool pool;

	private static final String all = "All";

	private static final String DAY = "DAY";

	private static final String MONTH = "MONTH";

	private static final String YEAR = "YEAR";

	/**
	 * @param user
	 *            用户
	 * @param list
	 */
	public void saveDailyTargetToRedis(User user, List<DailyTarget> list) {
		Jedis jedis = pool.getResource();
		String name = user.getShopName();
		// 获取部门
		String department = jedis.get(name);
		// 公司总计
		for (DailyTarget dailyTarget : list) {
			String ymd = dailyTarget.getDate().replaceAll("-", "")
					.replaceAll("/", "");
			// 店铺
			RedisPojo pojo = null;
			String dateString = jedis.hget(name + DAY, ymd);
			if (StringUtils.isNotBlank(dateString)) {
				pojo = GsonUtils.gson.fromJson(dateString, RedisPojo.class);

			} else {
				pojo = new RedisPojo();
				pojo.setDatetime(ymd);
			}
			pojo.setTarget(new BigDecimal(dailyTarget.getPayment()));
			jedis.hset(name + DAY, ymd, GsonUtils.gson.toJson(pojo));
			// 部门
			RedisPojo departPojo = null;
			String departString = jedis.hget(department + DAY, ymd);
			if (StringUtils.isNotBlank(departString)) {
				departPojo = GsonUtils.gson.fromJson(departString,
						RedisPojo.class);

			} else {
				departPojo = new RedisPojo();
				departPojo.setDatetime(ymd);
			}
			departPojo.setTarget(new BigDecimal(dailyTarget.getPayment()));
			jedis.hset(department + DAY, ymd, GsonUtils.gson.toJson(departPojo));
			// 总计
			RedisPojo allPojo = null;
			String allString = jedis.hget(all + DAY, ymd);
			if (StringUtils.isNotBlank(allString)) {
				allPojo = GsonUtils.gson.fromJson(allString, RedisPojo.class);

			} else {
				allPojo = new RedisPojo();
				allPojo.setDatetime(ymd);
			}
			allPojo.setTarget(new BigDecimal(dailyTarget.getPayment()));
			jedis.hset(all + DAY, ymd, GsonUtils.gson.toJson(allPojo));

		}

		pool.returnResource(jedis);
	}

	public void saveGmvMonthTargetToRedis(User user, GoalMonthsale sale) {

		saveGmvTargetToRedis(user.getShopName(), sale.getM01(), "01", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM02(), "02", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM03(), "03", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM04(), "04", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM05(), "05", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM06(), "06", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM07(), "07", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM08(), "08", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM09(), "09", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM10(), "10", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM11(), "11", sale.getY());
		saveGmvTargetToRedis(user.getShopName(), sale.getM12(), "12", sale.getY());
	}

	private void saveGmvTargetToRedis(String name, String amount, String m,
			String y) {
		Jedis jedis = pool.getResource();
		if (amount==null) {
			return;
		}
		BigDecimal newTarget = new BigDecimal(amount).divide(new BigDecimal("100"));
		y = y.substring(0, 4);
		String string = jedis.hget(name + MONTH, y + m);
		BigDecimal target = null;
		if (StringUtils.isNotBlank(string)) {
			target = GsonUtils.gson.fromJson(string, RedisPojo.class)
					.getTarget();
		} else {
			target = new BigDecimal(0);
		}
		Map<String, String> map = new HashMap<String, String>();
		// 店铺
		map.put(name + MONTH, y + m);
		map.put(name + YEAR, y);
		// 部门
		String department = jedis.get(name);
		map.put(department + MONTH, y + m);
		map.put(department + YEAR, y);
		// 公司总计
		map.put(all + MONTH, y + m);
		map.put(all + YEAR, y);
		for (String key : map.keySet()) {
			String field = map.get(key);

			RedisPojo pojo = null;
			String dateString = jedis.hget(key, field);
			if (StringUtils.isNotBlank(dateString)) {
				pojo = GsonUtils.gson.fromJson(dateString, RedisPojo.class);

			} else {
				pojo = new RedisPojo();
				pojo.setDatetime(field);
			}
			pojo.increaseTarget(newTarget.subtract(target));
			jedis.hset(key, field, GsonUtils.gson.toJson(pojo));
		}
		pool.returnResource(jedis);
	}

	public void saveAlipayMonthTargetToRedis(User user, GoalMonthAlip alip) {

		saveAliTargetToRedis(user.getShopName(), alip.getM01(), "01",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM02(), "02",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM03(), "03",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM04(), "04",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM05(), "05",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM06(), "06",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM07(), "07",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM08(), "08",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM09(), "09",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM10(), "10",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM11(), "11",
				alip.getY());
		saveAliTargetToRedis(user.getShopName(), alip.getM12(), "12",
				alip.getY());
	}

	private void saveAliTargetToRedis(String name, String amount, String m,
			String y) {
		if (amount==null) {
			return;
		}
		BigDecimal newAlipayTarget = new BigDecimal(amount).divide(new BigDecimal("100"));
		y = y.substring(0, 4);
		Jedis jedis = pool.getResource();
		String string = jedis.hget(name + MONTH, y + m);
		BigDecimal alipayTarget = null;
		if (StringUtils.isNotBlank(string)) {
			alipayTarget = GsonUtils.gson.fromJson(string, RedisPojo.class)
					.getAlipayTarget();

		} else {
			alipayTarget = new BigDecimal(0);
		}

		Map<String, String> map = new HashMap<String, String>();
		// 店铺
		map.put(name + MONTH, y + m);
		map.put(name + YEAR, y);
		// 部门
		String department = jedis.get(name);
		map.put(department + MONTH, y + m);
		map.put(department + YEAR, y);
		// 公司总计
		map.put(all + MONTH, y + m);
		map.put(all + YEAR, y);
		for (String key : map.keySet()) {
			String field = map.get(key);

			RedisPojo pojo = null;
			String dateString = jedis.hget(key, field);
			if (StringUtils.isNotBlank(dateString)) {
				pojo = GsonUtils.gson.fromJson(dateString, RedisPojo.class);

			} else {
				pojo = new RedisPojo();
				pojo.setDatetime(field);
			}
			pojo.increaseAlipayTarget(newAlipayTarget.subtract(alipayTarget));
			jedis.hset(key, field, GsonUtils.gson.toJson(pojo));
		}
		pool.returnResource(jedis);
	}

}
