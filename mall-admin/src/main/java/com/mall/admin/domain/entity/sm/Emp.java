package com.mall.admin.domain.entity.sm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.comm.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@TableName("JBOS_EMP")
@ApiModel("人员信息")
public class Emp extends BaseEntity implements Serializable {
    /**
     * 用户Id
     */
    @ApiModelProperty(value = "用户Id", required = true)
    @TableField("userid")
    private String userId;
    /**
     * 员工号
     */
    @ApiModelProperty(value = "员工号", required = true)
    @TableField("badge")
    private String badge;
    /**
     * 员工姓名
     */
    private String empName;
    /**
     * 所属机构Id
     */
    private String orgId;
    /**
     * 所属机构
     */
    private String orgName;
    /**
     * 所属部门Id
     */
    private String depId;
    /**
     * 所属部门
     */
    private String depName;
    /**
     * depEmpName
     */
    private String depEmpName;
    /**
     * 部门领导工号
     */
    private String headShip;
    /**
     * 部门领导
     */
    private String headShipName;
    /**
     * 员工状态
     */
    private String empStatus;
    /**
     * 员工状态
     */
    private String empStatusName;
}

