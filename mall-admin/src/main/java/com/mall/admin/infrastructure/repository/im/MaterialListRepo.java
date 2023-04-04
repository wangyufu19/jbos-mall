package com.mall.admin.infrastructure.repository.im;

import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.infrastructure.repository.im.mapper.MaterialListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * MaterialListRepo
 * @author youfu.wang
 * @date 2020-06-24
 */
@Component
public class MaterialListRepo {
    @Autowired
    private MaterialListMapper materialListMapper;

    public List<MaterialList> getMaterialListList(Map<String, Object> parameterObject){
        return materialListMapper.selectByMap(parameterObject);
    }

    public void addMaterialList(MaterialList materialList){
        materialListMapper.insert(materialList);
    }
    public MaterialList getMaterialById(String id){
        return materialListMapper.selectById(id);
    }
    public void updateMaterial(MaterialList materialList){
        materialListMapper.updateById(materialList);
    }

    public void deleteMaterial(Map<String, Object> parameterObject){
        materialListMapper.deleteByMap(parameterObject);
    }
}
