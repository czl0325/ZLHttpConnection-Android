package com.github.zlhttpconnection;

import com.github.zlhttpconnection.interfaces.IDataListener;
import com.github.zlhttpconnection.interfaces.IHttpService;
import com.github.zlhttpconnection.model.RequestHolder;

public class HttpTask<T> implements Runnable {
    private IHttpService httpService;

    public HttpTask(RequestHolder<T> requestHolder) {
        httpService = requestHolder.getHttpService();
        httpService.setUrl(requestHolder.getUrl());
        T requestInfo = requestHolder.getRequestInfo();
        httpService.setRequestData(requestInfo);
        httpService.setDataListener(requestHolder.getDataListener());
    }

    @Override
    public void run() {
        httpService.excute();
    }
}
