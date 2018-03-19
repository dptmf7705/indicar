package com.indicar.indicar_community.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by yeseul on 2018-02-23.
 */

public class HTTPClient {
    private static final String BASE_URL = "http://13.125.28.35:9080";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private HTTPClient(){

    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteURL(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteURL(url), params, responseHandler);
    }

    private static String getAbsoluteURL(String url){
        return BASE_URL + url;
    }
}
