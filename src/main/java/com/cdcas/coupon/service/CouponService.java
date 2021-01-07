package com.cdcas.coupon.service;

import com.cdcas.coupon.pojo.Coupon;
import com.cdcas.coupon.pojo.Get;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CouponService {
    Boolean add(Coupon item);

    List<Coupon> selectByPage(Integer page);

    List<Coupon> selectByInUse(String phone);

    int update(Integer cpId,Integer use);

    List<Coupon> selectHasGet(boolean isUsed,String phone,Date date);

    int acquire(Get get);

    Map getAll();
}
