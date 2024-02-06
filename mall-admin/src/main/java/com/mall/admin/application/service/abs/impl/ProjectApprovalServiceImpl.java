package com.mall.admin.application.service.abs.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.mall.admin.application.request.BaseRequestDto;
import com.mall.admin.application.request.abs.ProjectApprovalRequestDto;
import com.mall.admin.application.response.abs.ProjectApprovalResponseDto;
import com.mall.admin.application.service.abs.ProjectApprovalService;
import com.mall.admin.domain.entity.abs.AcctInfo;
import com.mall.admin.domain.entity.abs.ProjectInfo;
import com.mall.admin.infrastructure.repository.abs.ProjectApprovalRepo;
import com.mall.common.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * ProjectApprovalService
 *
 * @author youfu.wang
 * @date 2023/8/21
 **/
@Service
public class ProjectApprovalServiceImpl implements ProjectApprovalService {
    /**
     * projectApprovalRepo
     */
    @Autowired
    private ProjectApprovalRepo projectApprovalRepo;

    /**
     * 查询项目立项数据
     *
     * @param pageParam
     * @param parameterObject
     * @return list
     */
    public List<ProjectInfo> getProjectList(PageParam pageParam, Map<String, Object> parameterObject) {
        return projectApprovalRepo.getProjectList(pageParam, parameterObject);
    }

    /**
     * 根据id查询项目登记信息
     *
     * @param id
     * @return dto
     */
    public ProjectApprovalResponseDto getProjectApproval(String id) {
        ProjectApprovalResponseDto dto = new ProjectApprovalResponseDto();
        dto.setProjectInfo(projectApprovalRepo.getProjectInfo(id));
        dto.setAcctInfo(projectApprovalRepo.getAcctInfo(id));
        return dto;
    }

    /**
     * 项目立项登记或变更
     *
     * @param projectApprovalDto
     */
    @Transactional
    public void addOrUpdateProjectApproval(ProjectApprovalRequestDto projectApprovalDto) {
        if (BaseRequestDto.ACTION_CREATE.equals(projectApprovalDto.getAction())) {
            ProjectInfo projectInfo = projectApprovalDto.getProjectInfo();
            projectInfo.setId(IdUtil.randomUUID());
            this.addProjectInfo(projectInfo);
            AcctInfo acctInfo = projectApprovalDto.getAcctInfo();
            acctInfo.setId(IdUtil.randomUUID());
            acctInfo.setProjectId(projectInfo.getId());
            projectApprovalRepo.addAcctInfo(acctInfo);
        } else if (BaseRequestDto.ACTION_UPDATE.equals(projectApprovalDto.getAction())) {
            this.updateProjectInfo(projectApprovalDto.getProjectInfo());
            projectApprovalRepo.updateAcctInfo(projectApprovalDto.getAcctInfo());
        }
    }

    /**
     * 根据id查询项目信息
     *
     * @param id
     * @return ProjectInfo
     */
    public ProjectInfo getProjectInfo(String id) {
        return projectApprovalRepo.getProjectInfo(id);
    }

    /**
     * 新增项目信息
     *
     * @param projectInfo
     */
    public void addProjectInfo(ProjectInfo projectInfo) {
        projectInfo.setProjectSte(ProjectInfo.PROJECT_STE_NORMAL);
        projectInfo.setCreateTime(DateUtil.now());
        projectApprovalRepo.addProjectInfo(projectInfo);
    }

    /**
     * 修改项目信息
     *
     * @param projectInfo
     */
    public void updateProjectInfo(ProjectInfo projectInfo) {
        projectInfo.setUpdateTime(DateUtil.now());
        projectApprovalRepo.updateProjectInfo(projectInfo);
    }

    /**
     * 删除项目信息
     *
     * @param id
     * @param projectNo
     */
    public void deleteProjectInfo(String id, String projectNo) {
        projectApprovalRepo.deleteProjectInfo(id, projectNo);
    }
}
