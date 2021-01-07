package com.cdcas.coupon.controller;

import com.cdcas.coupon.pojo.Admin;
import com.cdcas.coupon.service.AdminService;
import com.cdcas.coupon.util.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @RequestMapping("login")
    public Map login(@RequestParam("phone") String phone,@RequestParam("pwd") String pwd) {
        JsonResult jsonResult = new JsonResult();

        Admin login = adminService.login(phone,pwd);
        if (login != null) {
            jsonResult.setCode(1);
            jsonResult.setData(login);
        } else {
            jsonResult.setCode(0);
        }

        return jsonResult.getValues();
    }
}
