
package com.pamc.api.http;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * author
 * created by zhenghongwei
 * 2018/9/17
 * description：
 **/
public class HttpResponse {
    private Map<String, List<String>> header;
    private byte[] body;
    private String charset = "UTF-8";
    private int status = 0;
    private String bodyStr;

    public HttpResponse() {
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBodyStr(String bodyStr) {
        this.bodyStr = bodyStr;
    }

    public String getBodyStr() {
        if(bodyStr != null){
            return bodyStr;
        }
        if (this.body == null) {
            return "";
        } else {
            try {
                return new String(this.body, this.charset);
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
                return new String(this.body);
            }
        }
    }

    public Map<String, List<String>> getHeader() {
        return this.header;
    }

    public void setHeader(Map<String, List<String>> header) {
        this.header = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        Iterator var2 = header.entrySet().iterator();

        while(var2.hasNext()) {
            Entry<String, List<String>> entry = (Entry)var2.next();
            if (entry.getKey() != null) {
                this.header.put(entry.getKey(), entry.getValue());
            }
        }

    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
