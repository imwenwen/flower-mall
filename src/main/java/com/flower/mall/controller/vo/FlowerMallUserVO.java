
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FlowerMallUserVO implements Serializable {

    private Long userId;

    private String nickName;

    private String loginName;

    private String introduceSign;

    private String address;

    private int shopCartItemCount;
}
