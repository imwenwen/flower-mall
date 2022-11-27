
package com.flower.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MallOrder {
    //订单表主键id
    private Long orderId;
    //订单号
    private String orderNo;
    //用户主键id
    private Long userId;
    //订单总价
    private Integer totalPrice;
    //支付状态:0.未支付,1.支付成功,-1:支付失败',
    private Byte payStatus;
    //0.无 1.支付宝支付 2.微信支付
    private Byte payType;
    //支付时间
    private Date payTime;
    //订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
    private Byte orderStatus;
    //订单body
    private String extraInfo;
    //收货人地址
    private String userAddress;
    //是否删除
    private Byte deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}