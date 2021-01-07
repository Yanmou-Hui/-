package com.cdcas.coupon.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Admin {
    private Integer id;

    private String name;

    private String pwd;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date regTime;
}