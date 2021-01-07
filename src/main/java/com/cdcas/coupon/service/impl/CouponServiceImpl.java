package com.cdcas.coupon.service.impl;

import com.cdcas.coupon.mapper.CouponMapper;
import com.cdcas.coupon.pojo.Coupon;
import com.cdcas.coupon.pojo.Get;
import com.cdcas.coupon.pojo.User;
import com.cdcas.coupon.service.CouponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("couponService")
@Transactional
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Override
    public Boolean add(Coupon item) {
        int insert = couponMapper.add(item);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Coupon> selectByPage(Integer page) {
        if (page == null) {
            return couponMapper.getAll();
        }
        return couponMapper.selectByPage((page - 1) * 10);
    }

    @Override
    public List<Coupon> selectByInUse(String phone) {
        return couponMapper.selectByInUse(phone);
    }

    @Override
    public int update(Integer cpId, Integer use) {
        boolean cp_use = false;
        if (use == 1) {
            cp_use = true;
        }
        return couponMapper.update(cpId, cp_use);
    }

    @Override
    public List<Coupon> selectHasGet(boolean isUsed, String phone, Date date) {
        if (isUsed) {
            return couponMapper.selectHasGetHasUsed(isUsed, phone, date);
        } else {
            return couponMapper.selectHasGetNotUsed(isUsed, phone, date);
        }
    }

    @Override
    public int acquire(Get get) {
        return couponMapper.acquire(get);
    }

    @Override
    public Map getAll() {
        List<Coupon> couponList = couponMapper.getAll();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("data", couponList);
        return dataMap;
    }
}
