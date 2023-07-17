package com.mall.admin.infrastructure.repository.im;

import com.mall.admin.domain.entity.im.MaterialInStore;
import com.mall.admin.domain.entity.im.MaterialStore;
import com.mall.admin.infrastructure.repository.im.mapper.MaterialStoreMapper;
import com.mall.common.page.PageParam;
import com.mall.common.page.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * MaterialStoreRepo
 *
 * @author youfu.wang
 * @date 2023/7/14
 **/
@Component
public class MaterialStoreRepo {
    @Autowired
    private MaterialStoreMapper materialStoreMapper;

    @Paging
    public List<MaterialStore> getMaterialStoreList(PageParam pageParam, Map<String, Object> parameterObject){
        return materialStoreMapper.getMaterialStoreList(parameterObject);
    }
    public void addMaterialStore(List<MaterialStore> materialStoreList){
        materialStoreMapper.addMaterialStore(materialStoreList);
    }
}
