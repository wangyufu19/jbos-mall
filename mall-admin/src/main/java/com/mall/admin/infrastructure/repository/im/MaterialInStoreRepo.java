package com.mall.admin.infrastructure.repository.im;


import com.mall.admin.domain.entity.im.MaterialInStore;
import com.mall.admin.infrastructure.repository.im.mapper.MaterialInStoreMapper;
import com.mall.common.page.PageParam;
import com.mall.common.page.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * MaterialInstoreRepo
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class MaterialInStoreRepo {
    @Autowired
    private MaterialInStoreMapper materialInstoreMapper;
    @Paging
    public List<MaterialInStore> getMaterialInStoreList(PageParam pageParam, Map<String, Object> parameterObject){
        return materialInstoreMapper.getMaterialInStoreList(parameterObject);
    }
    public MaterialInStore getMaterialInStoreById(String id){
        return materialInstoreMapper.getMaterialInStoreById(id);
    }

    public void addMaterialInStore(MaterialInStore MaterialInStore){
        materialInstoreMapper.insert(MaterialInStore);
    }

    public void updateMaterialInStore(MaterialInStore MaterialInStore){
        materialInstoreMapper.updateById(MaterialInStore);
    }

    public void deleteMaterialInStore(Map<String, Object> parameterObject){
        materialInstoreMapper.deleteByMap(parameterObject);
    }
    public void updateInstIdAndBizState(Map<String, Object> parameterObject){
        materialInstoreMapper.updateInstIdAndBizState(parameterObject);
    }
    public void updateBizState(Map<String, Object> parameterObject){
        materialInstoreMapper.updateBizState(parameterObject);
    }
}
