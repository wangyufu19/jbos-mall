package com.mall.admin.infrastructure.repository.im;


import com.mall.admin.domain.entity.im.MaterialOutStore;
import com.mall.admin.domain.entity.im.MaterialOutStoreList;
import com.mall.admin.infrastructure.repository.im.mapper.MaterialOutStoreMapper;
import com.mall.common.page.PageParam;
import com.mall.common.page.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * MaterialOutStoreRepo
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class MaterialOutStoreRepo {
    @Autowired
    private MaterialOutStoreMapper materialOutStoreMapper;
    @Paging
    public List<MaterialOutStore> getMaterialOutStoreList(PageParam pageParam, Map<String, Object> parameterObject){
        return materialOutStoreMapper.getMaterialOutStoreList(parameterObject);
    }
    public MaterialOutStore getMaterialOutStoreById(String id){
        return materialOutStoreMapper.getMaterialOutStoreById(id);
    }

    public void addMaterialOutStore(MaterialOutStore MaterialOutStore){
        materialOutStoreMapper.addMaterialOutStore(MaterialOutStore);
    }

    public void updateMaterialOutStore(MaterialOutStore MaterialOutStore){
        materialOutStoreMapper.updateMaterialOutStore(MaterialOutStore);
    }

    public void deleteMaterialOutStore(Map<String, Object> parameterObject){
        materialOutStoreMapper.deleteByMap(parameterObject);
    }

    public void addMaterialOutStoreItem(List<MaterialOutStoreList> materialOutStoreListList){
        materialOutStoreMapper.addMaterialOutStoreItem(materialOutStoreListList);
    }

    public List<MaterialOutStoreList> getMaterialOutStoreItem(Map<String, Object> parameterObject){
        return materialOutStoreMapper.getMaterialOutStoreItem(parameterObject);
    }
    public void deleteMaterialOutStoreItem(Map<String, Object> parameterObject){
        materialOutStoreMapper.deleteMaterialOutStoreItem(parameterObject);
    }
    public void updateInstIdAndBizState(Map<String, Object> parameterObject){
        materialOutStoreMapper.updateInstIdAndBizState(parameterObject);
    }
    public void updateBizState(Map<String, Object> parameterObject){
        materialOutStoreMapper.updateBizState(parameterObject);
    }
}
