package com.mall.admin.application.service.im;

import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.infrastructure.repository.im.MaterialListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * MaterialListService
 * @author youfu.wang
 * @date 2020-06-24
 */
@Service
public class MaterialListService {
    @Autowired
    private MaterialListRepo materialListRepo;

    public List<MaterialList> getMaterialListList(Map<String, Object> parameterObject){
        return materialListRepo.getMaterialListList(parameterObject);
    }
    public void addMaterialList(MaterialList materialList){
        materialListRepo.addMaterialList(materialList);
    }
    public MaterialList getMaterialById(String id){
        return materialListRepo.getMaterialById(id);
    }
    public void updateMaterial(MaterialList materialList){
        materialListRepo.updateMaterial(materialList);
    }
    public void deleteMaterial(Map<String, Object> parameterObject){
        materialListRepo.deleteMaterial(parameterObject);
    }
}
