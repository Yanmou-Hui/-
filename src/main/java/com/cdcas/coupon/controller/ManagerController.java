package com.cdcas.coupon.controller;

import com.cdcas.coupon.pojo.Manager;
import com.cdcas.coupon.pojo.User;
import com.cdcas.coupon.service.ManagerService;
import com.cdcas.coupon.util.JsonResult;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("mgr")
public class ManagerController {

    @Resource
    private ManagerService managerService;

    @PostMapping("login")
    public Map login(@RequestParam("phone") String phone) {
        JsonResult jsonResult = new JsonResult();

        Manager login = managerService.login(phone);
        if (login == null) {
            jsonResult.setCode(0);
        } else {
            jsonResult.setCode(1);
            jsonResult.setData(login);
        }
        return jsonResult.getValues();
    }

    @PostMapping("getList")
    public Map getList(@RequestParam("page") Integer page, @RequestParam(value = "name", required = false) String name) {
        Map list = managerService.getList(page, name);
        list.put("code", 1);

        return list;
    }

    @PostMapping("add")
    public Map add(@RequestBody Manager manager) {
        JsonResult jsonResult = new JsonResult();
        manager.setRegTime(new Date());

        Integer insert = managerService.insert(manager);
        if (insert > 0) {
            jsonResult.setCode(1);
        } else {
            jsonResult.setCode(0);
        }

        return jsonResult.getValues();
    }

    @PostMapping("delete")
    public Map delete(@RequestBody Manager manager) {
        JsonResult jsonResult = new JsonResult();

        Integer insert = managerService.delete(manager);
        if (insert > 0) {
            jsonResult.setCode(1);
        } else {
            jsonResult.setCode(0);
        }

        return jsonResult.getValues();
    }

    @PostMapping("update")
    public Map update(@RequestBody Manager manager) {
        JsonResult jsonResult = new JsonResult();

        Integer insert = managerService.update(manager);
        if (insert > 0) {
            jsonResult.setCode(1);
        } else {
            jsonResult.setCode(0);
        }

        return jsonResult.getValues();
    }
}
