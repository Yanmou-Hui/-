package com.cdcas.coupon.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Params {
    private Integer page;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private Date from;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    private Date to;
}
