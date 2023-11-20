package com.mall.admin.common.log;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * RequestApiWrapper
 *
 * @author youfu.wang
 * @date 2023/11/17 15:52
 */
public class RequestLogWrapper extends HttpServletRequestWrapper {
    private byte[] body = new byte[0];
    private boolean isMultipart;

    public RequestLogWrapper(HttpServletRequest request) {
        super(request);
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        this.isMultipart = multipartResolver.isMultipart(request);
        if (!this.isMultipart) {
            ServletInputStream inputStream = null;
            try {
                inputStream = request.getInputStream();
                this.body = IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {


            public int read() throws IOException {
                return byteArrayInputStream.read();
            }


            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
