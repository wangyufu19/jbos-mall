package com.mall.data.application.api;

import com.mall.common.response.ResponseResult;
import com.mall.data.application.entity.User;
import com.mall.data.application.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseResult get(@PathVariable("key") String key) {
        ResponseResult res= ResponseResult.ok();
        try{
            Object value=redisService.get(key);
            res.setData(value);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/set")
    @ApiOperation("设置值")
    public ResponseResult set(@RequestParam String key, @RequestParam String value, @RequestParam long expire) {
        ResponseResult res= ResponseResult.ok();
        try{
            User user=new User();
            user.setUid(1);
            user.setUserName("管理员");
            user.setSex(1);
            user.setAge(30);
            redisService.set(key,user,expire);
            res.setData(value);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/incr")
    @ApiOperation("将 key 中储存的数字值增一")
    public ResponseResult incr(@RequestParam String key) {
        ResponseResult res= ResponseResult.ok();
        try{
            long value=redisService.incr(key);
            res.setData(value);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    @ResponseBody
    @PostMapping("/incrby")
    @ApiOperation(" 将 key 中储存的数字加上指定的增量值")
    public ResponseResult incrby(@RequestParam String key, @RequestParam long incr) {
        ResponseResult res= ResponseResult.ok();
        try{
            long value=redisService.incrby(key,incr);
            res.setData(value);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/delete")
    @ApiOperation("删除值")
    public ResponseResult delete(@RequestParam String key) {
        ResponseResult res= ResponseResult.ok();
        redisService.delete(key);
        try{
            redisService.delete(key);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/hmset")
    @ApiOperation("同时将多个 field-value (域-值)对设置到哈希表 key 中")
    public ResponseResult hmset(@RequestParam String key, @RequestParam String field, @RequestParam String value) {
        ResponseResult res= ResponseResult.ok();
        try{
            Map map=new HashMap<>();
            map.put(field,value);
            redisService.hmset(key,map);
            res.setData(map);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/hmget")
    @ApiOperation("获取所有给定字段的值")
    public ResponseResult hmget() {
        ResponseResult res= ResponseResult.ok();
        try{
            Map map=new HashMap<>();   ;
            map=redisService.hget("user");
            res.setData(map);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/lpush")
    @ApiOperation("将一个或多个值插入到列表头部")
    public ResponseResult lpush(@RequestParam String key, @RequestParam String field, @RequestParam String value) {
        ResponseResult res= ResponseResult.ok();
        try{
            Map map=new HashMap<>();
            map.put(field,value);
            redisService.lpush(key,map);
            res.setData(map);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/lpop")
    @ApiOperation("移出并获取列表的第一个元素")
    public ResponseResult lpop(@RequestParam String key) {
        ResponseResult res= ResponseResult.ok();
        try{
            Object user=redisService.lpop(key);
            res.setData(user);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    @ResponseBody
    @PostMapping("/index")
    @ApiOperation("通过索引获取列表中的元素")
    public ResponseResult index(@RequestParam String key, @RequestParam int index) {
        ResponseResult res= ResponseResult.ok();
        try{
            Object user=redisService.index(key,index);
            res.setData(user);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}

