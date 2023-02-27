package com.mall.member.application.external.admin;

import com.mall.common.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

/**
 * UserMgrService
 * @author youfu.wang
 * @date 2021-08-24
 */
@FeignClient(name = "mall-admin" , fallback = UserMgrServiceFallback.class)
public interface UserMgrService {
    /**
     * 新增用户信息数据
     * @param params
     * @return
     */
    @PostMapping(value = "/user/add")
    public ResponseResult add(@RequestBody Map<String, Object> params);
}
