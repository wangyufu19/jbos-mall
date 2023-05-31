package com.mall.admin.application.request.im;

import com.mall.admin.application.service.ProcessDefConstants;
import com.mall.admin.domain.entity.im.MaterialBuy;
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
public class MaterialBuyDto {
    private MaterialBuy materialBuy;
    private List<MaterialList> materialList;

    public static MaterialBuyDto build(Map<String, Object> params){
        MaterialBuyDto dto=new MaterialBuyDto();
        Map<String,Object> materialBuyMap=(Map<String,Object>)params.get("formObj");
        String action=StringUtils.replaceNull(params.get("action"));

        MaterialBuy materialBuy=new MaterialBuy();
        if("create".equals(action)){
            materialBuy.setId(StringUtils.getUUID());
            materialBuy.setBizState("10");
        }else{
            materialBuy.setId(StringUtils.replaceNull(materialBuyMap.get("id")));
        }
        materialBuy.setBizNo(StringUtils.replaceNull(materialBuyMap.get("bizNo")));
        materialBuy.setApplyUserId(StringUtils.replaceNull(materialBuyMap.get("applyUserId")));
        materialBuy.setApplyDepId(StringUtils.replaceNull(materialBuyMap.get("applyDepId")));
        materialBuy.setApplyTime(DateUtils.parse(StringUtils.replaceNull(materialBuyMap.get("applyTime"))));
        materialBuy.setGmoTime(DateUtils.parse(StringUtils.replaceNull(materialBuyMap.get("gmoTime"))));
        materialBuy.setTotalAmt(Double.parseDouble(StringUtils.replaceNull(materialBuyMap.get("totalAmt"))));
        materialBuy.setPurpose(StringUtils.replaceNull(materialBuyMap.get("purpose")));
        dto.setMaterialBuy(materialBuy);

        List<Map<String,Object>> materials=(ArrayList<Map<String,Object>>)params.get("materials");

        if(!CollectionUtils.isEmpty(materials)){
            List<MaterialList> materialList=new ArrayList<>();
            for(Map<String,Object> materialMap:materials){
                MaterialList material=new MaterialList();
                material.setId(StringUtils.getUUID());
                material.setBizId(materialBuy.getId());
                material.setBizType(ProcessDefConstants.PROC_BIZTYPE_MATERIAL_BUY);
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
