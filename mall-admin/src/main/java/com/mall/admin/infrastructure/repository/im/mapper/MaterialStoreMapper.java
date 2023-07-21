package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.im.MaterialStore;

import java.util.List;
import java.util.Map;

/**
 * MaterialStoreMapper
 *
 * @author youfu.wang
 * @date 2023/7/14
 **/
public interface MaterialStoreMapper extends BaseMapper<MaterialStore> {

    public List<MaterialStore> getMaterialStoreSumList(Map<String, Object> parameterObject);

    public List<MaterialStore> getMaterialStoreList(Map<String, Object> parameterObject);

    public List<MaterialStore> getFIFOInfoByMaterialId(Map<String, Object> parameterObject);

    public void addMaterialStore(List<MaterialStore> materialStoreList);

    public void updateMaterialStore(Map<String, Object> parameterObject);
}
