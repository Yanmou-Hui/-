package com.cdcas.coupon.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserCouponVo {
    private Integer id; // 优惠券id
    private String userName; // 用户名
    private String phone; // 电话
    private String cpName; // 优惠券名称
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date useTime; // 优惠券使用日期
    private boolean idUsed; // 是否使用
    private String role; // 说明
    private String img; // 背景
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date collectExpire; // 领用有效期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date useExpire; //使用有效期
    private boolean inUse;//是否上架
}
