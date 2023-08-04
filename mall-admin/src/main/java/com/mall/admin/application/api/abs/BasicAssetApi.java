package com.mall.admin.application.api.abs;

import com.mall.admin.application.service.abs.BasicAssetService;
import com.mall.common.page.PageParam;
import com.mall.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * BasicAssetApi
 *
 * @author youfu.wang
 * @date 2023/8/4
 **/
@RestController
@RequestMapping("/basicAsset")
@Api("基础资产")
@Slf4j
public class BasicAssetApi {
    @Autowired
    private BasicAssetService basicAssetService;


    @ResponseBody
    @GetMapping(value = "/getBasicAssetList")
    @ApiOperation("基础资产入池")
    public ResponseResult getBasicAssetList(@RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            PageParam pageParam = PageParam.getPageParam(params);
            res=basicAssetService.getBasicAssetList(pageParam,params);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
            return res;
    }
    @ResponseBody
    @PostMapping(value = "/inPool")
    @ApiOperation("基础资产入池")
    public ResponseResult inPool(MultipartFile file, @RequestParam Map<String, Object> params){
        ResponseResult res= ResponseResult.ok();
        try{
            //判断合法的文件类型
            if(basicAssetService.includeExtensions(file)){
                basicAssetService.inPool(file);
            }else{
                res= ResponseResult.error(ResponseResult.CODE_FAILURE, "对不起，只能上传xls/xlsx文件");
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res= ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
