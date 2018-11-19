package com.pamc.api.http;

import com.alibaba.fastjson.JSON;
import com.pamc.api.client.ClientConnectionConfig;
import com.pamc.api.enums.BodyHandleEnum;
import com.pamc.api.enums.HttpMethodEnum;
import com.pamc.api.util.Util;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * author
 * created by zhenghongwei
 * 2018/9/17
 * description：
 **/
public class HttpRequest {
    private HashMap<String, String> headers;
    private HashMap<String, String> params;
    private HashMap<String, Object> body;//适合请求体结构复杂的对象 备注：该参数与参数bodyEntity同时出现时优先取bodyEntity
    private URI uri;
    private HttpMethodEnum httpMethod;
    private BodyHandleEnum bodyFormat;
    private String contentEncoding;
    private ClientConnectionConfig config;
    private Object bodyEntity;//适合请求相对简单，直接通过对象作为请求体

    public HttpRequest() {
        this.headers = new HashMap();
        this.params = new HashMap();
        this.body = new HashMap();
        this.httpMethod = HttpMethodEnum.POST;
        this.bodyFormat = BodyHandleEnum.RAW_JSON;
        this.contentEncoding = "UTF8";
        this.config = null;
    }

    public HttpRequest(HashMap<String, String> header, HashMap<String, String> bodyParams) {
        this.headers = header;
        this.params = bodyParams;
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public BodyHandleEnum getBodyFormat() {
        return this.bodyFormat;
    }

    public void setBodyFormat(BodyHandleEnum bodyFormat) {
        this.bodyFormat = bodyFormat;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
        if (key.equals("Content-Encoding")) {
            this.contentEncoding = value;
        }

    }

    public void addParam(String key, String value) {
        this.params.put(key, value);
    }

    public void addBody(String key, Object value) {
        this.body.put(key, value);
    }

    public void addBody(HashMap other) {
        if (other != null) {
            this.body.putAll(other);
        }

    }

    public HashMap<String, String> getParams() {
        return this.params;
    }

    /**
     * 该方法适合从以body作为请求体获取请求参数的String
     * @return
     */
    public String getBodyStr() {
        ArrayList<String> arr = new ArrayList();
        if (this.bodyFormat.equals(BodyHandleEnum.FORM_KV)) {
            Iterator var5 = this.body.entrySet().iterator();

            while(true) {
                while(var5.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry)var5.next();
                    if (entry.getValue() != null && !entry.getValue().equals("")) {
                        arr.add(String.format("%s=%s", Util.uriEncode((String)entry.getKey(), true), Util.uriEncode(entry.getValue().toString(), true)));
                    } else {
                        arr.add(Util.uriEncode((String)entry.getKey(), true));
                    }
                }

                return Util.mkString(arr.iterator(), '&');
            }
        } else if (!this.bodyFormat.equals(BodyHandleEnum.RAW_JSON)) {
            return this.bodyFormat.equals(BodyHandleEnum.RAW_JSON_ARRAY) ? (String)this.body.get("body") : "";
        } else {
            JSONObject json = new JSONObject();
            Iterator var3 = this.body.entrySet().iterator();
            try {
                while(var3.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry)var3.next();
                    json.put((String)entry.getKey(), entry.getValue());
                }
            }catch (JSONException e){

            }
            return json.toString();
        }
    }

    /**
     * 该方法适合以bodyEntity为参，获取请求体的json字符串
     * @return
     */
    public String getBodyJson(){
        String bodyJson = "";
        try {
            if(bodyEntity != null){
                bodyJson = new String(JSON.toJSONString(bodyEntity).getBytes("utf-8"),"utf-8");
            }else{
                bodyJson = new String(JSON.toJSONString(body).getBytes("utf-8"),"utf-8");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return bodyJson;
    }

    public String getParamStr() {
        StringBuffer buffer = new StringBuffer();
        Iterator var2 = this.params.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)var2.next();
            buffer.append(String.format("%s=%s&", entry.getKey(), entry.getValue()));
        }

        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }

        return buffer.toString();
    }

    public HashMap<String, Object> getBody() {
        return this.body;
    }

    public void setBody(HashMap<String, Object> body) {
        this.body = body;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public URI getUri() {
        return this.uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setUri(String url) {
        try {
            this.uri = new URI(url);
        } catch (URISyntaxException var3) {
            var3.printStackTrace();
        }

    }

    public HttpMethodEnum getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(HttpMethodEnum httpMethod) {
        this.httpMethod = httpMethod;
    }

    public ClientConnectionConfig getConfig() {
        return this.config;
    }

    public void setConfig(ClientConnectionConfig config) {
        this.config = config;
    }

    public Object getBodyEntity() {
        return bodyEntity;
    }

    public void setBodyEntity(Object bodyEntity) {
        this.bodyEntity = bodyEntity;
    }
}
