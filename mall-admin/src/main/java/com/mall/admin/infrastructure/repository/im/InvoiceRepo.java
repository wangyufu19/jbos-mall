package com.mall.admin.infrastructure.repository.im;

import com.mall.admin.domain.entity.im.Invoice;
import com.mall.admin.infrastructure.repository.im.mapper.InvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * InvoiceRepo
 *
 * @author youfu.wang
 * @date 2023/7/26
 **/
@Component
public class InvoiceRepo {
    @Autowired
    private InvoiceMapper invoiceMapper;

    public List<Invoice> getReimburseInvoiceItem(String bizId){
        return invoiceMapper.getReimburseInvoiceItem(bizId);
    }

    public void addReimburseInvoiceItem(List<Invoice> invoiceList){
        if(invoiceList!=null){
            invoiceMapper.addReimburseInvoiceItem(invoiceList);
        }
    }

    public void deleteReimburseInvoiceItem(String bizId){
        invoiceMapper.deleteReimburseInvoiceItem(bizId);
    }
}
