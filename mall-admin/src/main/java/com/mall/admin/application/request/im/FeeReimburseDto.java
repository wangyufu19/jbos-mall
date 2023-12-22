package com.mall.admin.application.request.im;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.mall.admin.domain.entity.im.FeeReimburseItem;
import com.mall.admin.domain.entity.im.Invoice;
import com.mall.admin.domain.entity.im.FeeReimburse;
import com.mall.admin.domain.entity.im.Payee;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    /**
     * 操作事件
     */
    private String action;
    /**
     * 费用报销实体
     */
    private FeeReimburse feeReimburse;
    /**
     * 收款账户实体
     */
    private Payee payee;
    /**
     * 费用明细
     */
    private List<FeeReimburseItem> feeReimburseItems;
    /**
     * 电子发票
     */
    private List<Invoice> invoiceItems;

    /**
     * 实例化FeeReimburseDto
     * @param params
     * @return FeeReimburseDto
     */
    public static FeeReimburseDto build(Map<String, Object> params) {
        FeeReimburseDto dto = new FeeReimburseDto();
        //报销基本信息
        Map<String, Object> feeReimburseMap = (Map<String, Object>) params.get("formObj");
        String action = String.valueOf(params.get("action"));
        dto.setAction(action);
        FeeReimburse feeReimburse = new FeeReimburse();
        if ("create".equals(action)) {
            feeReimburse.setId(IdUtil.randomUUID());
        } else {
            feeReimburse.setId(String.valueOf(feeReimburseMap.get("id")));
        }

        feeReimburse.setBizNo(String.valueOf(feeReimburseMap.get("bizNo")));
        feeReimburse.setApplyUserId(String.valueOf(feeReimburseMap.get("applyUserId")));
        feeReimburse.setApplyDepId(String.valueOf(feeReimburseMap.get("applyDepId")));
        feeReimburse.setApplyTime(DateUtil.parse(String.valueOf(feeReimburseMap.get("applyTime"))));
        feeReimburse.setFeeType(String.valueOf(feeReimburseMap.get("feeType")));
        feeReimburse.setFeeTmplt(String.valueOf(feeReimburseMap.get("feeTmplt")));
        feeReimburse.setTotalAmt(Double.parseDouble(String.valueOf(feeReimburseMap.get("totalAmt"))));
        feeReimburse.setBizDesc(String.valueOf(feeReimburseMap.get("bizDesc")));
        if ("create".equals(action) || "update".equals(action)) {
            feeReimburse.setBizState("10");
        }
        dto.setFeeReimburse(feeReimburse);
        //收款人信息
        Payee payee = new Payee();
        payee.setBizId(feeReimburse.getId());
        payee.setAcctName(String.valueOf(feeReimburseMap.get("acctName")));
        payee.setAcctNo(String.valueOf(feeReimburseMap.get("acctNo")));
        payee.setBankName(String.valueOf(feeReimburseMap.get("bankName")));
        payee.setTradeType(String.valueOf(feeReimburseMap.get("tradeType")));
        dto.setPayee(payee);
        //报销明细
        List<Map<String, Object>> feeReimburseItemList = (ArrayList<Map<String, Object>>) params.get("feeReimburseItem");
        if (!CollectionUtils.isEmpty(feeReimburseItemList)) {
            List<FeeReimburseItem> feeReimburseItems = new ArrayList<>();
            for (Map<String, Object> feeReimburseItemMap : feeReimburseItemList) {
                FeeReimburseItem feeReimburseItem = new FeeReimburseItem();
                feeReimburseItem.setId(IdUtil.randomUUID());
                feeReimburseItem.setBizId(feeReimburse.getId());
                feeReimburseItem.setFeeId(String.valueOf(feeReimburseItemMap.get("feeId")));
                feeReimburseItem.setFeeName(String.valueOf(feeReimburseItemMap.get("feeName")));
                feeReimburseItem.setAmount(Double.parseDouble(String.valueOf(feeReimburseItemMap.get("amount"))));
                feeReimburseItem.setFeeDesc(String.valueOf(feeReimburseItemMap.get("feeDesc")));
                feeReimburseItems.add(feeReimburseItem);
            }
            dto.setFeeReimburseItems(feeReimburseItems);
        }
        //发票信息
        List<Map<String, Object>> invoiceItemList = (ArrayList<Map<String, Object>>) params.get("invoiceItems");
        if (!CollectionUtils.isEmpty(invoiceItemList)) {
            List<Invoice> invoices = new ArrayList<>();
            for (Map<String, Object> invoiceItemMap : invoiceItemList) {
                Invoice invoice = new Invoice();
                invoice.setId(IdUtil.randomUUID());
                invoice.setBizId(feeReimburse.getId());
                invoice.setInvoiceCode(String.valueOf(invoiceItemMap.get("invoiceCode")));
                invoice.setInvoiceNo(String.valueOf(invoiceItemMap.get("invoiceNo")));
                invoice.setAmount(Double.parseDouble(String.valueOf(invoiceItemMap.get("amount"))));
                invoices.add(invoice);
            }
            dto.setInvoiceItems(invoices);
        }
        return dto;
    }
}
