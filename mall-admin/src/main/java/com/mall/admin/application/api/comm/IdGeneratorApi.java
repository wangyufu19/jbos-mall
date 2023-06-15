package com.mall.admin.application.api.comm;

import com.mall.admin.application.service.comm.IdGeneratorService;
import com.mall.admin.domain.entity.comm.Id;
import com.mall.common.response.ResponseResult;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseResult get(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        String bizType= StringUtils.replaceNull(params.get("bizType"));
        try{
            Id id=idGeneratorService.getId(bizType);
            res.setData(id);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
    /**
     * 得到业务ID
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getBizNo")
    @ApiOperation("得到业务编号")
    public ResponseResult getBizNo(@RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        String bizType = StringUtils.replaceNull(params.get("bizType"));
        try {
            Id id = idGeneratorService.getId(bizType);
            if(id!=null){
                //业务编号=YYYYMMDD+ID版本+ID
                String bizNo = DateUtils.format(DateUtils.getCurrentDate(), DateUtils.YYYYMMDD) +"" + id.getVersion() +
                        "" + id.getCurrentId();
                Map<String, String> data=new HashMap();
                data.put("bizNo",bizNo);
                res.setData(data);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
