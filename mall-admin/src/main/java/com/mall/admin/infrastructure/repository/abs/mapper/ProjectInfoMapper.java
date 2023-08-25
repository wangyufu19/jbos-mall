package com.mall.admin.infrastructure.repository.abs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.abs.ProjectInfo;

import java.util.List;
import java.util.Map;

/**
 * ProjectInfoMapper
 *
 * @author youfu.wang
 * @date 2023/8/21
 **/
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {
    /**
     * 查询项目立项数据
     * @param parameterObject
     * @return list
     */
    List<ProjectInfo> getProjectList(Map<String, Object> parameterObject);
}
