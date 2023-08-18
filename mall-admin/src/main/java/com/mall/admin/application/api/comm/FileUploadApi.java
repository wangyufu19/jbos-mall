package com.mall.admin.application.api.comm;

import com.mall.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * FileUploadApi
 *
 * @author youfu.wang
 * @date 2023/4/13
 **/
@RestController
@RequestMapping("/io")
@Api("文件上传接口")
@Slf4j
public class FileUploadApi {
    /**
     * 上传一个文件
     * @param params
     * @param file
     * @return ResponseResult
     */
    @ResponseBody
    @PostMapping(value = "/upload")
    @ApiOperation("上传一个文件")
    public ResponseResult upload(MultipartFile file, @RequestParam Map<String, Object> params) {
        ResponseResult res = ResponseResult.ok();
        try {
            log.info("upload");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = ResponseResult.error(ResponseResult.CODE_FAILURE, ResponseResult.MSG_FAILURE);
        }
        return res;
    }
}
