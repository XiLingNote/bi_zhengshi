package bi.baiqiu.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bi.baiqiu.page.PageBean;
import bi.baiqiu.pojo.ThrougTrainBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ThrougTrainMapperTest {

	@Autowired
	private ThrougTrainMapper Dao;

	@Test
	public void test() {
		List<ThrougTrainBean> list= Dao.queryByPage(null);
		
		for(ThrougTrainBean fa:list){
			System.out.println("直通车=============================="+fa.getId());
		}
		}
	@Test
	public void test2(){
		List<ThrougTrainBean> list= new ArrayList<>();
		ThrougTrainBean fans=new ThrougTrainBean();
		fans.setShopId(1);
		list.add(fans);
		list.add(fans);
		System.out.println("------------------------------------------"+Dao.insertByBatch(list));
		
		
		
	}

}
