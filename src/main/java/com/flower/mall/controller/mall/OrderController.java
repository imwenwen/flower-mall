
package com.flower.mall.controller.mall;

import com.flower.mall.common.Constants;
import com.flower.mall.common.MyException;
import com.flower.mall.common.OrderStatusEnum;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallOrderDetailVO;
import com.flower.mall.controller.vo.FlowerMallShoppingCartItemVO;
import com.flower.mall.controller.vo.FlowerMallUserVO;
import com.flower.mall.entity.MallOrder;
import com.flower.mall.service.OrderService;
import com.flower.mall.service.ShoppingCartService;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.Result;
import com.flower.mall.util.MallResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private OrderService orderService;

    @GetMapping("/orders/{orderNo}")
    public String orderDetailPage(HttpServletRequest request, @PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        FlowerMallOrderDetailVO orderDetailVO = orderService.getOrderDetailByOrderNo(orderNo, user.getUserId());
        request.setAttribute("orderDetailVO", orderDetailVO);
        return "mall/order-detail";
    }

    @GetMapping("/orders")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("userId", user.getUserId());
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", 3);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("orderPageResult", orderService.getMyOrders(pageUtil));
        request.setAttribute("path", "orders");
        return "mall/my-orders";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<FlowerMallShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (StringUtils.isEmpty(user.getAddress().trim())) {
            //无收货地址
            MyException.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物车中无数据则跳转至错误页
            MyException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        //保存订单并返回订单号
        String saveOrderResult = orderService.saveOrder(user, myShoppingCartItems);
        //跳转到订单详情页
        return "redirect:/orders/" + saveOrderResult;
    }

    @PutMapping("/orders/{orderNo}/cancel")
    @ResponseBody
    public Result cancelOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String cancelOrderResult = orderService.cancelOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(cancelOrderResult);
        }
    }

    @PutMapping("/orders/{orderNo}/finish")
    @ResponseBody
    public Result finishOrder(@PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        String finishOrderResult = orderService.finishOrder(orderNo, user.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(finishOrderResult);
        }
    }

    @GetMapping("/selectPayType")
    public String selectPayType(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallOrder mallOrder = orderService.getOrderByOrderNo(orderNo);
        //判断订单userId
        if (!user.getUserId().equals(mallOrder.getUserId())) {
            MyException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        //判断订单状态
        if (mallOrder.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            MyException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", mallOrder.getTotalPrice());
        return "mall/pay-select";
    }

    @GetMapping("/payPage")
    public String payOrder(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession, @RequestParam("payType") int payType) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallOrder mallOrder = orderService.getOrderByOrderNo(orderNo);
        //判断订单userId
        if (!user.getUserId().equals(mallOrder.getUserId())) {
            MyException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        //判断订单状态
        if (mallOrder.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            MyException.fail(ServiceResultEnum.ORDER_STATUS_ERROR.getResult());
        }
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", mallOrder.getTotalPrice());
        if (payType == 1) {
            return "mall/alipay";
        } else {
            return "mall/wxpay";
        }
    }

    /**
     * 付款成功
     * @param orderNo 订单号
     * @param payType 订单状态
     * @return
     */
    @GetMapping("/paySuccess")
    @ResponseBody
    public Result paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        //更改订单状态
        String payResult = orderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(payResult);
        }
    }

}
