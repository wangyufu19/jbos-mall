package com.mall.eureka.application;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * UserMgrApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@Api("HelloApi")
@Slf4j
public class HelloApi {


    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam Map<String, Object> params){
        return "HelloWorld!";
    }

}
