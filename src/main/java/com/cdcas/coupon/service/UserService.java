package com.cdcas.coupon.service;

import com.cdcas.coupon.pojo.Get;
import com.cdcas.coupon.pojo.User;
import com.cdcas.coupon.pojo.UserCouponVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {

    //注册方法
    Boolean register(User user);

    // 登录方法
    User login(String phone);

    // 查询全部用户列表的方法
    Map findAll(Integer page, String name);

    Map getCpnUsedList(Integer page, Date from, Date to);

    Map selectCpnList(Integer page, String phone);

    Integer updateUsed(Get get);
}
