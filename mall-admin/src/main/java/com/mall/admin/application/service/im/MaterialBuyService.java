package com.mall.admin.application.service.im;

import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.infrastructure.repository.im.MaterialBuyRepo;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    /**
     * 新增物品采购
     * @param materialBuy
     * @param materials
     */
    @Transactional
    public void addMaterialBuy(MaterialBuy materialBuy,List<Map<String,Object>> materials){
        //新增物品采购基本信息
        materialBuyRepo.addMaterialBuy(materialBuy);
        //新增物品采购清单
        this.addMaterialList(materialBuy.getId(),materials);
    }

    /**
     * 新增物品采购清单
     * @param bizId
     * @param materials
     */
    private void addMaterialList(String bizId,List<Map<String,Object>> materials){
        if(materials!=null){
            for(Map<String,Object> materialMap:materials){
                MaterialList materialList=new MaterialList();
                materialList.setId(StringUtils.getUUID());
                materialList.setBizId(bizId);
                materialList.setBizType("BUY");
                materialList.setMaterialName(StringUtils.replaceNull(materialMap.get("materialName")));
                materialList.setAmount(Double.parseDouble(StringUtils.replaceNull(materialMap.get("amount"))));
                materialList.setPrice(Double.parseDouble(StringUtils.replaceNull(materialMap.get("price"))));
                materialListService.addMaterialList(materialList);
            }
        }
    }
    /**
     * 更新物品采购清单
     * @param materialBuy
     * @param materials
     */
    @Transactional
    public void updateMaterialBuy(MaterialBuy materialBuy,List<Map<String,Object>> materials){
        //更新物品采购基本信息
        materialBuyRepo.updateMaterialBuy(materialBuy);
        Map<String, Object> parameterObject=new HashMap<String, Object>();
        parameterObject.put("bizid",materialBuy.getId());
        parameterObject.put("biztype","BUY");
        //删除物品采购清单
        materialListService.deleteMaterial(parameterObject);
        //新增物品采购清单
        this.addMaterialList(materialBuy.getId(),materials);
    }

    /**
     * 删除物品采购
     * @param parameterObject
     */
    @Transactional
    public void deleteMaterialBuy(Map<String, Object> parameterObject){
        //删除物品采购基本信息
        materialBuyRepo.deleteMaterialBuy(parameterObject);
        Map<String, Object> listParams=new HashMap<String, Object>();
        listParams.put("bizid",StringUtils.replaceNull(parameterObject.get("id")));
        listParams.put("biztype","BUY");
        //删除物品采购清单
        materialListService.deleteMaterial(listParams);
    }
}
