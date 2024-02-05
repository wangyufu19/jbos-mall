package com.mall.admin.common.xss;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * XssHttpServletRequestWrapper
 *
 * @author youfu.wang
 * @date 2024/2/5 11:07
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * XssHttpServletRequestWrapper
     *
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (!StringUtils.isEmpty(values)) {
            int length = values.length;
            String[] escapesValues = new String[length];
            for (int i = 0; i < length; i++) {
                //防止Xss攻击的转义字符策略
                escapesValues[i] = HtmlUtil.cleanHtmlTag(values[i]).trim();
            }
            return escapesValues;
        }
        return values;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        if (super.getHeader(HttpHeaders.CONTENT_TYPE).contains(MediaType.APPLICATION_JSON)) {
            //application/json
            String body = StrUtil.str(IoUtil.readBytes(super.getInputStream(), false), StandardCharsets.UTF_8);
            if (StrUtil.isEmpty(body)) {
                return super.getInputStream();
            }
            //防止Xss攻击的转义字符策略
            body = HtmlUtil.cleanHtmlTag(body).trim();
            ByteArrayInputStream bis = IoUtil.toStream(body.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return true;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return bis.read();
                }
            };
        } else {
            //other
            return super.getInputStream();
        }
    }
}
