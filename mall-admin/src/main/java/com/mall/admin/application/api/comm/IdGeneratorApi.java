package com.mall.admin.application.api.comm;

import com.mall.admin.application.service.comm.IdGeneratorService;
import com.mall.admin.domain.entity.comm.Id;
import com.mall.common.response.ResponseData;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * IdGeneratorApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@RequestMapping("/id")
@Slf4j
@Api("ID生成器接口")
public class IdGeneratorApi {
    @Autowired
    private IdGeneratorService idGeneratorService;
    /**
     * 得到业务ID
     * @return
     */
    @ResponseBody
    @GetMapping("/get")
    @ApiOperation("得到业务ID")
    public ResponseData get(@RequestParam Map<String, Object> params){
        ResponseData res=ResponseData.ok();
        String bizType= StringUtils.replaceNull(params.get("bizType"));
        try{
            Id id=idGeneratorService.getId(bizType);
            res.setData(id);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
