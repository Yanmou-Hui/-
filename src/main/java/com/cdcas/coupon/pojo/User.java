package com.cdcas.coupon.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


/*开发中, 对于实体类的设计规范和要求
1. 实现序列化接口(前后端通信时,对于实体类数据的传输都是序列化数据)
2. 命名都是字母(单词)
3. 对于字段封装的要求(private , getter/setter)
4. 基本数据类型都用对应的封装类型
5. 如果要添加全字段的构造方法,必须再添加一个空参构造
6. 为了调试方便,可以选择toString的重写方法*/

public class User implements Serializable {

    // 针对会员设计的实体类:
    // 表名: tb_user
    private Integer id;
    private String name;
    private String phone;
    // alt+enter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date birth;

    // 从数据库中查询的目标时间值转换为json的时候，默认使用时间戳
    // 时间戳
    // Jackson使用@JsonFormat注解快速实现格式化处理
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Date regTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public User(Integer id, String name, String phone, Date birth, Date regTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.regTime = regTime;
    }

    public User(){

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", birth=" + birth +
                ", regTime=" + regTime +
                '}';
    }
}
