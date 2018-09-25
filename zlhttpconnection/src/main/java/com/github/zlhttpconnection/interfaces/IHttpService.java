package com.github.zlhttpconnection.interfaces;

public interface IHttpService<T> {
    //设置网络请求地址
    void setUrl(String url);

    //处理网络
    void excute();

    //设置监听
    void setDataListener(IDataListener listener);

    //设置请求参数
    void setRequestData(T requestData);
}
