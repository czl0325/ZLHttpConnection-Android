package com.github.zlhttpconnection.model;

import com.github.zlhttpconnection.interfaces.IDataListener;
import com.github.zlhttpconnection.interfaces.IHttpListener;
import com.github.zlhttpconnection.interfaces.IHttpService;

public class RequestHolder<T> {
    private IHttpService httpService;
    private IDataListener dataListener;
    private T requestInfo;
    private String url;


    public IHttpService getHttpService() {
        return httpService;
    }

    public void setHttpService(IHttpService httpService) {
        this.httpService = httpService;
    }

    public IDataListener getDataListener() {
        return dataListener;
    }

    public void setDataListener(IDataListener dataListener) {
        this.dataListener = dataListener;
    }

    public T getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(T requestInfo) {
        this.requestInfo = requestInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
