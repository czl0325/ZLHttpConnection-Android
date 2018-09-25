package com.github.zlhttpconnection.interfaces;

public interface IDataListener<M> {
    void onSuccess(M m);

    void onFail(Exception e);
}
