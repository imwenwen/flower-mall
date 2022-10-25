
package com.flower.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Carousel {
    /**
     * 首页轮播图主键id
     */
    private Integer carouselId;
    /**
     * 轮播图 地址
     */
    private String carouselUrl;

    /**
     * 排序值(字段越大越靠前)
     */
    private Integer carouselRank;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人 id
     */
    private Integer createUser;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新人 id
     */
    private Integer updateUser;
}