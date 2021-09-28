package com.mall.admin.application.service.im;

import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.infrastructure.repository.im.MaterialBuyRepo;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * MaterialBuyService
 * @author youfu.wang
 * @date 2020-06-24
 */
@Service
public class MaterialBuyService {
    @Autowired
    private MaterialBuyRepo materialBuyRepo;
    @Autowired
    private MaterialListService materialListService;

    public List<MaterialBuy> getMaterialBuyList(Map<String, Object> parameterObject){
        return materialBuyRepo.getMaterialBuyList(parameterObject);
    }
    public MaterialBuy getMaterialBuyById(String id){
        return materialBuyRepo.getMaterialBuyById(id);
    }
    @Transactional
    public void addMaterialBuy(MaterialBuy materialBuy,List<Map<String,Object>> materials){
        materialBuyRepo.addMaterialBuy(materialBuy);
        if(materials!=null){
            for(Map<String,Object> materialMap:materials){
                MaterialList materialList=new MaterialList();
                materialList.setId(StringUtils.getUUID());
                materialList.setBizId(materialBuy.getId());
                materialList.setBizType("BUY");
                materialList.setMaterialName(StringUtils.replaceNull(materialMap.get("materialName")));
                materialList.setAmount(Double.parseDouble(StringUtils.replaceNull(materialMap.get("amount"))));
                materialList.setPrice(Double.parseDouble(StringUtils.replaceNull(materialMap.get("price"))));
                materialListService.addMaterialList(materialList);
            }
        }
    }

    public void updateMaterialBuy(MaterialBuy materialBuy){
        materialBuyRepo.updateMaterialBuy(materialBuy);
    }

    public void deleteMaterialBuy(Map<String, Object> parameterObject){
        materialBuyRepo.deleteMaterialBuy(parameterObject);
    }
}
