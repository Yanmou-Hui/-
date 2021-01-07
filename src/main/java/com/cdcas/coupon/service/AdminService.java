package com.cdcas.coupon.service;

import com.cdcas.coupon.pojo.Admin;

public interface AdminService {
    Admin login(String phone,String pwd);
}
