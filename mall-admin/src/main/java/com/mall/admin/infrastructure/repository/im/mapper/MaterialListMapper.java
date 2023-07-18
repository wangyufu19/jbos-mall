package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.im.MaterialList;

import java.util.List;
import java.util.Map;

/**
 * MaterialBuyMapper
 * @author youfu.wang
 * @date 2020-06-24
 */
public interface MaterialListMapper extends BaseMapper<MaterialList> {

    public List<MaterialList> getMaterialListList(Map<String, Object> parameterObject);
}
