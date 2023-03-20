
package com.flower.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flower.mall.common.MyException;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallSearchGoodsVO;
import com.flower.mall.dao.GoodsCategoryMapper;
import com.flower.mall.dao.MallGoodsMapper;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.service.FlowerMallGoodsService;
import com.flower.mall.util.BeanUtil;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FlowerMallGoodsServiceImpl implements FlowerMallGoodsService {

    @Autowired
    private MallGoodsMapper goodsMapper;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult getFlowerMallGoodsPage(PageQueryUtil pageUtil) {
        List<MallGoods> goodsList = goodsMapper.getFlowerMallGoodsPage(pageUtil);
        int total = goodsMapper.getTotalGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveFlowerMallGoods(MallGoods goods) {
        if (goodsMapper.insertFlowerMallGoods(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateGoods(MallGoods goods) {

        //1.查询数据库存不存在该商品
        MallGoods temp = goodsMapper.selectgoodsById(goods.getGoodsId());
        //2.如果不存在 直接提示错误
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //3.如果存在 更新数据库
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateGoods(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public MallGoods getFlowerMallGoodsById(Long id) {
        MallGoods mallGoods = goodsMapper.selectgoodsById(id);
        if (mallGoods == null) {
            MyException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return mallGoods;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchGoodsBy(String keyword,IPage iPage, int goodsSellStatus) {
       IPage<MallGoods> ipage= goodsMapper.searchGoodsBy(iPage,keyword,goodsSellStatus);
        return new PageResult(ipage.getRecords(),ipage.getTotal(),ipage.getSize(),ipage.getPages());
    }
}
