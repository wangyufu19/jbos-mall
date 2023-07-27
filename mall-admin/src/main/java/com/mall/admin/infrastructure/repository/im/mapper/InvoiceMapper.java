package com.mall.admin.infrastructure.repository.im.mapper;

import com.mall.admin.domain.entity.im.Invoice;

import java.util.List;


/**
 * InvoiceMapper
 *
 * @author youfu.wang
 * @date 2023/7/26
 **/
public interface InvoiceMapper {

    public List<Invoice> getReimburseInvoiceItem(String bizId);

    public void addReimburseInvoiceItem(List<Invoice> invoiceList);

    public void deleteReimburseInvoiceItem(String bizId);
}
