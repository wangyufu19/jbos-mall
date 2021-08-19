package com.mall.admin.infrastructure.repository.mapper;

import com.mall.admin.domain.entity.DictType;

import java.util.List;

/**
 * DictTypeMapper
 * @author youfu.wang
 * @date 2020-07-22
 */
public interface DictTypeMapper {
    /**
     * 得到字典类型数据
     * @return
     */
    public List<DictType> getDictTypeList();
}
