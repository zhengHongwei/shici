package com.pamc.api.client;

import com.pamc.api.http.HttpClient;
import com.pamc.api.http.HttpRequest;
import com.pamc.api.http.HttpResponse;
import com.pamc.api.enums.HttpMethodEnum;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zhenghongwei943
 * @date 2018/10/30
 * @description：
 **/
public class ApiClient extends  BaseClient{

    public ApiClient(String baseUri,ClientConnectionConfig config){
        super(baseUri,config);
    }
    /**
     * 执行爬虫
     * @param bodyEntity 请求体
     * @return
     */
    public String excute(Object bodyEntity, String api) {
        HttpRequest request = new HttpRequest();
        this.preOperation(request);
        request.setUri(baseUri+api);
        request.setBodyEntity(bodyEntity);
        this.postOperation(request);
        return this.requestServer(request);
    }

    protected void preOperation(HttpRequest request) {
        request.setHttpMethod(HttpMethodEnum.GET);
        request.addHeader(CONTENT_TYPE, "application/json; charset=utf-8");
    }

    protected void postOperation(HttpRequest request) {
    }

    protected String requestServer(HttpRequest request) {
        HttpResponse response = HttpClient.post(request);
        String resData = response.getBodyStr();
        Integer status = response.getStatus();
        try {
            if (status.equals(Integer.valueOf(200)) && !resData.equals("")) {
                JSONObject res = new JSONObject();
                res.put("rs",resData);
                return resData;
            } else {
                return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }
}
