package com.mall.admin.infrastructure.repository.im;

import com.mall.admin.domain.entity.im.MaterialBuy;
import com.mall.admin.infrastructure.repository.im.mapper.MaterialBuyMapper;
import com.mall.common.page.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * MaterialBuyRepo
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class MaterialBuyRepo {
    @Autowired
    private MaterialBuyMapper materialBuyMapper;
    @Paging
    public List<MaterialBuy> getMaterialBuyList(Map<String, Object> parameterObject){
        return materialBuyMapper.getMaterialBuyList(parameterObject);
    }
    public MaterialBuy getMaterialBuyById(String id){
        return materialBuyMapper.getMaterialBuyById(id);
    }

    public void addMaterialBuy(MaterialBuy materialBuy){
        materialBuyMapper.insert(materialBuy);
    }

    public void updateMaterialBuy(MaterialBuy materialBuy){
        materialBuyMapper.updateById(materialBuy);
    }

    public void deleteMaterialBuy(Map<String, Object> parameterObject){
        materialBuyMapper.deleteByMap(parameterObject);
    }
    public void updateMaterialInstIdAndBizState(Map<String, Object> parameterObject){
        materialBuyMapper.updateMaterialInstIdAndBizState(parameterObject);
    }
    public void updateMaterialBizState(Map<String, Object> parameterObject){
        materialBuyMapper.updateMaterialBizState(parameterObject);
    }
}
