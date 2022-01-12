package com.mall.auth.application.external.redis;

import com.mall.common.response.ResponseData;
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
    public ResponseData get(@PathVariable("key") String key){
        return ResponseData.error();
    }
    @PostMapping("/set")
    public ResponseData set(@RequestParam String key, @RequestParam String value,@RequestParam long expire){
        return ResponseData.error();
    }
    @PostMapping("/delete")
    public ResponseData delete(@RequestParam String key){
        return ResponseData.error();
    }
}
