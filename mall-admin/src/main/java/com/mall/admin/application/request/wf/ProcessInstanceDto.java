package com.mall.admin.application.request.wf;

import com.mall.admin.domain.entity.wf.ProcessInst;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import lombok.Data;

import java.util.Map;

/**
 * ProcessInstanceDto
 *
 * @author youfu.wang
 * @date 2023/6/14
 **/
@Data
public class ProcessInstanceDto {

    public static ProcessInst build(Map<String, Object> variable) {
        String currentTime = DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS);
        ProcessInst processInst = new ProcessInst();
        String id=StringUtils.getUUID();
        processInst.setId(id);
        processInst.setProcDefId(StringUtils.replaceNull(variable.get("id")));
        if(StringUtils.isNUll(variable.get("bizId"))){
            processInst.setBizId(id);
        }else{
            processInst.setBizId(StringUtils.replaceNull(variable.get("bizId")));
        }
        processInst.setBizNo(StringUtils.replaceNull(variable.get("bizNo")));
        processInst.setBizType(StringUtils.replaceNull(variable.get("bizType")));
        processInst.setBusinessKey(StringUtils.replaceNull(variable.get("businessKey")));
        processInst.setRouteUrl(StringUtils.replaceNull(variable.get("routeUrl")));
        processInst.setStartTime(currentTime);
        processInst.setCreateUserId(StringUtils.replaceNull(variable.get("userId")));
        processInst.setCreateTime(currentTime);
        return processInst;
    }
}
