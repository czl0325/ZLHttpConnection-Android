package com.github.zlhttpconnection;

import com.github.zlhttpconnection.interfaces.IDataListener;
import com.github.zlhttpconnection.interfaces.IHttpListener;
import com.github.zlhttpconnection.interfaces.IHttpService;
import com.github.zlhttpconnection.model.RequestHolder;
import com.github.zlhttpconnection.strategy.JsonGetHttpService;
import com.github.zlhttpconnection.strategy.JsonPostHttpService;

import java.util.concurrent.FutureTask;

public class ZLHttpConnection {

    /**
     * post请求网络
     * @param url           请求网址
     * @param requestInfo   请求参数
     * @param response      响应参数的类型
     * @param listener      监听
     * @param <T>
     * @param <M>
     */
    public static<T,M> void postRequest(String url,
                                        T requestInfo,
                                        Class<M> response,
                                        IDataListener listener){
        RequestHolder requestHolder = new RequestHolder();
        requestHolder.setUrl(url);
        IHttpService httpService = new JsonPostHttpService();
        httpService.setUrl(url);
    }

    public static<T,M> void getRequest(String url,
                                       T requestInfo,
                                       Class<M> response,
                                       IDataListener listener) {
        RequestHolder requestHolder = new RequestHolder();
        requestHolder.setUrl(url);
        requestHolder.setDataListener(listener);
        requestHolder.setRequestInfo(requestInfo);
        IHttpService httpService = new JsonGetHttpService();
        requestHolder.setHttpService(httpService);
        HttpTask httpTask = new HttpTask(requestHolder);
        ThreadPoolManager.getInstance().excute(new FutureTask(httpTask, null));
    }
}
