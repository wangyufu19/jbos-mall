package com.mall.admin.application.request.im;

import com.mall.admin.domain.entity.im.*;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * FeeReimburseDto
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Data
public class FeeReimburseDto {
    private String action;
    private FeeReimburse feeReimburse;
    private Payee payee;
    private List<FeeReimburseItem> feeReimburseItem;
    private List<Invoice> invoiceItem;

    public static FeeReimburseDto build(Map<String, Object> params) {
        FeeReimburseDto dto = new FeeReimburseDto();
        Map<String, Object> feeReimburseMap = (Map<String, Object>) params.get("formObj");
        String action = StringUtils.replaceNull(params.get("action"));

        FeeReimburse feeReimburse = new FeeReimburse();
        if ("create".equals(action)) {
            feeReimburse.setId(StringUtils.getUUID());
        } else {
            feeReimburse.setId(StringUtils.replaceNull(feeReimburseMap.get("id")));
        }
        feeReimburse.setBizNo(StringUtils.replaceNull(feeReimburseMap.get("bizNo")));
        feeReimburse.setApplyUserId(StringUtils.replaceNull(feeReimburseMap.get("applyUserId")));
        feeReimburse.setApplyDepId(StringUtils.replaceNull(feeReimburseMap.get("applyDepId")));
        feeReimburse.setApplyTime(DateUtils.parse(StringUtils.replaceNull(feeReimburseMap.get("applyTime"))));
        feeReimburse.setTotalAmt(Double.parseDouble(StringUtils.replaceNull(feeReimburseMap.get("totalAmt"))));
        feeReimburse.setBizDesc(StringUtils.replaceNull(feeReimburseMap.get("bizDesc")));
        if ("create".equals(action)||"update".equals(action)) {
            feeReimburse.setBizState("10");
        }
        dto.setFeeReimburse(feeReimburse);
        return dto;
    }
}
