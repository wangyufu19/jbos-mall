package com.mall.admin.application.external.redis;

import com.mall.common.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * RedisService
 * @author youfu.wang
 * @date 2021-08-24
 */
@FeignClient(name = "mall-redis" , fallback = RedisServiceFallback.class)
public interface RedisService {
    @GetMapping("/get/{key}")
    public ResponseData get(@PathVariable("key") String key);
    @PostMapping("/set")
    public ResponseData set(@RequestParam String key, @RequestParam String value, @RequestParam long expire);
    @PostMapping("/delete")
    public ResponseData delete(@RequestParam String key);
}
