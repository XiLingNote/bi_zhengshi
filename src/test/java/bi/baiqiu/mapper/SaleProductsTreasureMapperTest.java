package bi.baiqiu.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bi.baiqiu.page.PageBean;
import bi.baiqiu.pojo.SaleProductsTreasureBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SaleProductsTreasureMapperTest {

	@Autowired
	private SaleProductsTreasureMapper Dao;

	@Test
	public void test() {
		List<SaleProductsTreasureBean> list= Dao.queryByPage(null);
		
		for(SaleProductsTreasureBean fa:list){
			System.out.println("品销宝=============================="+fa.getId());
		}
		}
	@Test
	public void test2(){
		List<SaleProductsTreasureBean> list= new ArrayList<>();
		SaleProductsTreasureBean fans=new SaleProductsTreasureBean();
		fans.setShopId(1);
		list.add(fans);
		list.add(fans);
		System.out.println("------------------------------------------"+Dao.insertByBatch(list));
		
		
		
	}

}
