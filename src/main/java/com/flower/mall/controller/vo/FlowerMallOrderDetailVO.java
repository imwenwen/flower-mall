
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单详情页页面VO
 */
@Data
public class FlowerMallOrderDetailVO implements Serializable {

    private String orderNo;

    private Integer totalPrice;

    private Byte payStatus;

    private String payStatusString;

    private Byte payType;

    private String payTypeString;

    private Date payTime;

    private Byte orderStatus;

    private String orderStatusString;

    private String userAddress;

    private Date createTime;

    private List<FlowerMallOrderItemVO> flowerMallOrderItemVOS;
}
