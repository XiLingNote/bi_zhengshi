package bi.baiqiu;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bi.baiqiu.mapper2.SysTradeBeanMapper;
import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.PcFlowStructureBean;
import bi.baiqiu.pojo2.SysTradeBean;
import bi.baiqiu.service.impl.PcFlowStructureServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SysTradeBeanMapperTest {

	@Autowired
	SysTradeBeanMapper sysDao2;
	@Autowired
	PcFlowStructureServiceImpl pc;
	@Autowired
	@Qualifier("sqlSessionTemplate1")
	private SqlSessionTemplate sqlSessionTemplate;

	@Test
	public void test4() throws Exception{
		SqlSession session3 = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		SysTradeBeanMapper sysMapper = session3.getMapper(SysTradeBeanMapper.class);
	List<SysTradeBean> list2=	sysMapper.selectByModifyToPage("2010-01-01", "2017-01-01", 0, 2);
	for(SysTradeBean bean:list2){
		System.out.println(bean.getBuyerNick());
		
	}
	Page<PcFlowStructureBean> page=pc.queryByPage(new PcFlowStructureBean(), 0, 2);
	List<PcFlowStructureBean>list=page.getResult();
	for(PcFlowStructureBean bean:list){
		System.out.println(bean.getShopId());	
	}
	}

}
