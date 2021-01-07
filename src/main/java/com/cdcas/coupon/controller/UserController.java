package com.cdcas.coupon.controller;

import com.cdcas.coupon.pojo.*;
import com.cdcas.coupon.service.CouponService;
import com.cdcas.coupon.service.UserService;
import com.cdcas.coupon.util.JsonResult;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
/* 告诉服务器如何访问到当前类
    / 代表localhost:8080
 */
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private CouponService couponService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/login")  // localhost:8080/user/login
    public Map login(@RequestParam("phone") String phone) {
        JsonResult result = new JsonResult();

        User login = userService.login(phone);
        if (login != null) {
            result.setCode(1);
            result.setData(login);
        } else {
            result.setCode(0);
        }
        return result.getValues();
    }

    //     如果没有指定RequestMethod，默认支持POST和GET
//    @RequestMapping(value = "/reg", method = {RequestMethod.POST})
    @PostMapping("/reg")
    public Map<String, Object> reg(@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("captcha") String captcha, HttpSession session) {
        JsonResult result = new JsonResult();
//        1、@RequestParam("") : 申明在方法的参数列表中，用于修饰对应的参数

//        2、验证码的处理方法
//        2.1、如何发送(腾讯云/阿里云)
//         具体操作：
//         服务器要生成4位数字验证码：
//        声明一个可追加字符串

//        调用发送接口发送
//        同时服务器还要和用户输入的值做对比

        String code = String.valueOf(redisTemplate.opsForValue().get(phone));
//        String code = session.getAttribute("code").toString();
//        System.out.println("服务器访问的code:" + code);

//        String res = null;
        if (captcha.equals(code)) {
//            注册成功，返回1
            User user = new User();
            user.setName(name);
            user.setPhone(phone);
            user.setRegTime(new Date());

            Boolean register = userService.register(user);

            if (register) {
//                System.out.println("注册成功！");
//                res = "{\"code\":1}";
                result.setCode(1);
            } else {
//                System.out.println("注册失败，该手机号已经注册过会员");
//                res = "{\"code\":0}";
                result.setCode(0);
            }

        } else {
//        注册失败，返回0
//            res = "{\"code\":0}";
            result.setCode(0);
        }
        return result.getValues();
//        return res;
    }
