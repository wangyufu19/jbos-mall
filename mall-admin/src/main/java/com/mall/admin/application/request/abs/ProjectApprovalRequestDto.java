package com.mall.admin.application.request.abs;

import com.mall.admin.application.request.BaseRequestDto;
import com.mall.admin.domain.entity.abs.AcctInfo;
import com.mall.admin.domain.entity.abs.ProjectInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * ProjectApprovalRequestDto
 *
 * @author youfu.wang
 * @date 2023/8/21
 **/
@Setter
@Getter
public class ProjectApprovalRequestDto extends BaseRequestDto {

    /**
     * 项目信息实体
     */
    private ProjectInfo projectInfo;
    /**
     * 账户信息实体
     */
    private AcctInfo acctInfo;
}
