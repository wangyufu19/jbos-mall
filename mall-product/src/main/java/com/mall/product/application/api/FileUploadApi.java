package com.mall.product.application.api;

import com.mall.common.response.ResponseData;
import com.mall.product.application.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * FileUploadApi
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@Api("文件上传接口")
@Slf4j
public class FileUploadApi {
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 上传一个文件
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/upload")
    //@CrossOrigin
    @ApiOperation("上传一个文件")
    public ResponseData upload(MultipartFile file,@RequestParam Map<String, Object> params){
        ResponseData res= ResponseData.ok();
        try{
            //判断合法的文件类型
            if(fileUploadService.includeExtensions(file)){
                fileUploadService.upload(file);
            }else{
                res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
    /**
     * 上传多个文件
     * @param files
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/multiUpload")
    //@CrossOrigin
    @ApiOperation("上传多个文件")
    public ResponseData multiUpload(MultipartFile[] files){
        ResponseData res= ResponseData.ok();
        try{
            fileUploadService.upload(files);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
