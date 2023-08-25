package com.mall.admin.application.response.abs;

import com.mall.admin.domain.entity.abs.AcctInfo;
import com.mall.admin.domain.entity.abs.ProjectInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * ProjectApprovalResponseDto
 *
 * @author youfu.wang
 * @date 2023/8/22
 **/
@Setter
@Getter
public class ProjectApprovalResponseDto {
    /**
     * 项目信息实体
     */
    private ProjectInfo projectInfo;
    /**
     * 账户信息实体
     */
    private AcctInfo acctInfo;
}