/*
    @PostMapping("/reg")
    public void reg(@RequestBody User user) {
//        @RequestBody 把获取到的参数注入到User对象上
//        注意：要求前端必须经过json字符换序列化转换
        System.out.println(user.getName());
        System.out.println(user.getPhone());
    }*/

    @RequestMapping("/getCpnUsedList")
    public Map<String, Object> getCpnUsedList(@RequestBody Params param) {

        Map<String, Object> cpnUsedList = userService.getCpnUsedList(param.getPage(), param.getFrom(), param.getTo());
        cpnUsedList.put("code", 1);

        return cpnUsedList;
    }

    @RequestMapping("/getList")
    public Map<String, Object> getList(@RequestParam("page") Integer page, @RequestParam(value = "name", required = false) String name) {
        Map all = userService.findAll(page, name);
        all.put("code", 1);
        return all;
    }

    @RequestMapping("/getCpnListForWeb")
    public Map<String, Object> getCpnListForWeb(@RequestParam("phone") String phone) {
        JsonResult jsonResult = new JsonResult();

        // 可领取的
        List<Coupon> userNotGetCpnList = couponService.selectByInUse(phone);
        jsonResult.setCode(1);
        jsonResult.put("userNotGetCpnList", userNotGetCpnList);

        // 已经领取的 并且 还没使用
        List<Coupon> userGetCpnList = couponService.selectHasGet(false, phone, new Date());
        jsonResult.put("userGetCpnList", userGetCpnList);

        // 已领取 并且 使用了/过期了
        List<Coupon> overExpire = couponService.selectHasGet(true, phone, new Date());
        jsonResult.put("overExpire", overExpire);

        return jsonResult.getValues();
    }

    @RequestMapping("/getCpn")
    public Map getCpn(@RequestParam("cpId") Integer cpId, @RequestParam("phone") String phone) {
        JsonResult result = new JsonResult();

        Get get = new Get();
        get.setCpId(cpId);
        get.setPhone(phone);
        get.setTime(new Date());

        int acquire = couponService.acquire(get);

        if (acquire > 0) {
            result.setCode(1);
        } else {
            result.setCode(0);
        }

        return result.getValues();
    }

    @RequestMapping("/getCpnList")
    public Map getCpnList(@RequestParam(value = "page", required = false) Integer page, @RequestParam("phone") String phone) {

        Map<String, Object> map = userService.selectCpnList(page, phone);
        List<UserCouponVo> data = (List<UserCouponVo>) map.get("data");

        for (UserCouponVo datum : data) {
            System.out.println("UseExpire:" + datum.getUseExpire() + "\tCollectExpire" + datum.getCollectExpire());
            System.out.println(datum.getUseExpire().getTime() - datum.getCollectExpire().getTime());
        }

        map.put("code", 1);
        return map;
    }

    @RequestMapping("useCpn")
    public Map useCpn(@RequestParam("cpId") Integer cpId, @RequestParam("phone") String phone) {
        JsonResult result = new JsonResult();

        Get get = new Get();
        get.setCpId(cpId);
        get.setPhone(phone);
        get.setIdUsed(true);
        get.setUseTime(new Date());

        Integer update = userService.updateUsed(get);
        if (update > 0) {
            result.setCode(1);
        } else {
            result.setCode(0);
        }
        return result.getValues();
    }

    @RequestMapping("/exportList")
    public void exportList(HttpServletResponse response) throws IOException {
        // 查询所有的用户列表信息
        Map all = userService.findAll(null, null);
        List<User> userList = (List<User>) all.get("data");
        // 将列表数据填充到excel文件中

        // POI技术

        // 新建excel 工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //添加sheet:
        HSSFSheet getSheet = workbook.createSheet("sheetName");
        // headers表示excel表中第一行的表头 在excel表中添加表头
        String[] headers = {"序号", "姓名", "手机号", "注册时间"};
        // 创建第一行(表头)
        HSSFRow row = getSheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            // 在每次遍历过程中创建一个单元格
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);// 将字符串填充到单元格中
        }

        // 数据行，数字1表示第二行
        int rowNum = 1;
        for (User u : userList) { // 每获取List中的一条数据就创建一个新的行
            HSSFRow dataRow = getSheet.createRow(rowNum);
            dataRow.createCell(0).setCellValue(rowNum);
            dataRow.createCell(1).setCellValue(u.getName());
            dataRow.createCell(2).setCellValue(u.getPhone());
            dataRow.createCell(3).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(u.getRegTime()));
            rowNum++;
        }

        // 设置文件名:
        String fileName = "华联注册会员信息表.xls";
        // 对于文件名中的中文进行处理
        fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");

        //返回给前端下载，给调用者提供一个下载功能
        // 通知浏览器，此次相应内容不应该用访问方式，而应该用下载方式
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @RequestMapping("/exportCpnUsedList")
    public void exportCpnUsedList(HttpServletResponse response,
                                  @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                  @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) throws IOException {
        // 查询所有的用户列表信息
        Map all = userService.getCpnUsedList(null, from, to);
        List<UserCouponVo> cpnUsedList = (List<UserCouponVo>) all.get("data");
        // 将列表数据填充到excel文件中
        System.out.println(from + "\t" + to);
        // POI技术

        // 新建excel 工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //添加sheet:
        HSSFSheet getSheet = workbook.createSheet("sheetName");
        // headers表示excel表中第一行的表头 在excel表中添加表头
        String[] headers = {"序号", "姓名", "手机号", "优惠券名称", "兑换时间"};
        // 创建第一行(表头)
        HSSFRow row = getSheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            // 在每次遍历过程中创建一个单元格
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);// 将字符串填充到单元格中
        }

        // 数据行，数字1表示第二行
        int rowNum = 1;
        for (UserCouponVo u : cpnUsedList) { // 每获取List中的一条数据就创建一个新的行
            HSSFRow dataRow = getSheet.createRow(rowNum);
            dataRow.createCell(0).setCellValue(rowNum);
            dataRow.createCell(1).setCellValue(u.getUserName());
            dataRow.createCell(2).setCellValue(u.getPhone());
            dataRow.createCell(3).setCellValue(u.getCpName());
            dataRow.createCell(4).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(u.getUseTime()));
            rowNum++;
        }

        // 设置文件名:
        String fileName = "优惠券兑换清单.xls";
        // 对于文件名中的中文进行处理
        fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");

        //返回给前端下载，给调用者提供一个下载功能
        // 通知浏览器，此次相应内容不应该用访问方式，而应该用下载方式
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
