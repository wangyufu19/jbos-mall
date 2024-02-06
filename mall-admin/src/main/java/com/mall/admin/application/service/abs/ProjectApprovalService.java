package com.mall.admin.application.service.abs;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.mall.admin.application.request.BaseRequestDto;
import com.mall.admin.application.request.abs.ProjectApprovalRequestDto;
import com.mall.admin.application.response.abs.ProjectApprovalResponseDto;
import com.mall.admin.domain.entity.abs.AcctInfo;
import com.mall.admin.domain.entity.abs.ProjectInfo;
import com.mall.common.page.PageParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * ProjectApprovalService
 *
 * @author youfu.wang
 * @date 2024/2/6 14:31
 */
public interface ProjectApprovalService {

    /**
     * 查询项目立项数据
     *
     * @param pageParam
     * @param parameterObject
     * @return list
     */
    List<ProjectInfo> getProjectList(PageParam pageParam, Map<String, Object> parameterObject);

    /**
     * 根据id查询项目登记信息
     *
     * @param id
     * @return dto
     */
    ProjectApprovalResponseDto getProjectApproval(String id);

    /**
     * 项目立项登记或变更
     *
     * @param projectApprovalDto
     */
    void addOrUpdateProjectApproval(ProjectApprovalRequestDto projectApprovalDto);

    /**
     * 根据id查询项目信息
     *
     * @param id
     * @return ProjectInfo
     */
    ProjectInfo getProjectInfo(String id);

    /**
     * 新增项目信息
     *
     * @param projectInfo
     */
    void addProjectInfo(ProjectInfo projectInfo);
    /**
     * 修改项目信息
     *
     * @param projectInfo
     */
    void updateProjectInfo(ProjectInfo projectInfo);

    /**
     * 删除项目信息
     *
     * @param id
     * @param projectNo
     */
    void deleteProjectInfo(String id, String projectNo);
}
