
package com.flower.mall.controller.mall;

import com.flower.mall.common.Constants;
import com.flower.mall.common.MyException;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallShoppingCartItemVO;
import com.flower.mall.controller.vo.FlowerMallUserVO;
import com.flower.mall.entity.MallShoppingCartItem;
import com.flower.mall.service.ShoppingCartService;
import com.flower.mall.util.Result;
import com.flower.mall.util.MallResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping("/shop-cart")
    public String cartListPage(HttpServletRequest request,
                               HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        int itemsTotal = 0;
        int priceTotal = 0;
        List<FlowerMallShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数
            itemsTotal = myShoppingCartItems.stream().mapToInt(FlowerMallShoppingCartItemVO::getGoodsCount).sum();
            if (itemsTotal < 1) {
                MyException.fail("购物项不能为空");
            }
            //总价
            for (FlowerMallShoppingCartItemVO flowerMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += flowerMallShoppingCartItemVO.getGoodsCount() * flowerMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                MyException.fail("购物项价格异常");
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/cart";
    }

    @PostMapping("/shop-cart")
    @ResponseBody
    public Result saveNewBeeMallShoppingCartItem(@RequestBody MallShoppingCartItem mallShoppingCartItem,
                                                 HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        mallShoppingCartItem.setUserId(user.getUserId());
        String saveResult = shoppingCartService.saveNewBeeMallCartItem(mallShoppingCartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return MallResult.createSuccessRes();
        }
        //添加失败
        return MallResult.createFailRes(saveResult);
    }

    @PutMapping("/shop-cart")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@RequestBody MallShoppingCartItem mallShoppingCartItem,
                                                   HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        mallShoppingCartItem.setUserId(user.getUserId());
        String updateResult = shoppingCartService.updateNewBeeMallCartItem(mallShoppingCartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return MallResult.createSuccessRes();
        }
        //修改失败
        return MallResult.createFailRes(updateResult);
    }

    @DeleteMapping("/shop-cart/{newBeeMallShoppingCartItemId}")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@PathVariable("newBeeMallShoppingCartItemId") Long newBeeMallShoppingCartItemId,
                                                   HttpSession httpSession) {
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Boolean deleteResult = shoppingCartService.deleteById(newBeeMallShoppingCartItemId,user.getUserId());
        //删除成功
        if (deleteResult) {
            return MallResult.createSuccessRes();
        }
        //删除失败
        return MallResult.createFailRes(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    public String settlePage(HttpServletRequest request,
                             HttpSession httpSession) {
        int priceTotal = 0;
        FlowerMallUserVO user = (FlowerMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<FlowerMallShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //无数据则不跳转至结算页
            return "/shop-cart";
        } else {
            //总价
            for (FlowerMallShoppingCartItemVO flowerMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += flowerMallShoppingCartItemVO.getGoodsCount() * flowerMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                MyException.fail("购物项价格异常");
            }
        }
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/order-settle";
    }
}
