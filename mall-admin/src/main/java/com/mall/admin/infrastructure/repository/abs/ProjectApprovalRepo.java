package com.mall.admin.infrastructure.repository.abs;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mall.admin.domain.entity.abs.ProjectInfo;
import com.mall.admin.infrastructure.repository.abs.mapper.ProjectInfoMapper;
import com.mall.common.page.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ProjectApprovalRepo
 *
 * @author youfu.wang
 * @date 2023/8/21
 **/
@Component
public class ProjectApprovalRepo {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    /**
     * 查询项目立项数据
     *
     * @param pageParam
     * @param parameterObject
     * @return list
     */
    public List<ProjectInfo> getProjectList(PageParam pageParam, Map<String, Object> parameterObject) {
        return projectInfoMapper.getProjectList(parameterObject);
    }

    /**
     * 新增项目信息
     *
     * @param projectInfo
     */
    public void addProjectInfo(ProjectInfo projectInfo) {
        projectInfoMapper.insert(projectInfo);
    }

    /**
     * 修改项目信息
     *
     * @param projectInfo
     */
    public void updateProjectInfo(ProjectInfo projectInfo) {
        projectInfoMapper.updateById(projectInfo);
    }

    /**
     * 删除项目信息
     *
     * @param id
     * @param projectNo
     */
    public void deleteProjectInfo(String id, String projectNo) {
        UpdateWrapper<ProjectInfo> updateWrapper = new UpdateWrapper<ProjectInfo>();
        updateWrapper.eq("id", id);
        updateWrapper.eq("project_no", projectNo);
        projectInfoMapper.delete(updateWrapper);
    }
}
