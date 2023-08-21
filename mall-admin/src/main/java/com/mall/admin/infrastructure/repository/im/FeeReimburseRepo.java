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
    /**
     * 得到费用报销业务数据列表
     * @param pageParam
     * @param parameterObject
     * @return list
     */
    @Paging
    public List<FeeReimburse> getFeeReimburseList(PageParam pageParam, Map<String, Object> parameterObject) {
        return feeReimburseMapper.getFeeReimburseList(parameterObject);
    }
    /**
     * 根据业务Id得到费用报销业务数据
     *
     * @param id
     * @return FeeReimburse
     */
    public FeeReimburse getFeeReimburseById(String id) {
        return feeReimburseMapper.getFeeReimburseById(id);
    }
    /**
     * 新增费用报销业务数据
     * @param feeReimburse
     */
    public void addFeeReimburse(FeeReimburse feeReimburse) {
        feeReimburseMapper.addFeeReimburse(feeReimburse);
    }
    /**
     * 更新费用报销业务数据
     * @param feeReimburse
     */
    public void updateFeeReimburse(FeeReimburse feeReimburse) {
        feeReimburseMapper.updateFeeReimburse(feeReimburse);
    }

    /**
     * 删除费用报销业务数据
     * @param parameterObject
     */
    public void deleteFeeReimburse(Map<String, Object> parameterObject) {
        feeReimburseMapper.deleteByMap(parameterObject);
    }
    /**
     * 新增费用报销明细
     * @param feeReimburseItemList
     */
    public void addFeeReimburseItem(List<FeeReimburseItem> feeReimburseItemList) {
        if (feeReimburseItemList != null && feeReimburseItemList.size() > 0) {
            feeReimburseMapper.addFeeReimburseItem(feeReimburseItemList);
        }
    }
    /**
     * 得到费用报销明细
     * @param bizId
     * @return list
     */
    public List<FeeReimburseItem> getFeeReimburseItem(String bizId) {
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("bizId", bizId);
        return feeReimburseMapper.getFeeReimburseItem(parameterObject);
    }
    /**
     * 删除费用报销明细
     * @param bizId
     */
    public void deleteFeeReimburseItem(String bizId) {
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("bizId", bizId);
        feeReimburseMapper.deleteFeeReimburseItem(parameterObject);
    }
    /**
     * 更新费用报销业务状态
     * @param parameterObject
     */
    public void updateBizState(Map<String, Object> parameterObject) {
        feeReimburseMapper.updateBizState(parameterObject);
    }
}
