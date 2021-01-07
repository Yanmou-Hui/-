package com.cdcas.coupon.service.impl;

import com.cdcas.coupon.mapper.AdminMapper;
import com.cdcas.coupon.pojo.Admin;
import com.cdcas.coupon.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin login(String phone,String pwd) {
        return adminMapper.login(phone,pwd);
    }
}
