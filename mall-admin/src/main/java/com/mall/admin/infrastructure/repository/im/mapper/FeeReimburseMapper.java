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
    /**
     * 得到费用报销业务数据列表
     *
     * @param parameterObject
     * @return list
     */
    List<FeeReimburse> getFeeReimburseList(Map<String, Object> parameterObject);

    /**
     * 根据业务Id得到费用报销业务数据
     *
     * @param id
     * @return FeeReimburse
     */
    FeeReimburse getFeeReimburseById(String id);

    /**
     * 新增费用报销业务数据
     * @param feeReimburse
     */
    void addFeeReimburse(FeeReimburse feeReimburse);

    /**
     * 更新费用报销业务数据
     * @param feeReimburse
     */
    void updateFeeReimburse(FeeReimburse feeReimburse);

    /**
     * 新增费用报销明细
     * @param feeReimburseItemList
     */
    void addFeeReimburseItem(List<FeeReimburseItem> feeReimburseItemList);

    /**
     * 得到费用报销明细
     * @param parameterObject
     * @return list
     */
    List<FeeReimburseItem> getFeeReimburseItem(Map<String, Object> parameterObject);

    /**
     * 删除费用报销明细
     * @param parameterObject
     */
    void deleteFeeReimburseItem(Map<String, Object> parameterObject);

    /**
     * 更新费用报销业务状态
     * @param parameterObject
     */
    void updateBizState(Map<String, Object> parameterObject);
}
