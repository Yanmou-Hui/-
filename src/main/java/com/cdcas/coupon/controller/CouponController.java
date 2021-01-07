package com.cdcas.coupon.controller;

import com.cdcas.coupon.pojo.Coupon;
import com.cdcas.coupon.pojo.User;
import com.cdcas.coupon.service.CouponService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("cpn")
@ResponseBody
public class CouponController {

    @Resource(name = "couponService")
    private CouponService couponService;

    @RequestMapping("add")
    public Map add(@RequestBody Coupon coupon) {
        JsonResult JsonResult = new JsonResult();
        Boolean add = couponService.add(coupon);
        if (add) {
            JsonResult.setCode(1);
        } else {
            JsonResult.setCode(0);
        }
        return JsonResult.getValues();
    }

    @RequestMapping("getList")
    public Map getList(@RequestParam("page") Integer page) {
        JsonResult jsonResult = new JsonResult();
        List<Coupon> cpnList = couponService.selectByPage(page);

        jsonResult.setCode(1);
        jsonResult.setData(cpnList);

        return jsonResult.getValues();
    }

    @RequestMapping("update")
    public Map update(@RequestParam("cpId") Integer cpId,@RequestParam("use") Integer use) {
        JsonResult jsonResult = new JsonResult();
        int update = couponService.update(cpId, use);
        if (update > 0) {
            jsonResult.setCode(1);
        } else {
            jsonResult.setCode(0);
        }

        return jsonResult.getValues();
    }


    @RequestMapping("/exportCpnList")
    public void exportCpnList(HttpServletResponse response) throws IOException, ParseException {
        // 查询所有的优惠券列表信息
        Map all = couponService.getAll();
        List<Coupon> couponList = (List<Coupon>) all.get("data");
        // 将列表数据填充到excel文件中

        // POI技术

        // 新建excel 工作表
        HSSFWorkbook workbook = new HSSFWorkbook();
        //添加sheet:
        HSSFSheet getSheet = workbook.createSheet("sheetName");
        // headers表示excel表中第一行的表头 在excel表中添加表头
        String[] headers = {"序号", "优惠券名称", "优惠券简单描述", "规则", "申领有效期", "使用有效期", "是否上架"};
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
        for (Coupon c : couponList) { // 每获取List中的一条数据就创建一个新的行
            HSSFRow dataRow = getSheet.createRow(rowNum);
            dataRow.createCell(0).setCellValue(rowNum);
            dataRow.createCell(1).setCellValue(c.getName());
            dataRow.createCell(2).setCellValue(c.getDesc());
            dataRow.createCell(3).setCellValue(c.getRole());
            dataRow.createCell(4).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getCollectExpire()));
            dataRow.createCell(5).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getUseExpire()));
            dataRow.createCell(6).setCellValue(c.getInUse());
            rowNum++;
        }

        // 设置文件名:
        String fileName = "优惠券清单.xls";
        // 对于文件名中的中文进行处理
        fileName = URLEncoder.encode(fileName,"utf-8").replaceAll("\\+", "%20");

        //返回给前端下载，给调用者提供一个下载功能
        // 通知浏览器，此次相应内容不应该用访问方式，而应该用下载方式
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
