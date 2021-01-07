package com.cdcas.coupon.mapper;

import com.cdcas.coupon.pojo.Admin;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    Admin login(String phone,String pwd);
}