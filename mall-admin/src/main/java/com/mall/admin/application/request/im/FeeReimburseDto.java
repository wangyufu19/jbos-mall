package com.mall.admin.application.request.im;

import com.mall.admin.domain.entity.im.FeeReimburseItem;
import com.mall.admin.domain.entity.im.Invoice;
import com.mall.admin.domain.entity.im.FeeReimburse;
import com.mall.admin.domain.entity.im.Payee;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
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
        String action = StringUtils.replaceNull(params.get("action"));
        dto.setAction(action);
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
        feeReimburse.setFeeType(StringUtils.replaceNull(feeReimburseMap.get("feeType")));
        feeReimburse.setFeeTmplt(StringUtils.replaceNull(feeReimburseMap.get("feeTmplt")));
        feeReimburse.setTotalAmt(Double.parseDouble(StringUtils.replaceNull(feeReimburseMap.get("totalAmt"))));
        feeReimburse.setBizDesc(StringUtils.replaceNull(feeReimburseMap.get("bizDesc")));
        if ("create".equals(action) || "update".equals(action)) {
            feeReimburse.setBizState("10");
        }
        dto.setFeeReimburse(feeReimburse);
        //收款人信息
        Payee payee = new Payee();
        payee.setBizId(feeReimburse.getId());
        payee.setAcctName(StringUtils.replaceNull(feeReimburseMap.get("acctName")));
        payee.setAcctNo(StringUtils.replaceNull(feeReimburseMap.get("acctNo")));
        payee.setBankName(StringUtils.replaceNull(feeReimburseMap.get("bankName")));
        payee.setTradeType(StringUtils.replaceNull(feeReimburseMap.get("tradeType")));
        dto.setPayee(payee);
        //报销明细
        List<Map<String, Object>> feeReimburseItemList = (ArrayList<Map<String, Object>>) params.get("feeReimburseItem");
        if (!CollectionUtils.isEmpty(feeReimburseItemList)) {
            List<FeeReimburseItem> feeReimburseItems = new ArrayList<>();
            for (Map<String, Object> feeReimburseItemMap : feeReimburseItemList) {
                FeeReimburseItem feeReimburseItem = new FeeReimburseItem();
                feeReimburseItem.setId(StringUtils.getUUID());
                feeReimburseItem.setBizId(feeReimburse.getId());
                feeReimburseItem.setFeeId(StringUtils.replaceNull(feeReimburseItemMap.get("feeId")));
                feeReimburseItem.setFeeName(StringUtils.replaceNull(feeReimburseItemMap.get("feeName")));
                feeReimburseItem.setAmount(Double.parseDouble(StringUtils.replaceNull(feeReimburseItemMap.get("amount"))));
                feeReimburseItem.setFeeDesc(StringUtils.replaceNull(feeReimburseItemMap.get("feeDesc")));
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
                invoice.setId(StringUtils.getUUID());
                invoice.setBizId(feeReimburse.getId());
                invoice.setInvoiceCode(StringUtils.replaceNull(invoiceItemMap.get("invoiceCode")));
                invoice.setInvoiceNo(StringUtils.replaceNull(invoiceItemMap.get("invoiceNo")));
                invoice.setAmount(Double.parseDouble(StringUtils.replaceNull(invoiceItemMap.get("amount"))));
                invoices.add(invoice);
            }
            dto.setInvoiceItems(invoices);
        }
        return dto;
    }
}
