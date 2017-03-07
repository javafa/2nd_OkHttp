package com.veryworks.android.remoteokhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
    OkHttp 라이브러리는 HttpUrlConnection 을 쉽게 사용할 수 있게 해준다
    하지만 Thread 처리가 되어 있지 않기 때문에
    Thread 를 사용하는 다른 Api 와 함께 사용해야만 한다
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OkHttp 는 네트웍에 접근하기 위해서 새로운 Thread를 생성해서 처리해야한다
        // 1. Thread 생성방법 - AsyncTask
        new AsyncTask<Void,Void,Void>() {
            @Override
            public Void doInBackground(Void... params){
                try {
                    String result = getData("http://daum.net");
                    Log.i("OkHttp", "result=" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        // 2. new Thread
        new Thread(){
            @Override
            public void run(){
                try {
                    String result = getData("http://google.com");
                    Log.i("OkHttp", "result=" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private String getData(String url) throws IOException {
        // 1. OKHttp 인스턴스 생성
        OkHttpClient client = client = new OkHttpClient();

        // 2. request 개체 생성
        Request request = new Request.Builder()
                .url(url)
                .build();

        // 3. client 인스턴스에 request 를 담아 보낸다
        Response response = client.newCall(request).execute(); // -> 서버측으로 요청

        return response.body().string();
    }
}
