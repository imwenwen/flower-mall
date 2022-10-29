
package com.flower.mall.service.impl;

import com.flower.mall.common.*;
import com.flower.mall.controller.vo.*;
import com.flower.mall.dao.NewBeeMallGoodsMapper;
import com.flower.mall.dao.NewBeeMallOrderItemMapper;
import com.flower.mall.dao.NewBeeMallOrderMapper;
import com.flower.mall.dao.NewBeeMallShoppingCartItemMapper;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.entity.MallOrder;
import com.flower.mall.entity.MallOrderItem;
import com.flower.mall.entity.StockNumDTO;
import com.flower.mall.service.NewBeeMallOrderService;
import com.flower.mall.util.BeanUtil;
import com.flower.mall.util.NumberUtil;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class NewBeeMallOrderServiceImpl implements NewBeeMallOrderService {

    @Autowired
    private NewBeeMallOrderMapper newBeeMallOrderMapper;
    @Autowired
    private NewBeeMallOrderItemMapper newBeeMallOrderItemMapper;
    @Autowired
    private NewBeeMallShoppingCartItemMapper newBeeMallShoppingCartItemMapper;
    @Autowired
    private NewBeeMallGoodsMapper newBeeMallGoodsMapper;

    @Override
    public PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil) {
        List<MallOrder> mallOrders = newBeeMallOrderMapper.findNewBeeMallOrderList(pageUtil);
        int total = newBeeMallOrderMapper.getTotalNewBeeMallOrders(pageUtil);
        PageResult pageResult = new PageResult(mallOrders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String updateOrderInfo(MallOrder mallOrder) {
        MallOrder temp = newBeeMallOrderMapper.selectByPrimaryKey(mallOrder.getOrderId());
        //不为空且orderStatus>=0且状态为出库之前可以修改部分信息
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(mallOrder.getTotalPrice());
            temp.setUserAddress(mallOrder.getUserAddress());
            temp.setUpdateTime(new Date());
            if (newBeeMallOrderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<MallOrder> orders = newBeeMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (MallOrder mallOrder : orders) {
                if (mallOrder.getDeleted() == 1) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                    continue;
                }
                if (mallOrder.getOrderStatus() != 1) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行配货完成操作 修改订单状态和更新时间
                if (newBeeMallOrderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功的订单，无法执行配货完成操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkOut(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<MallOrder> orders = newBeeMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (MallOrder mallOrder : orders) {
                if (mallOrder.getDeleted() == 1) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                    continue;
                }
                if (mallOrder.getOrderStatus() != 1 && mallOrder.getOrderStatus() != 2) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行出库操作 修改订单状态和更新时间
                if (newBeeMallOrderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功或配货完成无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功或配货完成的订单，无法执行出库操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String closeOrder(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<MallOrder> orders = newBeeMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (MallOrder mallOrder : orders) {
                // deleted=1 一定为已关闭订单
                if (mallOrder.getDeleted() == 1) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                    continue;
                }
                //已关闭或者已完成无法关闭订单
                if (mallOrder.getOrderStatus() == 4 || mallOrder.getOrderStatus() < 0) {
                    errorOrderNos += mallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行关闭操作 修改订单状态和更新时间&&恢复库存
                if (newBeeMallOrderMapper.closeOrder(Arrays.asList(ids), NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0 && recoverStockNum(Arrays.asList(ids))) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行关闭操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单不能执行关闭操作";
                } else {
                    return "你选择的订单不能执行关闭操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String saveOrder(FlowerMallUserVO user, List<FlowerMallShoppingCartItemVO> myShoppingCartItems) {
        List<Long> itemIdList = myShoppingCartItems.stream().map(FlowerMallShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = myShoppingCartItems.stream().map(FlowerMallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
        List<MallGoods> mallGoods = newBeeMallGoodsMapper.selectByPrimaryKeys(goodsIds);
        //检查是否包含已下架商品
        List<MallGoods> goodsListNotSelling = mallGoods.stream()
                .filter(newBeeMallGoodsTemp -> newBeeMallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            //goodsListNotSelling 对象非空则表示有下架商品
            NewBeeMallException.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");
        }
        Map<Long, MallGoods> newBeeMallGoodsMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        //判断商品库存
        for (FlowerMallShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!newBeeMallGoodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
                NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //存在数量大于库存的情况，直接返回错误提醒
            if (shoppingCartItemVO.getGoodsCount() > newBeeMallGoodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
                NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        //删除购物项
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(mallGoods)) {
            if (newBeeMallShoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
                int updateStockNumResult = newBeeMallGoodsMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //生成订单号
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                //保存订单
                MallOrder mallOrder = new MallOrder();
                mallOrder.setOrderNo(orderNo);
                mallOrder.setUserId(user.getUserId());
                mallOrder.setUserAddress(user.getAddress());
                //总价
                for (FlowerMallShoppingCartItemVO flowerMallShoppingCartItemVO : myShoppingCartItems) {
                    priceTotal += flowerMallShoppingCartItemVO.getGoodsCount() * flowerMallShoppingCartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    NewBeeMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                mallOrder.setTotalPrice(priceTotal);
                //订单body字段，用来作为生成支付单描述信息，暂时未接入第三方支付接口，故该字段暂时设为空字符串
                String extraInfo = "";
                mallOrder.setExtraInfo(extraInfo);
                //生成订单项并保存订单项纪录
                if (newBeeMallOrderMapper.insertSelective(mallOrder) > 0) {
                    //生成所有的订单项快照，并保存至数据库
                    List<MallOrderItem> mallOrderItems = new ArrayList<>();
                    for (FlowerMallShoppingCartItemVO flowerMallShoppingCartItemVO : myShoppingCartItems) {
                        MallOrderItem mallOrderItem = new MallOrderItem();
                        //使用BeanUtil工具类将newBeeMallShoppingCartItemVO中的属性复制到newBeeMallOrderItem对象中
                        BeanUtil.copyProperties(flowerMallShoppingCartItemVO, mallOrderItem);
                        //NewBeeMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
                        mallOrderItem.setOrderId(mallOrder.getOrderId());
                        mallOrderItems.add(mallOrderItem);
                    }
                    //保存至数据库
                    if (newBeeMallOrderItemMapper.insertBatch(mallOrderItems) > 0) {
                        //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
                        return orderNo;
                    }
                    NewBeeMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                NewBeeMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            NewBeeMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        NewBeeMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }

    @Override
    public FlowerMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        MallOrder mallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder == null) {
            NewBeeMallException.fail(ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult());
        }
        //验证是否是当前userId下的订单，否则报错
        if (!userId.equals(mallOrder.getUserId())) {
            NewBeeMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        List<MallOrderItem> orderItems = newBeeMallOrderItemMapper.selectByOrderId(mallOrder.getOrderId());
        //获取订单项数据
        if (CollectionUtils.isEmpty(orderItems)) {
            NewBeeMallException.fail(ServiceResultEnum.ORDER_ITEM_NOT_EXIST_ERROR.getResult());
        }
        List<FlowerMallOrderItemVO> flowerMallOrderItemVOS = BeanUtil.copyList(orderItems, FlowerMallOrderItemVO.class);
        FlowerMallOrderDetailVO flowerMallOrderDetailVO = new FlowerMallOrderDetailVO();
        BeanUtil.copyProperties(mallOrder, flowerMallOrderDetailVO);
        flowerMallOrderDetailVO.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(flowerMallOrderDetailVO.getOrderStatus()).getName());
        flowerMallOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(flowerMallOrderDetailVO.getPayType()).getName());
        flowerMallOrderDetailVO.setFlowerMallOrderItemVOS(flowerMallOrderItemVOS);
        return flowerMallOrderDetailVO;
    }

    @Override
    public MallOrder getNewBeeMallOrderByOrderNo(String orderNo) {
        return newBeeMallOrderMapper.selectByOrderNo(orderNo);
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = newBeeMallOrderMapper.getTotalNewBeeMallOrders(pageUtil);
        List<MallOrder> mallOrders = newBeeMallOrderMapper.findNewBeeMallOrderList(pageUtil);
        List<FlowerMallOrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(mallOrders, FlowerMallOrderListVO.class);
            //设置订单状态中文显示值
            for (FlowerMallOrderListVO flowerMallOrderListVO : orderListVOS) {
                flowerMallOrderListVO.setOrderStatusString(NewBeeMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(flowerMallOrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = mallOrders.stream().map(MallOrder::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<MallOrderItem> orderItems = newBeeMallOrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<MallOrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(MallOrderItem::getOrderId));
                for (FlowerMallOrderListVO flowerMallOrderListVO : orderListVOS) {
                    //封装每个订单列表对象的订单项数据
                    if (itemByOrderIdMap.containsKey(flowerMallOrderListVO.getOrderId())) {
                        List<MallOrderItem> orderItemListTemp = itemByOrderIdMap.get(flowerMallOrderListVO.getOrderId());
                        //将NewBeeMallOrderItem对象列表转换成NewBeeMallOrderItemVO对象列表
                        List<FlowerMallOrderItemVO> flowerMallOrderItemVOS = BeanUtil.copyList(orderItemListTemp, FlowerMallOrderItemVO.class);
                        flowerMallOrderListVO.setFlowerMallOrderItemVOS(flowerMallOrderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String cancelOrder(String orderNo, Long userId) {
        MallOrder mallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(mallOrder.getUserId())) {
                NewBeeMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            //订单状态判断
            if (mallOrder.getOrderStatus().intValue() == NewBeeMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus()
                    || mallOrder.getOrderStatus().intValue() == NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
                    || mallOrder.getOrderStatus().intValue() == NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
                    || mallOrder.getOrderStatus().intValue() == NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            //修改订单状态&&恢复库存
            if (newBeeMallOrderMapper.closeOrder(Collections.singletonList(mallOrder.getOrderId()), NewBeeMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0 && recoverStockNum(Collections.singletonList(mallOrder.getOrderId()))) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
        MallOrder mallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            //验证是否是当前userId下的订单，否则报错
            if (!userId.equals(mallOrder.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            //订单状态判断 非出库状态下不进行修改操作
            if (mallOrder.getOrderStatus().intValue() != NewBeeMallOrderStatusEnum.ORDER_EXPRESS.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            mallOrder.setOrderStatus((byte) NewBeeMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            mallOrder.setUpdateTime(new Date());
            if (newBeeMallOrderMapper.updateByPrimaryKeySelective(mallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        MallOrder mallOrder = newBeeMallOrderMapper.selectByOrderNo(orderNo);
        if (mallOrder != null) {
            //订单状态判断 非待支付状态下不进行修改操作
            if (mallOrder.getOrderStatus().intValue() != NewBeeMallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            mallOrder.setOrderStatus((byte) NewBeeMallOrderStatusEnum.ORDER_PAID.getOrderStatus());
            mallOrder.setPayType((byte) payType);
            mallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            mallOrder.setPayTime(new Date());
            mallOrder.setUpdateTime(new Date());
            if (newBeeMallOrderMapper.updateByPrimaryKeySelective(mallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public List<FlowerMallOrderItemVO> getOrderItems(Long id) {
        MallOrder mallOrder = newBeeMallOrderMapper.selectByPrimaryKey(id);
        if (mallOrder != null) {
            List<MallOrderItem> orderItems = newBeeMallOrderItemMapper.selectByOrderId(mallOrder.getOrderId());
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<FlowerMallOrderItemVO> flowerMallOrderItemVOS = BeanUtil.copyList(orderItems, FlowerMallOrderItemVO.class);
                return flowerMallOrderItemVOS;
            }
        }
        return null;
    }

    /**
     * 恢复库存
     * @param orderIds
     * @return
     */
    public Boolean recoverStockNum(List<Long> orderIds) {
        //查询对应的订单项
        List<MallOrderItem> mallOrderItems = newBeeMallOrderItemMapper.selectByOrderIds(orderIds);
        //获取对应的商品id和商品数量并赋值到StockNumDTO对象中
        List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(mallOrderItems, StockNumDTO.class);
        //执行恢复库存的操作
        int updateStockNumResult = newBeeMallGoodsMapper.recoverStockNum(stockNumDTOS);
        if (updateStockNumResult < 1) {
            NewBeeMallException.fail(ServiceResultEnum.CLOSE_ORDER_ERROR.getResult());
            return false;
        } else {
            return true;
        }
    }
}