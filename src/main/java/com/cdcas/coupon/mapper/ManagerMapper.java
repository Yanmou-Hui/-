package com.cdcas.coupon.mapper;

import com.cdcas.coupon.pojo.Manager;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ManagerMapper {
    Manager login(String phone);

    List<Manager> getList(Integer offset, String name);

    Integer getCount(String name);

    Integer insert(Manager manager);

    Integer delete(Manager manager);

    Integer update(Manager manager);
}