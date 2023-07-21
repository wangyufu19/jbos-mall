package com.mall.admin.application.service.im;

import com.mall.admin.domain.entity.im.MaterialList;
import com.mall.admin.domain.entity.im.MaterialStore;
import com.mall.admin.infrastructure.repository.im.MaterialStoreRepo;
import com.mall.common.page.PageParam;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MaterialStoreService
 *
 * @author youfu.wang
 * @date 2023/7/13
 **/
@Service
public class MaterialStoreService {
    @Autowired
    private MaterialStoreRepo materialStoreRepo;



    /**
     * 查询物品库存汇总数据
     * @param pageParam
     * @param parameterObject
     * @return
     */
    public List<MaterialStore> getMaterialStoreSumList(PageParam pageParam, Map<String, Object> parameterObject){
        return materialStoreRepo.getMaterialStoreSumList(pageParam,parameterObject);
    }
    /**
     * 查询物品库存剩余数量
     * @param materialId
     * @return
     */
    public double getMaterialStoreSurplusAmt(String materialId){
        Map<String, Object> parameterObject=new HashMap<>();
        parameterObject.put("materialIdS",parameterObject);
        List<MaterialStore> materialStoreList= materialStoreRepo.getMaterialStoreSumList(parameterObject);
        if(materialStoreList!=null&&materialStoreList.size()>0){
            return materialStoreList.get(0).getSurplusAmt();
        }
        return 0.00;
    }

    /**
     * 查询物品库存明细数据
     * @param parameterObject
     * @return
     */
    public List<MaterialStore> getMaterialStoreList(Map<String, Object> parameterObject){
        return materialStoreRepo.getMaterialStoreList(parameterObject);
    }

    /**
     * 根据物品Id查询物品库存明细(物品入库先进先出规则)
     * @param materialId
     * @return
     */
    public List<MaterialStore> getFIFOInfoByMaterialId(String materialId){
        Map<String, Object> parameterObject=new HashMap<>();
        parameterObject.put("materialId",materialId);
        return materialStoreRepo.getFIFOInfoByMaterialId(parameterObject);
    }

    /**
     * 新增物品库存数据
     * @param materialListList
     */
    @Transactional
    public void addMaterialStore(List<MaterialList> materialListList){
        if (materialListList==null){
            return;
        }
        List<MaterialStore> materialStoreList=new ArrayList<>();
        for(MaterialList materialList:materialListList){
            MaterialStore materialStore=new MaterialStore();
            materialStore.setId(StringUtils.getUUID());
            materialStore.setBatchNo(DateUtils.format(DateUtils.getCurrentDate(), "yyyyMMddHHmmss"));
            materialStore.setInBizId(materialList.getBizId());
            materialStore.setMaterialId(materialList.getMaterialId());
            materialStore.setMaterialName(materialList.getMaterialName());
            materialStore.setAmount(materialList.getAmount());
            materialStore.setPrice(materialList.getPrice());
            materialStore.setInTime(DateUtils.format(DateUtils.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
            materialStoreList.add(materialStore);
        }
        if(materialStoreList.size()>0){
            materialStoreRepo.addMaterialStore(materialStoreList);
        }
    }

    /**
     * 增加或减少物品库存数据
     * @param batchNo
     * @param materialId
     * @param diffAmount
     */
    public void updateMaterialStore(String batchNo,String materialId,double diffAmount){
        Map<String, Object> parameterObject = new HashMap();
        parameterObject.put("batchNo",batchNo);
        parameterObject.put("materialId",materialId);
        parameterObject.put("diffAmount",diffAmount);
        materialStoreRepo.updateMaterialStore(parameterObject);
    }
}
