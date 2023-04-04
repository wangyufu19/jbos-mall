package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.im.MaterialBuy;

import java.util.List;
import java.util.Map;

/**
 * MaterialBuyMapper
 * @author youfu.wang
 * @date 2020-06-24
 */
public interface MaterialBuyMapper extends BaseMapper<MaterialBuy> {

    public List<MaterialBuy> getMaterialBuyList(Map<String, Object> parameterObject);

    public MaterialBuy getMaterialBuyById(String id);

    public void addMaterialBuy(Map<String, Object> parameterObject);

    public void updateMaterialBuy(Map<String, Object> parameterObject);

    public void deleteMaterialBuy(Map<String, Object> parameterObject);

    public void updateMaterialInstId(Map<String, Object> parameterObject);

    public void updateMaterialBizState(Map<String, Object> parameterObject);

}
