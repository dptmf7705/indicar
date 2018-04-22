package com.indicar.indicar_community.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.indicar.indicar_community.model.vo.BoardFileVO;
import com.indicar.indicar_community.utils.HttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BoardFileModel implements BaseModel<BoardFileVO> {

    private static final String TAG = BoardFileModel.class.getSimpleName();

    @Override
    public void getDataList(HashMap<String, String> map, final LoadDataListCallBack callBack) {
        final String URL = "/selectFileInfs";

        String atchFileId = map.get("atch_file_id");

        RequestParams params = new RequestParams();
        params.put("atch_file_id", atchFileId);

        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {
                Type listType = new TypeToken<List<BoardFileVO>>(){}.getType();

                List<BoardFileVO> fileList = new Gson().fromJson(new String(bytes), listType);

                callBack.onDataListLoaded(fileList);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @Override
    public void getData(final HashMap<String, String> map, final LoadDataCallBack callBack) {
        final String URL = "/selectFileInfs";

        String atchFileId = map.get("atch_file_id");

        RequestParams params = new RequestParams();
        params.put("atch_file_id", atchFileId);

        Log.d(TAG, "getData() called ... with atch_file_id: " + atchFileId);
        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {

                Type listType = new TypeToken<List<BoardFileVO>>(){}.getType();
                List<BoardFileVO> array = new Gson().fromJson(new String(bytes), listType);

                callBack.onDataLoaded(array.get(0));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @Override
    public void insertData(HashMap<String, Object> map, LoadDataCallBack callBack) {

    }

    @Override
    public void updateData(HashMap<String, Object> map, LoadDataCallBack callBack) {

    }

    @Override
    public void deleteData(HashMap<String, Object> map, LoadDataCallBack callBack) {

    }


}
