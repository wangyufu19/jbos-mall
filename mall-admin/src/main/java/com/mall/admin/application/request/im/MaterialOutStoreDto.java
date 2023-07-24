package com.mall.admin.application.request.im;

import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.domain.entity.im.MaterialOutStore;
import com.mall.admin.domain.entity.im.MaterialOutStoreList;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.NumberUtils;
import com.mall.common.utils.StringUtils;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MaterialOutStoreDto
 *
 * @author youfu.wang
 * @date 2023/5/31
 **/
@Data
public class MaterialOutStoreDto {
    private String action;
    private MaterialOutStore materialOutStore;
    private List<MaterialOutStoreList> materialList;

    public static MaterialOutStoreDto build(Map<String, Object> params){
        MaterialOutStoreDto dto=new MaterialOutStoreDto();
        Map<String,Object> materialBuyMap=(Map<String,Object>)params.get("formObj");
        dto.setAction(StringUtils.replaceNull(params.get("action")));
        MaterialOutStore materialOutStore=new MaterialOutStore();
        if("create".equals(dto.getAction())){
            materialOutStore.setId(StringUtils.getUUID());
            materialOutStore.setBizState("10");
        }else{
            materialOutStore.setId(StringUtils.replaceNull(materialBuyMap.get("id")));
        }
        materialOutStore.setBizNo(StringUtils.replaceNull(materialBuyMap.get("bizNo")));
        materialOutStore.setApplyUserId(StringUtils.replaceNull(materialBuyMap.get("applyUserId")));
        materialOutStore.setApplyDepId(StringUtils.replaceNull(materialBuyMap.get("applyDepId")));
        materialOutStore.setApplyTime(DateUtils.parse(StringUtils.replaceNull(materialBuyMap.get("applyTime"))));
        if(NumberUtils.isNumeric(StringUtils.replaceNull(materialBuyMap.get("totalAmt")))){
            materialOutStore.setTotalAmt(Double.parseDouble(StringUtils.replaceNull(materialBuyMap.get("totalAmt"))));
        }else{
            materialOutStore.setTotalAmt(0.00);
        }
        dto.setMaterialOutStore(materialOutStore);

        List<Map<String,Object>> materials=(ArrayList<Map<String,Object>>)params.get("materials");

        if(!CollectionUtils.isEmpty(materials)){
            List<MaterialOutStoreList> materialList=new ArrayList<>();
            for(Map<String,Object> materialMap:materials){
                MaterialOutStoreList material=new MaterialOutStoreList();
                material.setId(StringUtils.getUUID());
                material.setBizId(materialOutStore.getId());
                material.setBizType(ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE);
                material.setMaterialId(StringUtils.replaceNull(materialMap.get("materialId")));
                material.setMaterialName(StringUtils.replaceNull(materialMap.get("materialName")));
                material.setAmount(Double.parseDouble(StringUtils.replaceNull(materialMap.get("amount"))));
                materialList.add(material);
            }
            dto.setMaterialList(materialList);
        }
        return dto;
    }
}