
package com.flower.mall.dao;

import com.flower.mall.entity.MallGoods;
import com.flower.mall.entity.StockNumDTO;
import com.flower.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewBeeMallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(MallGoods record);

    int insertSelective(MallGoods record);

    MallGoods selectByPrimaryKey(Long goodsId);

    MallGoods selectByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);

    int updateByPrimaryKeySelective(MallGoods record);

    int updateByPrimaryKeyWithBLOBs(MallGoods record);

    int updateByPrimaryKey(MallGoods record);

    List<MallGoods> findNewBeeMallGoodsList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoods(PageQueryUtil pageUtil);

    List<MallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<MallGoods> findNewBeeMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("newBeeMallGoodsList") List<MallGoods> mallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int recoverStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}