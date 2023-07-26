package com.mall.admin.infrastructure.repository.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.admin.domain.entity.im.FeeReimburse;
import com.mall.admin.domain.entity.im.FeeReimburseItem;
import java.util.List;
import java.util.Map;

/**
 * FeeReimburseMapper
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
public interface FeeReimburseMapper extends BaseMapper<FeeReimburse> {

    public List<FeeReimburse> getFeeReimburseList(Map<String,Object> parameterObject);

    public FeeReimburse getFeeReimburseById(String id);

    public void addFeeReimburse(FeeReimburse feeReimburse);

    public void updateFeeReimburse(FeeReimburse feeReimburse);

    public void addFeeReimburseItem(List<FeeReimburseItem> feeReimburseItemList);

    public List<FeeReimburseItem> getFeeReimburseItem(Map<String,Object> parameterObject);

    public void deleteFeeReimburseItem(Map<String,Object> parameterObject);

    public void updateBizState(Map<String,Object> parameterObject);
}
