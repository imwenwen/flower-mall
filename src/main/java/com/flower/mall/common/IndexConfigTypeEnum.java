
package com.flower.mall.common;

import lombok.Getter;

/**
 * @apiNote 首页配置项 1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
 */
@Getter
public enum IndexConfigTypeEnum {

    DEFAULT(0, "DEFAULT","首页配置项"),
    INDEX_SEARCH_HOTS(1, "INDEX_SEARCH_HOTS","搜索框热搜"),
    INDEX_SEARCH_DOWN_HOTS(2, "INDEX_SEARCH_DOWN_HOTS","搜索下拉框热搜"),
    INDEX_GOODS_HOT(3, "INDEX_GOODS_HOTS","热销商品"),
    INDEX_GOODS_NEW(4, "INDEX_GOODS_NEW","新品上线"),
    INDEX_GOODS_RECOMMOND(5, "INDEX_GOODS_RECOMMOND","为你推荐");

    private int type;

    private String name;

    private String value;

    IndexConfigTypeEnum(int type, String name,String value) {
        this.type = type;
        this.name = name;
        this.value=value;
    }

    public static IndexConfigTypeEnum getIndexConfigTypeEnumByType(int type) {
        for (IndexConfigTypeEnum indexConfigTypeEnum : IndexConfigTypeEnum.values()) {
            if (indexConfigTypeEnum.getType() == type) {
                return indexConfigTypeEnum;
            }
        }
        return DEFAULT;
    }
}
