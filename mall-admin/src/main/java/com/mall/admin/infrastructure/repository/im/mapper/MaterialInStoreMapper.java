package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.im.MaterialInStore;

import java.util.List;
import java.util.Map;

/**
 * MaterialInstoreMapper
 * @author youfu.wang
 * @date 2020-06-24
 */
public interface MaterialInStoreMapper extends BaseMapper<MaterialInStore> {

    public List<MaterialInStore> getMaterialInStoreList(Map<String, Object> parameterObject);

    public MaterialInStore getMaterialInStoreById(String id);

    public void addMaterialInStore(Map<String, Object> parameterObject);

    public void updateMaterialInStore(Map<String, Object> parameterObject);

    public void deleteMaterialInStore(Map<String, Object> parameterObject);

    public void updateInstIdAndBizState(Map<String, Object> parameterObject);

    public void updateBizState(Map<String, Object> parameterObject);

}
