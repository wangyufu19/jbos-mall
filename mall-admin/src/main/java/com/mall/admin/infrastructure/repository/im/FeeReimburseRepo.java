package com.mall.admin.infrastructure.repository.im;

import com.mall.admin.domain.entity.im.FeeReimburse;
import com.mall.admin.domain.entity.im.FeeReimburseItem;
import com.mall.admin.infrastructure.repository.im.mapper.FeeReimburseMapper;
import com.mall.common.page.PageParam;
import com.mall.common.page.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FeeReimburseRepo
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Component
public class FeeReimburseRepo {
    @Autowired
    private FeeReimburseMapper feeReimburseMapper;

    @Paging
    public List<FeeReimburse> getFeeReimburseList(PageParam pageParam, Map<String, Object> parameterObject) {
        return feeReimburseMapper.getFeeReimburseList(parameterObject);
    }

    public FeeReimburse getFeeReimburseById(String id) {
        return feeReimburseMapper.getFeeReimburseById(id);
    }

    public void addFeeReimburse(FeeReimburse feeReimburse) {
        feeReimburseMapper.addFeeReimburse(feeReimburse);
    }

    public void updateFeeReimburse(FeeReimburse feeReimburse) {
        feeReimburseMapper.updateFeeReimburse(feeReimburse);
    }

    public void deleteFeeReimburse(Map<String, Object> parameterObject) {
        feeReimburseMapper.deleteByMap(parameterObject);
    }

    public void addFeeReimburseItem(List<FeeReimburseItem> feeReimburseItemList) {
        if (feeReimburseItemList != null && feeReimburseItemList.size() > 0) {
            feeReimburseMapper.addFeeReimburseItem(feeReimburseItemList);
        }
    }

    public List<FeeReimburseItem> getFeeReimburseItem(String bizId) {
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("bizId", bizId);
        return feeReimburseMapper.getFeeReimburseItem(parameterObject);
    }

    public void deleteFeeReimburseItem(String bizId) {
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("bizId", bizId);
        feeReimburseMapper.deleteFeeReimburseItem(parameterObject);
    }

    public void updateBizState(Map<String, Object> parameterObject) {
        feeReimburseMapper.updateBizState(parameterObject);
    }
}
