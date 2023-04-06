package com.mall.gateway.common.zuul;

import com.mall.common.response.ResponseResult;
import com.mall.common.utils.JacksonUtils;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
/**
 * MallGatewayFilter
 * @author youfu.wang
 * @date 2021-08-19
 */
@Component
public class MallGatewayFallback implements FallbackProvider {
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse(){

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers=new HttpHeaders();
                MediaType mt=new MediaType("application","json", Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }

            @Override
            public InputStream getBody() throws IOException {
                ResponseResult r= ResponseResult.error("【"+route+"】服务不可用");
                Map<String,Object> data=new HashMap<String,Object>();
                data.put("serviceId",route);
                r.setData(data);
                return new ByteArrayInputStream(JacksonUtils.toJson(r).getBytes(Charset.forName("UTF-8")));
            }

            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }
        };
    }
}
