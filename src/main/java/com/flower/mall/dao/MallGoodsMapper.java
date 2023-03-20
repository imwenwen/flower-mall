
package com.flower.mall.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.entity.StockNumDTO;
import com.flower.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(MallGoods record);

    int insertFlowerMallGoods(MallGoods record);

    MallGoods selectgoodsById(Long goodsId);

    int updateGoods(MallGoods record);

    int updateByPrimaryKeyWithBLOBs(MallGoods record);

    int updateByPrimaryKey(MallGoods record);

    List<MallGoods> getFlowerMallGoodsPage(PageQueryUtil pageUtil);

    int getTotalGoods(PageQueryUtil pageUtil);

    List<MallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    int batchInsert(@Param("mallGoodsList") List<MallGoods> mallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int recoverStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

    IPage<MallGoods> searchGoodsBy(IPage iPage,@Param("keyword")String keyword,@Param("goodsSellStatus") int goodsSellStatus);
}