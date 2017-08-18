package bi.baiqiu.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import bi.baiqiu.pojo.SortAmountByDate;
import bi.baiqiu.pojo.Top;
import bi.baiqiu.service.IndustryTrendService;
import bi.baiqiu.utils.DateUtils;
import bi.baiqiu.utils.PinYin4jUtils;

@SuppressWarnings("all")
@Service("industryTrendService")
public class IndustryTrendServiceImpl implements IndustryTrendService {
	@Autowired
	JedisPool jedisPool;

	@Override
	public Map<String, Object> getDay(String brandOrStore, String[] storeArr,
			Date begin, Date end) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Top> topList = new ArrayList<Top>();
		Jedis jedis = jedisPool.getResource();
		int d = begin.getDate();
		int m = begin.getMonth();
		int y = begin.getYear();
		String getyyyyMMdd = DateUtils.getyyyyMMdd(end);
		int endtime = Integer.parseInt(getyyyyMMdd);
		for (String name : storeArr) {
			Top top = new Top();
			List<SortAmountByDate> list = new ArrayList<SortAmountByDate>();
			String encode = null;
			try {
				encode = URLEncoder.encode(name, "utf-8");
				encode = encode.replace("%2F", "/");
				encode = encode.replace("%26", "&");
				encode = encode.replace("%2B", " ");
				encode = encode.replace("%3B", ";");
				encode = encode.replace("%3A", ":");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			for (int i = d;; i++) {
				Date date = new Date(y, m, i);
				String thisdate = DateUtils.getyyyyMMdd(date);
				int thistime = Integer.parseInt(thisdate);
				if (endtime < thistime) {
					break;
				}

				SortAmountByDate sortAmountByDate = new SortAmountByDate();
				Double score = jedis.zscore(brandOrStore + thisdate, name);
				BigDecimal amount = null;
				if (score != null) {
					amount = new BigDecimal(score);
				} else {
					amount = new BigDecimal(0);
				}
				sortAmountByDate.setAmount(amount);
				sortAmountByDate.setDate(thisdate);
				list.add(sortAmountByDate);
			}
			top.setName(encode);
			top.setList(list);
			topList.add(top);
		}

		// DAY
		map.put("topList", topList);
		jedisPool.returnResource(jedis);
		return map;
	}

	@Override
	public Map<String, Object> getMonth(String brandOrStore, String[] nameArr,
			Date begin, Date end) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Top> topList = new ArrayList<Top>();
		Jedis jedis = jedisPool.getResource();
		int m = begin.getMonth();
		int y = begin.getYear();
		int d = begin.getDate();
		String yyyyMM = DateUtils.getyyyyMM(end);
		int endtime = Integer.parseInt(yyyyMM);
		for (String name : nameArr) {
			String encode = null;
			try {
				encode = URLEncoder.encode(name, "utf-8");
				encode = encode.replace("%2F", "/");
				encode = encode.replace("%26", "&");
				encode = encode.replace("%2B", " ");
				encode = encode.replace("%3B", ";");
				encode = encode.replace("%3A", ":");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Top top = new Top();
			List<SortAmountByDate> list = new ArrayList<SortAmountByDate>();
			for (int i = m;; i++) {
				Date date = new Date(y, i, d);
				String thisdate = DateUtils.getyyyyMM(date);
				int thistime = Integer.parseInt(thisdate);
				if (endtime < thistime) {
					break;
				}
				SortAmountByDate sortAmountByDate = new SortAmountByDate();
				Double score = jedis.zscore(brandOrStore + thisdate, name);
				BigDecimal amount = null;
				if (score != null) {
					amount = new BigDecimal(score);
				} else {
					amount = new BigDecimal(0);
				}
				sortAmountByDate.setAmount(amount);
				sortAmountByDate.setDate(thisdate);
				list.add(sortAmountByDate);
			}
			top.setName(encode);
			top.setList(list);
			topList.add(top);
		}

		map.put("topList", topList);
		jedisPool.returnResource(jedis);
		return map;
	}

	@Override
	public Map<String, Object> getYear(String brandOrStore, String[] nameArr,
			int i, int j) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Top> topList = new ArrayList<Top>();
		Jedis jedis = jedisPool.getResource();
		for (String name : nameArr) {
			String encode = null;
			try {
				encode = URLEncoder.encode(name, "utf-8");
				encode = encode.replace("%2F", "/");
				encode = encode.replace("%26", "&");
				encode = encode.replace("%2B", " ");
				encode = encode.replace("%3B", ";");
				encode = encode.replace("%3A", ":");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Top top = new Top();
			String namePinyin = PinYin4jUtils.hanziToPinyin(name, "");
			List<SortAmountByDate> list = new ArrayList<SortAmountByDate>();
			for (int k = i; k <= j; k++) {
				String thisdate = "" + k;
				SortAmountByDate sortAmountByDate = new SortAmountByDate();
				Double score = jedis.zscore(brandOrStore + thisdate, name);
				BigDecimal amount = null;
				if (score != null) {
					amount = new BigDecimal(score);
				} else {
					amount = new BigDecimal(0);
				}
				sortAmountByDate.setAmount(amount);
				sortAmountByDate.setDate(thisdate);
				list.add(sortAmountByDate);
			}
			top.setName(encode);
			top.setList(list);
			topList.add(top);
		}

		// YEAR
		map.put("topList", topList);
		jedisPool.returnResource(jedis);
		return map;
	}

	// industryStore
	@Override
	public Set<String> queryIndustryStoreName() {
		Set<String> set = new HashSet<String>();
		Jedis jedis = jedisPool.getResource();
		Set<String> names = jedis.smembers("industryStore");
		for (String storeName : names) {
			set.add(storeName);
		}
		jedisPool.returnResource(jedis);
		return set;
	}

	// industryBrand
	@Override
	public Set<String> queryIndustryBrandName() {
		Set<String> set = new HashSet<String>();
		Jedis jedis = jedisPool.getResource();
		Set<String> names = jedis.smembers("industryBrand");
		for (String storeName : names) {
			set.add(storeName);
		}
		jedisPool.returnResource(jedis);
		return set;
	}

}
