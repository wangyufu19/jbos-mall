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

    public static ProcessInst build(Map<String, Object> params) {
        String currentTime = DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDDHIMMSS);
        Map<String, Object> formMap = (Map<String, Object>) params.get("formObj");
        ProcessInst processInst = new ProcessInst();
        String id=StringUtils.getUUID();
        processInst.setId(id);
        processInst.setProcDefId(StringUtils.replaceNull(formMap.get("id")));
        if(StringUtils.isNUll(formMap.get("bizId"))){
            processInst.setBizId(id);
        }else{
            processInst.setBizId(StringUtils.replaceNull(formMap.get("bizId")));
        }
        processInst.setBizNo(StringUtils.replaceNull(formMap.get("bizNo")));
        processInst.setBizType(StringUtils.replaceNull(formMap.get("bizType")));
        processInst.setBusinessKey(StringUtils.replaceNull(formMap.get("businessKey")));
        processInst.setRouteUrl(StringUtils.replaceNull(formMap.get("routeUrl")));
        processInst.setStartTime(currentTime);
        processInst.setCreateUserId(StringUtils.replaceNull(formMap.get("userId")));
        processInst.setCreateTime(currentTime);
        return processInst;
    }
}
