package com.cdcas.coupon.mapper;

import com.cdcas.coupon.pojo.Coupon;

import java.util.Date;
import java.util.List;

import com.cdcas.coupon.pojo.Get;
import org.apache.ibatis.annotations.Param;

public interface CouponMapper {
    int add(Coupon item);

    List<Coupon> selectByPage(Integer offset);

    List<Coupon> selectByInUse(String phone);

    int update(Integer cpId,boolean use);

    List<Coupon> getAll();

    List<Coupon> selectHasGetNotUsed(boolean isUsed,String phone,Date date);

    List<Coupon> selectHasGetHasUsed(boolean isUsed,String phone,Date date);

    int acquire(Get get);
}