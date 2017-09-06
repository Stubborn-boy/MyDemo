package com.example.mydemo.http;

import android.content.Context;

import com.example.baselibrary.http.HttpHelper;
import com.example.baselibrary.http.IDataHelper;

/**
 * Created by 71541 on 2017/9/6.
 */

public class RetrofitClient {

    private static RetrofitClient ourInstance;
    private ApiService apiService;

    public static RetrofitClient getInstance(Context context) {
        if (ourInstance == null) {
            synchronized (RetrofitClient.class) {
                if (ourInstance == null) {
                    ourInstance = new RetrofitClient(context.getApplicationContext());
                }
            }
        }
        return ourInstance;
    }

    private RetrofitClient(Context context) {
        HttpHelper httpHelper=  new HttpHelper(context);
        IDataHelper.NetConfig netConfig= new IDataHelper.NetConfig();
        httpHelper.initConfig(netConfig);
        this.apiService = httpHelper.createApi(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
