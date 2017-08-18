package bi.baiqiu.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.druid.support.json.JSONUtils;

import bi.baiqiu.pojo.CommodityPropertyBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CommodityPropertyMapperTest1 {

	@Autowired
	private CommodityPropertyMapper Dao;

	@Test
	public void test() {
		List<CommodityPropertyBean> list= Dao.queryByPage(null);
		}
	@Test
	public void test2(){
		List<CommodityPropertyBean> list= new ArrayList<>();
		CommodityPropertyBean fans=new CommodityPropertyBean();
		fans.setShopId(1);
		list.add(fans);
		list.add(fans);
		System.out.println("------------------------------------------"+Dao.insertByBatch(list));
		
		
		
	}	
	@Test
	public void test3() {
		List<Map<String, String[]>> list= Dao.queryFeatureAndProperty(1);
		System.out.println(JSONUtils.toJSONString(list));
		}

}
