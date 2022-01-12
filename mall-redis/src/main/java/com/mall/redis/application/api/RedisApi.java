package com.mall.redis.application.api;

import com.mall.common.response.ResponseData;
import com.mall.redis.application.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * RedisApi
 * @author youfu.wang
 * @date 2021-10-10
 */
@RestController
@Api("Redis接口")
@Slf4j
public class RedisApi {
    @Autowired
    private RedisService redisService;

    @ResponseBody
    @GetMapping("/get/{key}")
    @ApiOperation("得到值")
    public ResponseData get(@PathVariable("key") String key) {
        ResponseData res=ResponseData.ok();
        try{
            Object value=redisService.get(key);
            res.setData(value);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/set")
    @ApiOperation("设置值")
    public ResponseData set(@RequestParam String key,@RequestParam String value,@RequestParam long expire) {
        ResponseData res=ResponseData.ok();
        try{
            redisService.set(key,value,expire);
            res.setData(value);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/delete")
    @ApiOperation("删除值")
    public ResponseData delete(@RequestParam String key) {
        ResponseData res=ResponseData.ok();
        redisService.delete(key);
        try{
            redisService.delete(key);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
