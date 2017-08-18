package bi.baiqiu.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bi.baiqiu.page.PageBean;
import bi.baiqiu.pojo.DrillShowBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DrillShowMapperTest {

	@Autowired
	private DrillShowMapper Dao;

	@Test
	public void test() {
		List<DrillShowBean> list= Dao.queryByPage(null);
		
		for(DrillShowBean fa:list){
			System.out.println("钻展=============================="+fa.getId());
		}
		}
	@Test
	public void test2(){
		List<DrillShowBean> list= new ArrayList<>();
		DrillShowBean fans=new DrillShowBean();
		fans.setShopId(1);
		list.add(fans);
		list.add(fans);
		System.out.println("------------------------------------------"+Dao.insertByBatch(list));
		
		
		
	}

}
