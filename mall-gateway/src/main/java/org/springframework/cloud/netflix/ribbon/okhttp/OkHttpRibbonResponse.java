//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cloud.netflix.ribbon.okhttp;

import com.google.common.reflect.TypeToken;
import com.netflix.client.ClientException;
import com.netflix.client.http.CaseInsensitiveMultiMap;
import com.netflix.client.http.HttpHeaders;
import com.netflix.client.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.util.Assert;

@Slf4j
public class OkHttpRibbonResponse implements HttpResponse {
    private final ResponseBody body;
    private URI uri;
    private Response response;

    public OkHttpRibbonResponse(Response response, URI uri) {
        Assert.notNull(response, "response can not be null");
        this.response = response;
        this.body = response.body();
        this.uri = uri;
    }

    public int getStatus() {
        return this.response.code();
    }

    public String getStatusLine() {
        return this.response.message();
    }

    public Object getPayload() throws ClientException {
        return !this.hasPayload() ? null : this.body.byteStream();
    }

    public boolean hasPayload() {
        return this.body != null;
    }

    public boolean isSuccess() {
        return this.response.isSuccessful();
    }

    public URI getRequestedURI() {
        return this.uri;
    }

    public Map<String, Collection<String>> getHeaders() {
        Map<String, Collection<String>> headers = new HashMap();
        Iterator var2 = this.response.headers().toMultimap().entrySet().iterator();

        while(var2.hasNext()) {
            Entry<String, List<String>> entry = (Entry)var2.next();
            String name = (String)entry.getKey();
            Iterator var5 = ((List)entry.getValue()).iterator();

            while(var5.hasNext()) {
                String value = (String)var5.next();
                if (headers.containsKey(name)) {
                    ((Collection)headers.get(name)).add(value);
                } else {
                    List<String> values = new ArrayList();
                    values.add(value);
                    headers.put(name, values);
                }
            }
        }

        return headers;
    }

    public HttpHeaders getHttpHeaders() {
        CaseInsensitiveMultiMap headers = new CaseInsensitiveMultiMap();
        Iterator var2 = this.response.headers().toMultimap().entrySet().iterator();

        while(var2.hasNext()) {
            Entry<String, List<String>> entry = (Entry)var2.next();
            Iterator var4 = ((List)entry.getValue()).iterator();

            while(var4.hasNext()) {
                String value = (String)var4.next();
                headers.addHeader((String)entry.getKey(), value);
            }
        }

        return headers;
    }

    public void close() {
        this.response.close();
    }

    public InputStream getInputStream() {
        return this.body == null ? null : this.body.byteStream();
    }

    public boolean hasEntity() {
        return this.hasPayload();
    }

    public <T> T getEntity(Class<T> type) throws Exception {
        return null;
    }

    public <T> T getEntity(Type type) throws Exception {
        return null;
    }

    public <T> T getEntity(TypeToken<T> type) throws Exception {
        return null;
    }
}
