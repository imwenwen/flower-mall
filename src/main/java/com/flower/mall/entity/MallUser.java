
package com.flower.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class MallUser {
    //用户主键id
    private Long userId;
    //用户昵称
    private String nickName;
   //登陆名称
    private String loginName;
   //MD5加密后的密码
    private String passwordMd5;
   //个性签名
    private String introduceSign;
   //收货地址
    private String address;

    /**
     * 是否删除 0未删除 1已删除
     */
    private Byte deleted;

    /**
     * 是否锁定 0未锁定 1已锁定无法登陆
     */
    private Byte lockedFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}