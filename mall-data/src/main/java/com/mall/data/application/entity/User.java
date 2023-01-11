package com.mall.data.application.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private int uid;
    private String userName;
    private int sex;
    private int age;
    private String address;
}
