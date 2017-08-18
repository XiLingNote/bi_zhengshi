package bi.baiqiu.mapper;

import bi.baiqiu.pojo.BiTradeGoods;
import bi.baiqiu.pojo.BiTradeGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.taobao.api.domain.Order;
@Repository
public interface BiTradeGoodsMapper {
    int countByExample(BiTradeGoodsExample example);

    int deleteByExample(BiTradeGoodsExample example);

    int insertBys(Order record);
    
    int insert(BiTradeGoods record);

    int insertSelective(BiTradeGoods record);

    List<BiTradeGoods> selectByExample(BiTradeGoodsExample example);

    int updateByExampleSelective(@Param("record") BiTradeGoods record, @Param("example") BiTradeGoodsExample example);

    int updateByExample(@Param("record") BiTradeGoods record, @Param("example") BiTradeGoodsExample example);
}