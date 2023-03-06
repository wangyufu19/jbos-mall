package com.mall.gateway.application.external.redis;

import com.mall.common.response.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * UserAuthServiceFallback
 * @author youfu.wang
 * @date 2021-08-24
 */
@Component
public class RedisServiceFallback implements RedisService{
    @GetMapping("/get/{key}")
    public ResponseResult get(@PathVariable("key") String key){
        return ResponseResult.error();
    }
    @PostMapping("/set")
    public ResponseResult set(@RequestParam String key, @RequestParam String value, @RequestParam long expire){
        return ResponseResult.error();
    }
    @PostMapping("/delete")
    public ResponseResult delete(@RequestParam String key){
        return ResponseResult.error();
    }
}
