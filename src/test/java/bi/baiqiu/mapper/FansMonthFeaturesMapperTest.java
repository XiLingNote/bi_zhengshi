package bi.baiqiu.mapper;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bi.baiqiu.page.PageBean;
import bi.baiqiu.pojo.FansMonthFeaturesBean;

/**粉丝月报特征
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FansMonthFeaturesMapperTest {
@Autowired
 private FansMonthFeaturesMapper fansMonthFeaturesDao;
	@Test
	public void testQueryByPage() {
		List<FansMonthFeaturesBean> list=
		fansMonthFeaturesDao.queryByPage(null);
	for(FansMonthFeaturesBean fans:list){
		System.out.println("------------------------粉丝月特征-"+fans.getId());
	}
	}

	@Test
	public void testInsertByBatch() {

		List<FansMonthFeaturesBean> list= new ArrayList<>();
		FansMonthFeaturesBean fans=new FansMonthFeaturesBean();
		fans.setShopId(1);
		list.add(fans);
		list.add(fans);
		System.out.println("------------------------------------------"+fansMonthFeaturesDao.insertByBatch(list));
		
		
	}

}
