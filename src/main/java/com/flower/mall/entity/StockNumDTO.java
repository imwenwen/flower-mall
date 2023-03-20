
package com.flower.mall.entity;

import lombok.Data;

/**
 * 库存修改所需实体
 */
@Data
public class StockNumDTO {
    //关联商品id
    private Long goodsId;
    //数量
    private Integer goodsCount;


}
