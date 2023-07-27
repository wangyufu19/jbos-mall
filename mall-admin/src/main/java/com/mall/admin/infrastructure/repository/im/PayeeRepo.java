package com.mall.admin.infrastructure.repository.im;

import com.mall.admin.domain.entity.im.Payee;
import com.mall.admin.infrastructure.repository.im.mapper.PayeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * PayeeRepo
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Component
public class PayeeRepo {
    @Autowired
    private PayeeMapper payeeMapper;

    /**
     * 得到收款人信息
     * @param bizId
     * @return
     */
    public Payee getPayeeInfo(String bizId){
        return payeeMapper.getPayeeInfo(bizId);
    }

    /**
     * 新增收款人信息
     * @param payee
     */
    public void addPayeeInfo(Payee payee){
        if(payee!=null){
            payeeMapper.insert(payee);
        }
    }

    /**
     * 修改收款人信息
     * @param payee
     */
    public void updatePayeeInfo(Payee payee){
        if(payee!=null) {
            payeeMapper.updateById(payee);
        }
    }
}
