package com.github.zlhttpconnection.strategy;

import android.os.Handler;
import android.os.Looper;

import com.github.zlhttpconnection.interfaces.IDataListener;
import com.github.zlhttpconnection.interfaces.IHttpListener;
import com.github.zlhttpconnection.interfaces.IHttpService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class JsonGetHttpService<T> implements IHttpService {
    private String url;
    private IDataListener dataListener;
    private T requestData;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void excute() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL uu = new URL(url);
                    //打开连接
                    HttpURLConnection urlConnection = (HttpURLConnection) uu.openConnection();

                    if(200 == urlConnection.getResponseCode()){
                        //得到输入流
                        InputStream is =urlConnection.getInputStream();
                        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while(-1 != (len = is.read(buffer))){
                            baos.write(buffer,0,len);
                            baos.flush();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    dataListener.onSuccess(baos.toString("utf-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                    dataListener.onFail(e);
                                }
                            }
                        });
                    }
                }  catch (IOException e) {
                    e.printStackTrace();
                    dataListener.onFail(e);
                }
            }
        }).start();
    }

    @Override
    public void setDataListener(IDataListener listener) {
        this.dataListener = listener;
    }

    @Override
    public void setRequestData(Object requestData) {
        this.requestData = (T) requestData;
    }
}
