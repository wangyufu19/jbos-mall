package com.mall.auth.application.api;

import com.google.code.kaptcha.Producer;
import com.mall.auth.application.service.CaptchaService;
import com.mall.common.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * CaptchaApi
 * @author youfu.wang
 * @date 2019-01-31
 */
@RestController
@Slf4j
@Api("登录验证码接口")
public class CaptchaApi {
    @Autowired
    private Producer producer;
    @Autowired
    private CaptchaService captchaService;


    /**
     * 登录验证码
     * @return
     */
    @ResponseBody
    @RequestMapping("/captcha")
    @ApiOperation("登录验证码")
    public ResponseData captcha() throws IOException {
        ResponseData res=ResponseData.ok();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        String text=producer.createText();
        BufferedImage image=producer.createImage(text);
        ImageIO.write(image,"jpg",out);
        String captchaToken= UUID.randomUUID().toString();
        String captchaSrc= Base64.encodeBase64String(out.toByteArray());
        Map<String,Object> captcha=new HashMap();
        captcha.put("captchaToken",captchaToken);
        captcha.put("captchaSrc",captchaSrc);
        res.setData(captcha);
        try{
            captchaService.addCaptcha(text,captchaToken);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            res=ResponseData.error(ResponseData.RETCODE_FAILURE,ResponseData.RETMSG_FAILURE);
        }
        return res;
    }
}
