package com.mall.admin.application.service.external.camunda;

import com.mall.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * ProcessDeploymentService
 *
 * @author youfu.wang
 * @date 2023/6/9
 **/
@Component
@FeignClient(name = "mall-workflow" , contextId = "deployment", fallback = DeploymentServiceFallback.class)
public interface DeploymentService {

    @ResponseBody
    @PostMapping(value = "/deployment/deploy",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult deploy(MultipartFile file, @RequestParam Map<String, Object> params);

    @ResponseBody
    @PostMapping(value = "/deployment/unDeploy")
    public ResponseResult unDeploy(@RequestBody Map<String, Object> params);

    @ResponseBody
    @GetMapping(value = "/deployment/getProcessDefinitionList")
    public ResponseResult getProcessDefinitionList(@RequestParam Map<String, Object> params);
}
