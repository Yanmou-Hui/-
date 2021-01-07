package com.cdcas.coupon.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Coupon {
    private Integer id;

    private String name;

    private String desc;

    private String img;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date collectExpire;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date useExpire;

    private Boolean inUse;

    private String role;

}