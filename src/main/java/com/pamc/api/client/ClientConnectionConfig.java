package com.pamc.api.client;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * author
 * created by zhenghongwei
 * 2018/9/17
 * description：客户端链接配置{支持htpp代理和socket代理}
 **/
public class ClientConnectionConfig {
    private int connectionTimeoutMillis;
    private int socketTimeoutMillis;
    private Proxy proxy;//代理设置{支持htpp代理和socket代理}
    private int maxTotal;//整个连接池最大连接数
    private int defaultMaxPerRoute;//每路由最大连接数

    public ClientConnectionConfig() {
        this.connectionTimeoutMillis = 0;
        this.socketTimeoutMillis = 0;
        this.proxy = Proxy.NO_PROXY;
        this.maxTotal = 50;
        this.defaultMaxPerRoute = 5;
    }

    public ClientConnectionConfig(int connectionTimeoutMillis, int socketTimeoutMillis, Proxy proxy,
                                  int maxTotal,int defaultMaxPerRoute) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
        this.socketTimeoutMillis = socketTimeoutMillis;
        this.proxy = proxy;
        this.maxTotal = maxTotal;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public int getConnectionTimeoutMillis() {
        return this.connectionTimeoutMillis;
    }

    public void setConnectionTimeoutMillis(int connectionTimeoutMillis) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
    }

    public int getSocketTimeoutMillis() {
        return this.socketTimeoutMillis;
    }

    public void setSocketTimeoutMillis(int socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setProxy(String host, int port, Proxy.Type type) {
        SocketAddress addr = new InetSocketAddress(host, port);
        this.proxy = new Proxy(type, addr);
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }
}
