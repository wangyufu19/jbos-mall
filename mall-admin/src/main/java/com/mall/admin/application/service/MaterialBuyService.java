package com.mall.admin.application.service;

import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.admin.infrastructure.repository.im.MaterialBuyRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * MaterialBuyService
 * @author youfu.wang
 * @date 2020-06-24
 */
@Service
public class MaterialBuyService {
    private MaterialBuyRepo materialBuyRepo;

    public List<MaterialBuy> getMaterialBuyList(Map<String, Object> parameterObject){
        return materialBuyRepo.getMaterialBuyList(parameterObject);
    }

    public void addMaterialBuy(MaterialBuy materialBuy){
        materialBuyRepo.addMaterialBuy(materialBuy);
    }

    public void updateMaterialBuy(MaterialBuy materialBuy){
        materialBuyRepo.updateMaterialBuy(materialBuy);
    }

    public void deleteMaterialBuy(Map<String, Object> parameterObject){
        materialBuyRepo.deleteMaterialBuy(parameterObject);
    }
}
