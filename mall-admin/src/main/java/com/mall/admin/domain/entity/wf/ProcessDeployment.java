package com.mall.admin.domain.entity.wf;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.admin.domain.entity.comm.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * ProcessDef
 *
 * @author youfu.wang
 * @date 2023/6/9
 **/
@Data
@TableName("wf_process_deployment")
public class ProcessDeployment extends BaseEntity implements Serializable {
    @TableField("DEPLOYMENTID")
    private String deploymentId;
    @TableField("PROCNAME")
    private String procName;
    @TableField("PROCKEY")
    private String procKey;
    @TableField("RESOURCE")
    private String resource;
    @TableField("VERSION")
    private String version;
    @TableField("DEPLOYTIME")
    private String deployTime;
}
