package com.github.zlhttpconnection_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.zlhttpconnection.ZLHttpConnection;
import com.github.zlhttpconnection.interfaces.IDataListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZLHttpConnection.getRequest(
                        "http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?" +
                                "scope=103&format=json&appid=379020&bk_key=" +
                                "爱因斯坦&bk_length=600",
                        null,
                        String.class,
                        new IDataListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Log.e("czl",o.toString());
                            }

                            @Override
                            public void onFail(Exception e) {

                            }
                        }
                );
            }
        });
    }
}
