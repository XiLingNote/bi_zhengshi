package bi.baiqiu.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.fastjson.JSON;
import com.taobao.api.domain.Order;

import bi.baiqiu.mapper.pojo.BorderBean;
import bi.baiqiu.mapper.pojo.Trade22;
import bi.baiqiu.page.PageHelper;
import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.SysTradeBean;
import bi.baiqiu.utils.DateUtils;
import bi.baiqiu.utils.GetDateTread;
import bi.baiqiu.utils.GetDateTread.RunGetDateTread;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SysTradeBeanMapperTest {

	@Autowired
	SysTradeBeanMapper sysDao;
	
	@Autowired
	BiTradeOrdersMapper biOrderDao;
	
	@Autowired
	BiTradeGoodsMapper biGoodsDao;
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private DataSourceTransactionManager dataSourceTransactionManager;

	

	@Test
	public void test() {
		PageHelper.startPage(1000, 1);
		sysDao.selectByExample(null);
		Page<SysTradeBean> page = PageHelper.endPage();
		List<SysTradeBean> list = page.getResult();
		System.out.println("-----------begin---------------------");

		for (SysTradeBean sys : list) {
			BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);

			System.out.println(border.getTrade_fullinfo_get_response().getTrade().getAdjustFee());

		}
		System.out.println("-----------end---------------------");
	}

	@Test
	public void test3() {
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		SqlSession session2 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			Page<SysTradeBean> page = new Page<>(1, 100);
			System.out.println(page);
			Date d1 = new Date();
			System.out.println("-----------query--begin---------------------");
			List<SysTradeBean> list = sysDao.selectByPage(page);
			Date d2 = new Date();
			System.out.println("-----------query--end---------------------" + (d2.getTime() - d1.getTime()));
			Date d3 = new Date();
			System.out.println("-----------insert--begin---------------------");
			BiTradeGoodsMapper goodsMapper = session2.getMapper(BiTradeGoodsMapper.class);
			int commit=1;
			for (SysTradeBean sys : list) {
				BorderBean border = JSON.parseObject(sys.getJdpResponse(), BorderBean.class);
				Trade22 tr=border.getTrade_fullinfo_get_response().getTrade();
				for(Order order:tr.getOrders().getOrder()){
					goodsMapper.insertBys(order);
				}
				commit++;
				if(commit%2000==0||commit==list.size())
				{
					session2.commit();
					 session2.clearCache();
					Date d5 = new Date();
					System.out.println("-----------commit--end---------------------"+commit+"--"+ (d5.getTime() - d3.getTime()));
				}
			}
			Date d4 = new Date();
			System.out.println("-----------insert--end---------------------" + (d4.getTime() - d3.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			session2.rollback();
		}
		finally {
			session.close();
			session2.close();
		}

	}
	@Test
	public void test4(){
		SysTradeBean bean=new SysTradeBean();
		bean.setStartDate("2017-01-01");
		bean.setEndDate("2017-08-18");
		
		System.out.println("---------------------------"+sysDao.selectDateCount(bean));
	}

}
