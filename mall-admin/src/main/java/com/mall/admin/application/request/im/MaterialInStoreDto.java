package com.mall.admin.application.request.im;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.domain.entity.im.MaterialInStore;
import com.mall.admin.domain.entity.im.MaterialList;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MaterialInStoreDto
 *
 * @author youfu.wang
 * @date 2023/5/31
 **/
@Data
public class MaterialInStoreDto {
    private String action;
    private MaterialInStore materialInStore;
    private List<MaterialList> materialList;

    public static MaterialInStoreDto build(Map<String, Object> params) {
        MaterialInStoreDto dto = new MaterialInStoreDto();
        Map<String, Object> materialBuyMap = (Map<String, Object>) params.get("formObj");
        dto.setAction(String.valueOf(params.get("action")));
        MaterialInStore materialInStore = new MaterialInStore();
        if ("create".equals(dto.getAction())) {
            materialInStore.setId(IdUtil.randomUUID());
            materialInStore.setBizState("10");
        } else {
            materialInStore.setId(String.valueOf(materialBuyMap.get("id")));
        }
        materialInStore.setBizNo(String.valueOf(materialBuyMap.get("bizNo")));
        materialInStore.setApplyUserId(String.valueOf(materialBuyMap.get("applyUserId")));
        materialInStore.setApplyDepId(String.valueOf(materialBuyMap.get("applyDepId")));
        materialInStore.setApplyTime(DateUtil.parse(String.valueOf(materialBuyMap.get("applyTime"))));
        materialInStore.setTotalAmt(Double.parseDouble(String.valueOf(materialBuyMap.get("totalAmt"))));
        dto.setMaterialInStore(materialInStore);

        List<Map<String, Object>> materials = (ArrayList<Map<String, Object>>) params.get("materials");

        if (!CollectionUtils.isEmpty(materials)) {
            List<MaterialList> materialList = new ArrayList<>();
            for (Map<String, Object> materialMap : materials) {
                MaterialList material = new MaterialList();
                material.setId(IdUtil.randomUUID());
                material.setBizId(materialInStore.getId());
                material.setBizType(ProcessDefConstants.PROC_DEF_MATERIAL_IN_STORE);
                material.setMaterialId(String.valueOf(materialMap.get("materialId")));
                material.setMaterialName(String.valueOf(materialMap.get("materialName")));
                material.setAmount(Double.parseDouble(String.valueOf(materialMap.get("amount"))));
                material.setPrice(Double.parseDouble(String.valueOf(materialMap.get("price"))));
                materialList.add(material);
            }
            dto.setMaterialList(materialList);
        }
        return dto;
    }
}
