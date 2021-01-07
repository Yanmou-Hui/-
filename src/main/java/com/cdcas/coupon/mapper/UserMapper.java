package com.cdcas.coupon.mapper;

import com.cdcas.coupon.pojo.Get;
import com.cdcas.coupon.pojo.User;
import com.cdcas.coupon.pojo.UserCouponVo;

import java.util.Date;
import java.util.List;

public interface UserMapper {

    Integer insert(User user);

    Integer delete(String phone);

    User selectById(Integer id);

    User selectByPhone(String phone);

    Integer update(User user);

    List<User> selectAll(Integer offset, String name);

    List<User> selectAllNoArgs();

    Integer getCount(String name);

    List<UserCouponVo> getCpnUsedList(Integer offset, Date from, Date to);

    List<UserCouponVo> selectCpnList(Integer offset, String phone);

    Integer getCpnUsedListCount(Date from, Date to);

    Integer selectCpnListCount(String phone);

    Integer updateUsed(Get get);
}
