package com.github.zlhttpconnection.interfaces;

import org.apache.http.HttpEntity;

public interface IHttpListener {
    void onSuccess(HttpEntity httpEntity);

    void onFail(Exception e);
}
