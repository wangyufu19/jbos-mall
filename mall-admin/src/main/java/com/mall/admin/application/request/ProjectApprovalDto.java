package com.mall.admin.application.request;

import com.mall.admin.domain.entity.abs.ProjectInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * ProjectApprovalDto
 *
 * @author youfu.wang
 * @date 2023/8/21
 **/
@Setter
@Getter
public class ProjectApprovalDto {
    /**
     * 操作类型(create;update)
     */
    private String action;
    /**
     * 项目信息实体
     */
    private ProjectInfo projectInfo;
}
