package com.mall.admin.infrastructure.repository.comm;

import com.mall.admin.domain.entity.comm.Id;
import com.mall.admin.infrastructure.repository.mapper.comm.IdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/**
 * IdRepo
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class IdRepo {
    @Autowired
    private IdMapper idMapper;

    /**
     * 得到当前号码
     * @param bizType
     * @return
     */
    public Id getId(String bizType){
        return idMapper.getId(bizType);
    }

    /**
     * 更新当前号码
     * @param bizType
     * @param version
     */
    public void updateId(String bizType,int version){
        Map<String,Object> parameterObject=new HashMap<String,Object> ();
        parameterObject.put("bizType",bizType);
        parameterObject.put("version",version);
        idMapper.updateId(parameterObject);
    }
}
