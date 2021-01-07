package com.cdcas.coupon.controller;

import com.cdcas.coupon.util.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@ResponseBody
public class UploadController {

    @PostMapping("/upload")
    public Map upload(@RequestParam("file")MultipartFile multipartFile, HttpSession session) throws IOException {
        JsonResult result = new JsonResult();
        // 获取原始文件名    test.png
        String originalFilename = multipartFile.getOriginalFilename();
        // 获取文件的后缀 suffix prefix :  png
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 全球唯一值UUID
        String uuid = UUID.randomUUID().toString();    //"234sdf24-2343fasdf23=23423423"
        uuid = uuid.replaceAll("-", "");

        // 判断文件大小并限制
        long size = multipartFile.getSize();   // 1024*1024
        if(size> 1024*1024){
            result.setCode(0);
            return result.getValues();
            // return ;
        }

        // 核心上传方法
        // 获取当前程序在服务器中部署时的根路径
        ServletContext servletContext = session.getServletContext();  // 获取上下文
        String realPath = servletContext.getRealPath("/");

        // 获取文件上传的路径
        String pathname = realPath+"static\\images\\"+ uuid+suffix;

        File targetFile = new File(pathname);
        multipartFile.transferTo(targetFile);


        session.setAttribute("imgUrl",pathname);
        // JsonResult 给用户反馈
        result.setCode(1);
        result.put("fileName", uuid + suffix);
        return result.getValues();
    }
}
