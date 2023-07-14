package com.mall.admin.infrastructure.repository.im;

import com.mall.admin.domain.entity.im.MaterialStore;
import com.mall.admin.infrastructure.repository.im.mapper.MaterialStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void addMaterialStore(List<MaterialStore> materialStoreList){
        materialStoreMapper.addMaterialStore(materialStoreList);
    }
}
