//package com.mall.admin.application.service.external.camunda;
//
//import com.mall.common.response.ResponseResult;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.Map;
//
///**
// * IdentityMgrService
// * @author youfu.wang
// * @date 2021-08-24
// */
//@Component
//@FeignClient(name = "mall-workflow" , contextId = "user", fallback = IdentityMgrServiceFallback.class)
//public interface IdentityMgrService {
//
//    @PostMapping(value = "/user/create")
//    public ResponseResult create(@RequestBody Map<String, Object> params);
//}
