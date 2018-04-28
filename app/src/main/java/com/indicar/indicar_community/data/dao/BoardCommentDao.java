package com.indicar.indicar_community.data.dao;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.indicar.indicar_community.data.vo.BoardCommentVO;
import com.indicar.indicar_community.utils.HttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BoardCommentDao implements BaseDao<BoardCommentVO> {

    @Override
    public void getDataList(HashMap<String, String> map, final BaseDao.LoadDataListCallBack callBack) {
        final String URL = "/selectCommentList";

        String bbsId = map.get("bbs_id");
        String nttId = map.get("ntt_id");

        RequestParams params = new RequestParams();
        params.put("bbs_id", bbsId);
        params.put("ntt_id", nttId);

        Log.d("BoardCommentDao", "getDataList() called... with bbs_id: " + bbsId + ", ntt_id: " + nttId);
        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JsonElement result = new JsonParser().parse(new String(responseBody));

                // 댓글 존재
                if(result.isJsonArray()){
                    Type listType = new TypeToken<List<BoardCommentVO>>(){}.getType();

                    List<BoardCommentVO> commentList = new Gson().fromJson(result, listType);

                    callBack.onDataListLoaded(commentList);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void getData(HashMap<String, String> map, LoadDataCallBack callBack) {

    }

    @Override
    public void updateData(HashMap<String, Object> map, final LoadDataCallBack callBack) {
        final String URL = "/updateComment";

        String nttId = map.get("ntt_id").toString();
        String answerNo = map.get("answer_no").toString();
        String answer = map.get("answer").toString();

        RequestParams params = new RequestParams();
        params.put("ntt_id", nttId);
        params.put("answer_no", answerNo);
        params.put("answer", answer);

        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callBack.onDataLoaded("success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    @Override
    public void deleteData(HashMap<String, Object> map, final LoadDataCallBack callBack) {
        final String URL = "/deleteComment";


        String nttId = map.get("ntt_id").toString();
        String answerNo = map.get("answer_no").toString();

        RequestParams params = new RequestParams();
        params.put("ntt_id", nttId);
        params.put("answer_no", answerNo);

        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callBack.onDataLoaded("success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    @Override
    public void insertData(HashMap<String, Object> map, final LoadDataCallBack callBack) {
        final String URL = "/insertComment";

        String bbsId = map.get("bbs_id").toString();
        String nttId = map.get("ntt_id").toString();
        String answer = map.get("answer").toString();
        String writerName = map.get("writer_nm").toString();
        String writerId = map.get("writer_id").toString();

        RequestParams params = new RequestParams();
        params.put("bbs_id", bbsId);
        params.put("ntt_id", nttId);
        params.put("answer", answer);
        params.put("writer_nm", writerName);
        params.put("writer_id", writerId);

        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callBack.onDataLoaded("success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
