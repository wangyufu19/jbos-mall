package com.mall.admin.application.service.abs;
import com.mall.admin.domain.entity.abs.ProjectInfo;
import com.mall.admin.infrastructure.repository.abs.ProjectApprovalRepo;
import com.mall.common.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ProjectApprovalService
 *
 * @author youfu.wang
 * @date 2023/8/21
 **/
@Service
public class ProjectApprovalService {
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
     * 新增项目信息
     *
     * @param projectInfo
     */
    public void addProjectInfo(ProjectInfo projectInfo) {
        projectApprovalRepo.addProjectInfo(projectInfo);
    }

    /**
     * 修改项目信息
     *
     * @param projectInfo
     */
    public void updateProjectInfo(ProjectInfo projectInfo) {
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
