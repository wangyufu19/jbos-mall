package com.mall.admin.application.request.im;

import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.domain.entity.im.MaterialInStore;
import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MaterialBuyDto
 *
 * @author youfu.wang
 * @date 2023/5/31
 **/
@Data
public class MaterialInStoreDto {
    private String action;
    private MaterialInStore materialInStore;
    private List<MaterialList> materialList;

    public static MaterialInStoreDto build(Map<String, Object> params){
        MaterialInStoreDto dto=new MaterialInStoreDto();
        Map<String,Object> materialBuyMap=(Map<String,Object>)params.get("formObj");
        dto.setAction(StringUtils.replaceNull(params.get("action")));
        MaterialInStore materialInStore=new MaterialInStore();
        if("create".equals(dto.getAction())){
            materialInStore.setId(StringUtils.getUUID());
            materialInStore.setBizState("10");
        }else{
            materialInStore.setId(StringUtils.replaceNull(materialBuyMap.get("id")));
        }
        materialInStore.setBizNo(StringUtils.replaceNull(materialBuyMap.get("bizNo")));
        materialInStore.setApplyUserId(StringUtils.replaceNull(materialBuyMap.get("applyUserId")));
        materialInStore.setApplyDepId(StringUtils.replaceNull(materialBuyMap.get("applyDepId")));
        materialInStore.setApplyTime(DateUtils.parse(StringUtils.replaceNull(materialBuyMap.get("applyTime"))));
        materialInStore.setTotalAmt(Double.parseDouble(StringUtils.replaceNull(materialBuyMap.get("totalAmt"))));
        dto.setMaterialInStore(materialInStore);

        List<Map<String,Object>> materials=(ArrayList<Map<String,Object>>)params.get("materials");

        if(!CollectionUtils.isEmpty(materials)){
            List<MaterialList> materialList=new ArrayList<>();
            for(Map<String,Object> materialMap:materials){
                MaterialList material=new MaterialList();
                material.setId(StringUtils.getUUID());
                material.setBizId(materialInStore.getId());
                material.setBizType(ProcessDefConstants.PROC_DEF_MATERIAL_IN_STORE);
                material.setMaterialId(StringUtils.replaceNull(materialMap.get("materialId")));
                material.setMaterialName(StringUtils.replaceNull(materialMap.get("materialName")));
                material.setAmount(Double.parseDouble(StringUtils.replaceNull(materialMap.get("amount"))));
                material.setPrice(Double.parseDouble(StringUtils.replaceNull(materialMap.get("price"))));
                materialList.add(material);
            }
            dto.setMaterialList(materialList);
        }
        return dto;
    }
}
