package com.github.zlhttpconnection.strategy;

import android.os.Handler;
import android.os.Message;

import com.github.zlhttpconnection.interfaces.IDataListener;
import com.github.zlhttpconnection.interfaces.IHttpListener;
import com.github.zlhttpconnection.interfaces.IHttpService;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonPostHttpService<T> implements IHttpService {
    private IDataListener dataListener;
    private T requestData;
    private String url;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void excute() {
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)u.openConnection();
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            // 设置请求方式
            connection.setRequestMethod("POST");
            // 设置是否使用缓存
            connection.setUseCaches(false);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置使用标准编码格式编码参数的名-值对
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            // 设置超时时间
            connection.setConnectTimeout(3000);
            // 连接
            connection.connect();

            // 写入参数到请求中
            String params = getParam(requestData);
            OutputStream out = connection.getOutputStream();
            out.write(params.getBytes());
            out.flush();
            out.close();

            String reponse = "";
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    reponse += line + "\n";
                }
                reader.close();
                Message msg = new Message();
                msg.what = 0;
                msg.obj = reponse;
                mHandler.sendMessage(msg);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            dataListener.onFail(e);
        } catch (IOException e) {
            e.printStackTrace();
            dataListener.onFail(e);
        }

    }

    @Override
    public void setDataListener(IDataListener listener) {
        this.dataListener = listener;
    }

    @Override
    public void setRequestData(Object requestData) {
        this.requestData = (T) requestData;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    dataListener.onSuccess(msg.obj);
                    break;
                case 1:
                    dataListener.onFail((Exception) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private String getParam(T requestData) {
        Class clazz = requestData.getClass();
        Field []fields = clazz.getDeclaredFields();
        String params = null;
        for (int i=0; i<fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            try {
                if (f.get(clazz)!=null) {
                    params += (f.getName()+"="+f.get(clazz));
                    if (i!=fields.length-1) {
                        params += "&";
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return params;
    }
}
