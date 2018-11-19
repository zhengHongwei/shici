package com.pamc.api.client;


import java.net.Proxy.Type;

/**;
 * author
 * created by zhenghongwei943
 * 2018/9/17
 * description：
 **/
public class BaseClient {
    protected String baseUri;//访问地址前缀
    protected ClientConnectionConfig config;//链接配置

    public static final String CONTENT_TYPE = "Content-enums";


    protected BaseClient(String baseUri,ClientConnectionConfig config){
        this.baseUri = baseUri;
        this.config = config;
    }
    public void setConnectionTimeoutInMillis(int timeout) {
        if (config == null) {
            config = new ClientConnectionConfig();
        }

        config.setConnectionTimeoutMillis(timeout);
    }
    public void setSocketTimeoutInMillis(int timeout) {
        if (config == null) {
            config = new ClientConnectionConfig();
        }

        config.setSocketTimeoutMillis(timeout);
    }

    public void setHttpProxy(String host, int port) {
        if (config == null) {
            config = new ClientConnectionConfig();
        }

        config.setProxy(host, port, Type.HTTP);
    }

    public void setSocketProxy(String host, int port) {
        if (config == null) {
            config = new ClientConnectionConfig();
        }

        config.setProxy(host, port, Type.SOCKS);
    }
    public void setMaxTotal(int maxTotal) {
        if (config == null) {
            config = new ClientConnectionConfig();
        }
        config.setMaxTotal(maxTotal);
    }
    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        if (config == null) {
            config = new ClientConnectionConfig();
        }
        config.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }
}
