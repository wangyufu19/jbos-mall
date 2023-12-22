package com.mall.admin.application.request.wf;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.mall.admin.domain.entity.wf.ProcessInst;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * ProcessInstanceDto
 *
 * @author youfu.wang
 * @date 2023/6/14
 **/
@Data
public class ProcessInstanceDto {
    /**
     * ProcessInst
     * @param variable
     * @return ProcessInst
     */
    public static ProcessInst build(Map<String, Object> variable) {
        String currentTime = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_FORMAT);
        ProcessInst processInst = new ProcessInst();
        String id = IdUtil.randomUUID();
        processInst.setId(id);
        processInst.setProcDefId(String.valueOf(variable.get("id")));
        if (StringUtils.isEmpty(variable.get("bizId"))) {
            processInst.setBizId(id);
        } else {
            processInst.setBizId(String.valueOf(variable.get("bizId")));
        }
        processInst.setBizNo(String.valueOf(variable.get("bizNo")));
        processInst.setBizType(String.valueOf(variable.get("bizType")));
        processInst.setBusinessKey(String.valueOf(variable.get("businessKey")));
        processInst.setRouteUrl(String.valueOf(variable.get("routeUrl")));
        processInst.setStartTime(currentTime);
        processInst.setCreateUserId(String.valueOf(variable.get("userId")));
        processInst.setCreateTime(currentTime);
        return processInst;
    }
}
