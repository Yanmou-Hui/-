package com.cdcas.coupon.service.impl;

import com.cdcas.coupon.mapper.ManagerMapper;
import com.cdcas.coupon.pojo.Manager;
import com.cdcas.coupon.service.ManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("managerService")
@Transactional
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private ManagerMapper managerMapper;

    @Override
    public Manager login(String phone) {
        return managerMapper.login(phone);
    }

    @Override
    public Map getList(Integer page, String name) {
        List<Manager> list = managerMapper.getList((page - 1) * 10, name);
        Integer count = managerMapper.getCount(name);

        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        map.put("count", count);

        return map;
    }

    @Override
    public Integer insert(Manager manager) {
        return managerMapper.insert(manager);
    }

    @Override
    public Integer delete(Manager manager) {
        return managerMapper.delete(manager);
    }

    @Override
    public Integer update(Manager manager) {
        return managerMapper.update(manager);
    }
}
