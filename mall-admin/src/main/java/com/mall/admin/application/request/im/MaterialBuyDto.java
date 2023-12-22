package com.mall.admin.application.request.im;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.mall.admin.application.service.wf.ProcessDefConstants;
import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.admin.domain.entity.im.MaterialList;
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
public class MaterialBuyDto {
    private MaterialBuy materialBuy;
    private List<MaterialList> materialList;

    public static MaterialBuyDto build(Map<String, Object> params) {
        MaterialBuyDto dto = new MaterialBuyDto();
        Map<String, Object> materialBuyMap = (Map<String, Object>) params.get("formObj");
        String action = String.valueOf(params.get("action"));

        MaterialBuy materialBuy = new MaterialBuy();
        if ("create".equals(action)) {
            materialBuy.setId(IdUtil.randomUUID());
            materialBuy.setBizState("10");
        } else {
            materialBuy.setId(String.valueOf(materialBuyMap.get("id")));
        }
        materialBuy.setBizNo(String.valueOf(materialBuyMap.get("bizNo")));
        materialBuy.setApplyUserId(String.valueOf(materialBuyMap.get("applyUserId")));
        materialBuy.setApplyDepId(String.valueOf(materialBuyMap.get("applyDepId")));
        materialBuy.setApplyTime(DateUtil.parse(String.valueOf(materialBuyMap.get("applyTime"))));
        materialBuy.setGmoTime(DateUtil.parse(String.valueOf(materialBuyMap.get("gmoTime"))));
        materialBuy.setTotalAmt(Double.parseDouble(String.valueOf(materialBuyMap.get("totalAmt"))));
        materialBuy.setPurpose(String.valueOf(materialBuyMap.get("purpose")));
        dto.setMaterialBuy(materialBuy);

        List<Map<String, Object>> materials = (ArrayList<Map<String, Object>>) params.get("materials");

        if (!CollectionUtils.isEmpty(materials)) {
            List<MaterialList> materialList = new ArrayList<>();
            for (Map<String, Object> materialMap : materials) {
                MaterialList material = new MaterialList();
                material.setId(IdUtil.randomUUID());
                material.setBizId(materialBuy.getId());
                material.setBizType(ProcessDefConstants.PROC_DEF_MATERIAL_BUY);
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
