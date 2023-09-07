package com.mall.admin.application.api.comm;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.IOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * FileUploadApi
 *
 * @author youfu.wang
 * @date 2023/4/13
 **/
@RestController
@RequestMapping("/io")
@Api(tags = "文件上传接口")
@Slf4j
public class FileUploadApi {
    /**
     * 上传一个文件
     *
     * @param file
     * @param params
     * @return
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

    @PostMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation("导出一个文件")
    public void download(HttpServletResponse response) {
        ClassPathResource classPathResource = new ClassPathResource("public/资产池.xlsx");
        try {
            IOUtils.write(response.getOutputStream(), classPathResource.getInputStream());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
