package bi.baiqiu.mapper;

import bi.baiqiu.pojo.BiTradeOrders;
import bi.baiqiu.pojo.BiTradeOrdersExample;
import bi.baiqiu.pojo.test.Trade22;

import java.util.List;
import org.apache.ibatis.annotations.Param;



public interface BiTradeOrdersMapper {
    int countByExample(BiTradeOrdersExample example);

    int deleteByExample(BiTradeOrdersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BiTradeOrders record);
    
    int insertBys(Trade22 trade);

    int insertSelective(BiTradeOrders record);

    List<BiTradeOrders> selectByExample(BiTradeOrdersExample example);

    BiTradeOrders selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BiTradeOrders record, @Param("example") BiTradeOrdersExample example);

    int updateByExample(@Param("record") BiTradeOrders record, @Param("example") BiTradeOrdersExample example);

    int updateByPrimaryKeySelective(BiTradeOrders record);

    int updateByPrimaryKey(BiTradeOrders record);
}