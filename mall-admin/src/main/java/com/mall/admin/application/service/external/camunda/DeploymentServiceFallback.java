package com.mall.admin.application.service.external.camunda;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * ProcessDeploymentService
 *
 * @author youfu.wang
 * @date 2023/6/9
 **/
@Component
public class DeploymentServiceFallback implements DeploymentService {


    public ResponseResult deploy(MultipartFile file, @RequestParam Map<String, Object> params){
        return ResponseResult.error();
    }
}
