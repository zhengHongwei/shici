package com.pamc.api.http;


import com.pamc.api.client.ClientConnectionConfig;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * author
 * created by zhenghongwei
 * 2018/9/18
 * description：
 **/
public class HttpClient {
    private final static Logger logger = LoggerFactory.getLogger(HttpClient.class);

    public HttpClient() {
    }


    private static PoolingHttpClientConnectionManager cm;
    private static void init(ClientConnectionConfig config) {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            if(config != null){
                cm.setMaxTotal(config.getMaxTotal());//整个连接池最大连接数
                cm.setDefaultMaxPerRoute(config.getDefaultMaxPerRoute());//每路由最大连接数，默认值是5
            }
        }
    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient(ClientConnectionConfig config) {
        init(config);
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    public static HttpResponse post(HttpRequest request) {
        HttpResponse response = new HttpResponse();
        CloseableHttpClient httpClient = getHttpClient(request.getConfig());
        HttpPost httpPost = new HttpPost(request.getUri().toString());
        for(Map.Entry<String,String> entry : request.getHeaders().entrySet()){
            httpPost.setHeader(entry.getKey(),entry.getValue());
        }
        httpPost.setEntity(new StringEntity(request.getBodyJson(),"utf-8"));
        try {
            CloseableHttpResponse apacheResponse  = httpClient.execute(httpPost);
            InputStream is = apacheResponse.getEntity().getContent();
            response.setStatus(apacheResponse.getStatusLine().getStatusCode());
            Header[] headArray = apacheResponse.getAllHeaders();
            if(headArray!=null && headArray.length>0){
                Map<String,List<String>> map = new HashMap<>();
                for(Header header:headArray){
                    List<String> listValue = new ArrayList<String>();
                    listValue.add(header.getValue());
                    map.put(header.getName(),listValue);
                    header.getName();
                    header.getValue();
                }
            }
            HttpEntity httpEntity = apacheResponse.getEntity();
            if(httpEntity != null ){
                response.setBodyStr(EntityUtils.toString(httpEntity,"utf-8"));
            }

            /*if (is != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                boolean var14 = false;

                int len;
                while((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                resp.setBody(outStream.toByteArray());
            }*/
            EntityUtils.consume(httpEntity);
            apacheResponse.close();
            return response;
        }catch (IOException e){
            e.printStackTrace();
            response.setStatus(1005);//自定义一个异常错误码
        }
        return response;
    }
}
