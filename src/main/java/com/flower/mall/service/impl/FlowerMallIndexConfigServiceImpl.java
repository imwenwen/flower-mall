
package com.flower.mall.service.impl;

import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallIndexConfigGoodsVO;
import com.flower.mall.dao.IndexConfigMapper;
import com.flower.mall.dao.MallGoodsMapper;
import com.flower.mall.entity.IndexConfig;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.service.FlowerMallIndexConfigService;
import com.flower.mall.util.BeanUtil;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlowerMallIndexConfigServiceImpl implements FlowerMallIndexConfigService {

    @Autowired
    private IndexConfigMapper indexConfigMapper;

    @Autowired
    private MallGoodsMapper goodsMapper;

    @Override
    public PageResult getConfigsPage(PageQueryUtil pageUtil) {
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigList(pageUtil);
        int total = indexConfigMapper.getTotalIndexConfigs(pageUtil);
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveIndexConfig(IndexConfig indexConfig) {
        if (goodsMapper.selectgoodsById(indexConfig.getGoodsId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        if (indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId()) != null) {
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        if (goodsMapper.selectgoodsById(indexConfig.getGoodsId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        IndexConfig temp2 = indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId());
        if (temp2 != null && !temp2.getConfigId().equals(indexConfig.getConfigId())) {
            //goodsId相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        indexConfig.setUpdateTime(new Date());
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public IndexConfig getIndexConfigById(Long id) {
        return null;
    }

    @Override
    public List<FlowerMallIndexConfigGoodsVO> getConfigGoodsBy(int configType, int number) {
        List<FlowerMallIndexConfigGoodsVO> flowerMallIndexConfigGoods = new ArrayList<>(number);
        //根据类型搜索配置表获取 配置表上面的商品ids
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType, number);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            //取出所有的goodsId商品ids,
            List<Long> goodsIds = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());
            //根据查询商品表 查询商品
            List<MallGoods> mallGoods = goodsMapper.selectByPrimaryKeys(goodsIds);
            flowerMallIndexConfigGoods = BeanUtil.copyList(mallGoods, FlowerMallIndexConfigGoodsVO.class);
            for (FlowerMallIndexConfigGoodsVO flowerMallIndexConfigGoodsVO : flowerMallIndexConfigGoods) {
                String goodsName = flowerMallIndexConfigGoodsVO.getGoodsName();
                String goodsIntro = flowerMallIndexConfigGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                //商品名称超过30个字，超出部分用...代替
                if (goodsName.length() > 30) {
                    goodsName = goodsName.substring(0, 30) + "...";
                    flowerMallIndexConfigGoodsVO.setGoodsName(goodsName);
                }
                //商品简介超过30个字，超出部分用...代替
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    flowerMallIndexConfigGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        return flowerMallIndexConfigGoods;
    }

    @Override
    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return indexConfigMapper.deleteBatch(ids) > 0;
    }
}
