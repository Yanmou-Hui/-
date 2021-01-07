package com.cdcas.coupon.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Return 给前端返回固定格式的工具类
 */
public class JsonResult {
    private Map<String, Object> dataMap = new HashMap<String, Object>();

    // 针对code
    public void setCode(Object code) {
        dataMap.put("code", code);
    }

    // 针对msg
    public void setMsg(Object msg) {
        dataMap.put("msg", msg);
    }

    // 针对data
    public void setData(Object data) {
        dataMap.put("data", data);
    }

    public Map<String, Object> getValues() {
        return dataMap;
    }

    public void put(String key, Object value) {
        dataMap.put(key, value);
    }
}
