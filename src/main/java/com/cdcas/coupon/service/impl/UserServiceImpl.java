package com.cdcas.coupon.service.impl;

import com.cdcas.coupon.mapper.UserMapper;
import com.cdcas.coupon.pojo.Get;
import com.cdcas.coupon.pojo.User;
import com.cdcas.coupon.pojo.UserCouponVo;
import com.cdcas.coupon.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    //声明UserMapper的实例
    @Resource
    UserMapper userMapper = null;

    public Boolean register(User user) {
        // 注册时,判断用户输入的手机号是否已经被注册过
        User selectUser = userMapper.selectByPhone(user.getPhone());
        Integer insert = 0;
        // 如果没有被注册,则继续注册流程
        if(selectUser == null){
            insert = userMapper.insert(user);
        }
        //否则注册失败
        return insert>0;
    }

    public User login(String phone) {
        return userMapper.selectByPhone(phone);
    }

    @Override
    public Map findAll(Integer page, String name) {
        List<User> userList = null;
        // 如果不加分页条件，且不加模糊查询条件，此查询为全查询
        if (page == null && name == null) {
            userList = userMapper.selectAllNoArgs();
        } else {        // 在业务层将page转为偏移量offset
            userList = userMapper.selectAll((page - 1) * 10, name);
        }

        Integer count = userMapper.getCount(name);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("count", count);
        dataMap.put("data", userList);

        return dataMap;
    }

    @Override
    public Map getCpnUsedList(Integer page, Date from, Date to) {
        Map<String,Object> map = new HashMap<>();

        if (page == null) {
            List<UserCouponVo> allCpnUsedList = userMapper.getCpnUsedList(null, from, to);
            map.put("data", allCpnUsedList);
        } else {
            List<UserCouponVo> cpnUsedList = userMapper.getCpnUsedList((page - 1) * 10, from, to);
            Integer cpnUsedListCount = userMapper.getCpnUsedListCount(from, to);

            map.put("data", cpnUsedList);
            map.put("count", cpnUsedListCount);
        }

        return map;
    }

    @Override
    public Map selectCpnList(Integer page, String phone) {
        Map<String,Object> map = new HashMap<>();

        List<UserCouponVo> userCouponVos = null;
        if (page != null) {
            userCouponVos = userMapper.selectCpnList((page - 1) * 10, phone);
        } else {
            userCouponVos = userMapper.selectCpnList(null, phone);
        }
        map.put("data", userCouponVos);

        Integer count = userMapper.selectCpnListCount(phone);
        map.put("count", count);

        return map;
    }

    @Override
    public Integer updateUsed(Get get) {
        return userMapper.updateUsed(get);
    }
}
