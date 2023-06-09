package com.mall.admin.domain.entity.wf;

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
public class ProcessDeployment extends BaseEntity implements Serializable {
    private String procName;
    private String procKey;
    private String version;
    private String resource;
    private String deployTime;
}
