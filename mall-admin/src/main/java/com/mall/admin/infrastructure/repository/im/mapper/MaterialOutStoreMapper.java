package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.im.MaterialOutStore;
import com.mall.admin.domain.entity.im.MaterialOutStoreList;
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

    public void addMaterialOutStore(MaterialOutStore materialOutStore);

    public void updateMaterialOutStore(MaterialOutStore materialOutStore);

    public void deleteMaterialOutStore(Map<String, Object> parameterObject);

    public void addMaterialOutStoreItem(List<MaterialOutStoreList> materialOutStoreListList);

    public List<MaterialOutStoreList> getMaterialOutStoreItem(Map<String, Object> parameterObject);

    public void deleteMaterialOutStoreItem(Map<String, Object> parameterObject);

    public void updateInstIdAndBizState(Map<String, Object> parameterObject);

    public void updateBizState(Map<String, Object> parameterObject);

}
