package com.mall.admin.application.api.im;

import com.mall.admin.application.service.comm.BizGeneratorService;
import com.mall.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * BizNoApi
 *
 * @author youfu.wang
 * @date 2023/7/25
 **/
@Slf4j
public class BizNoApi {

    @Autowired
    private BizGeneratorService bizGeneratorService;

    @ResponseBody
    @GetMapping("/getBizNo")
    @ApiOperation("查询物品领取业务编号")
    public ResponseResult getBizNo(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            res.setData(bizGeneratorService.getBizNo());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
