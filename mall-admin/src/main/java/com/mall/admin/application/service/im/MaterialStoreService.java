package com.mall.admin.application.service.im;

import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.domain.entity.im.MaterialStore;
import com.mall.admin.infrastructure.repository.im.MaterialStoreRepo;
import com.mall.admin.infrastructure.repository.im.mapper.MaterialStoreMapper;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * MaterialStoreService
 *
 * @author youfu.wang
 * @date 2023/7/13
 **/
@Service
public class MaterialStoreService {
    @Autowired
    private MaterialStoreRepo materialStoreRepo;
    /**
     * 新增物品库存数据
     * @param materialListList
     */
    @Transactional
    public void addMaterialStore(List<MaterialList> materialListList){
        if (materialListList==null){
            return;
        }
        List<MaterialStore> materialStoreList=new ArrayList<>();
        for(MaterialList materialList:materialListList){
            MaterialStore materialStore=new MaterialStore();
            materialStore.setId(StringUtils.getUUID());
            materialStore.setBatchNo(DateUtils.format(DateUtils.getCurrentDate(), "yyyyMMddHHmmss"));
            materialStore.setInBizId(materialList.getBizId());
            materialStore.setMaterialName(materialList.getMaterialName());
            materialStore.setAmount(materialList.getAmount());
            materialStore.setPrice(materialList.getPrice());
            materialStore.setInTime(DateUtils.format(DateUtils.getCurrentDate(), "yyyyMMddHHmmss"));
            materialStoreList.add(materialStore);
        }
        if(materialStoreList.size()>0){
            materialStoreRepo.addMaterialStore(materialStoreList);
        }
    }

}
