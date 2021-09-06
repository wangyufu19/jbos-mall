package com.mall.admin.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Emp extends BaseEntity implements Serializable{
    private String badge;
    private String empName;
    private String orgId;
    private String orgName;
    private String depId;
    private String depName;
    private String depEmpName;
    private String headShip;
    private String headShipName;
    private String empStatus;
    private String empStatusName;
}

