package com.cdcas.coupon.service;

import com.cdcas.coupon.pojo.Manager;

import java.util.Map;

public interface ManagerService {
    Manager login(String phone);

    Map getList(Integer page, String name);

    Integer insert(Manager manager);

    Integer delete(Manager manager);

    Integer update(Manager manager);
}
