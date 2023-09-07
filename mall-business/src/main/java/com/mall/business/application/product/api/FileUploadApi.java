package com.mall.business.application.product.api;

import com.mall.common.response.ResponseResult;
import com.mall.business.application.product.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * FileUploadApi
 *
 * @author youfu.wang
 * @date 2021-08-19
 */
@RestController
@RequestMapping("/io")
@Api("文件上传接口")
@Slf4j
public class FileUploadApi {
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 上传一个文件
     *
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/upload")
    //@CrossOrigin
    @ApiOperation("上传一个文件")
    public ResponseResult upload(MultipartFile file, @RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            //判断合法的文件类型
            if (fileUploadService.includeExtensions(file)) {
                fileUploadService.upload(file, params);
            } else {
                res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 上传多个文件
     *
     * @param files
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/multiUpload")
    //@CrossOrigin
    @ApiOperation("上传多个文件")
    public ResponseResult multiUpload(MultipartFile[] files, @RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            fileUploadService.upload(files, params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }

    /**
     * 删除一个文件
     *
     * @param params
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/delete")
    @ApiOperation("删除一个文件")
    public ResponseResult delete(@RequestBody Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            fileUploadService.delete(params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
