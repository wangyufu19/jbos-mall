package com.mall.admin.application.request.im;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.domain.entity.im.MaterialOutStore;
import com.mall.admin.domain.entity.im.MaterialOutStoreList;
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

    public static MaterialOutStoreDto build(Map<String, Object> params) {
        MaterialOutStoreDto dto = new MaterialOutStoreDto();
        Map<String, Object> materialBuyMap = (Map<String, Object>) params.get("formObj");
        dto.setAction(String.valueOf(params.get("action")));
        MaterialOutStore materialOutStore = new MaterialOutStore();
        if ("create".equals(dto.getAction())) {
            materialOutStore.setId(IdUtil.randomUUID());
            materialOutStore.setBizState("10");
        } else {
            materialOutStore.setId(String.valueOf(materialBuyMap.get("id")));
        }
        materialOutStore.setBizNo(String.valueOf(materialBuyMap.get("bizNo")));
        materialOutStore.setApplyUserId(String.valueOf(materialBuyMap.get("applyUserId")));
        materialOutStore.setApplyDepId(String.valueOf(materialBuyMap.get("applyDepId")));
        materialOutStore.setApplyTime(DateUtil.parse(String.valueOf(materialBuyMap.get("applyTime"))));
        if (NumberUtil.isNumber(String.valueOf(materialBuyMap.get("totalAmt")))) {
            materialOutStore.setTotalAmt(Double.parseDouble(String.valueOf(materialBuyMap.get("totalAmt"))));
        } else {
            materialOutStore.setTotalAmt(0.00);
        }
        dto.setMaterialOutStore(materialOutStore);

        List<Map<String, Object>> materials = (ArrayList<Map<String, Object>>) params.get("materials");

        if (!CollectionUtils.isEmpty(materials)) {
            List<MaterialOutStoreList> materialList = new ArrayList<>();
            for (Map<String, Object> materialMap : materials) {
                MaterialOutStoreList material = new MaterialOutStoreList();
                material.setId(IdUtil.randomUUID());
                material.setBizId(materialOutStore.getId());
                material.setBizType(ProcessDefConstants.PROC_DEF_MATERIAL_OUT_STORE);
                material.setMaterialId(String.valueOf(materialMap.get("materialId")));
                material.setMaterialName(String.valueOf(materialMap.get("materialName")));
                material.setAmount(Double.parseDouble(String.valueOf(materialMap.get("amount"))));
                materialList.add(material);
            }
            dto.setMaterialList(materialList);
        }
        return dto;
    }
}
