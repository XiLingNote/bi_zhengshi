package bi.baiqiu.mapper;

import bi.baiqiu.page.PageHelper.Page;
import bi.baiqiu.pojo.SysTradeBean;
import bi.baiqiu.pojo.SysTradeBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysTradeBeanMapper {
	
	List<SysTradeBean> selectByPage(Page<SysTradeBean> bean);
	
    int countByExample(SysTradeBeanExample example);

    int deleteByExample(SysTradeBeanExample example);

    int deleteByPrimaryKey(Long tid);
    
    int selectDateCount(SysTradeBean record);

    int insert(SysTradeBean record);

    int insertSelective(SysTradeBean record);

    List<SysTradeBean> selectByExampleWithBLOBs(SysTradeBeanExample example);

    List<SysTradeBean> selectByExample(SysTradeBeanExample example);

    SysTradeBean selectByPrimaryKey(Long tid);

    int updateByExampleSelective(@Param("record") SysTradeBean record, @Param("example") SysTradeBeanExample example);

    int updateByExampleWithBLOBs(@Param("record") SysTradeBean record, @Param("example") SysTradeBeanExample example);

    int updateByExample(@Param("record") SysTradeBean record, @Param("example") SysTradeBeanExample example);

    int updateByPrimaryKeySelective(SysTradeBean record);

    int updateByPrimaryKeyWithBLOBs(SysTradeBean record);

    int updateByPrimaryKey(SysTradeBean record);
}