package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.im.MaterialOutStore;

import java.util.List;
import java.util.Map;

/**
 * MaterialOutStoreMapper
 * @author youfu.wang
 * @date 2020-06-24
 */
public interface MaterialOutStoreMapper extends BaseMapper<MaterialOutStore> {

    public List<MaterialOutStore> getMaterialOutStoreList(Map<String, Object> parameterObject);

    public MaterialOutStore getMaterialOutStoreById(String id);

    public void addMaterialOutStore(Map<String, Object> parameterObject);

    public void updateMaterialOutStore(Map<String, Object> parameterObject);

    public void deleteMaterialOutStore(Map<String, Object> parameterObject);

    public void updateInstIdAndBizState(Map<String, Object> parameterObject);

    public void updateBizState(Map<String, Object> parameterObject);

}
