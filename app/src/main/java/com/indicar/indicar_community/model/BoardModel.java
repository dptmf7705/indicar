package com.indicar.indicar_community.model;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.indicar.indicar_community.model.vo.BoardDetailVO;
import com.indicar.indicar_community.model.vo.BoardWriteVO;
import com.indicar.indicar_community.utils.DateUtil;
import com.indicar.indicar_community.utils.HttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BoardModel implements BaseModel<BoardDetailVO> {

    private final String TAG = BoardModel.class.getSimpleName();

    @Override
    public void getDataList(HashMap<String, String> map, final LoadDataListCallBack callBack) {
        final String URL = "/selectBoardArticles";
        final int PAGE_UNIT_COUNT = 15;

        String bbsId = map.get("bbs_id");
        String searchCnd = map.get("searchCnd");
        String pageIndex = map.get("pageIndex");

        RequestParams params = new RequestParams();
        params.put("bbs_id", bbsId);
        params.put("searchCnd", searchCnd);
        params.put("pageIndex", pageIndex);
        params.put("pageUnit", PAGE_UNIT_COUNT);

        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {
                JsonElement result = new JsonParser().parse(new String(bytes));

                // 게시물 리스트 존재
                if (result.isJsonArray()){
                    JsonArray array = result.getAsJsonArray();

                    List<BoardDetailVO> boardList = new ArrayList<>();

                    for(int i = 0 ; i < array.size() ; i++) {

                        // 게시물 끝
                        if (!array.get(i).isJsonObject()) break;

                        BoardDetailVO vo = new Gson().fromJson(array.get(i), BoardDetailVO.class);
                        Log.d(TAG, "board vo(" + i + ") : " + vo.toString());
                        boardList.add(vo);
                    }

                    callBack.onDataListLoaded(boardList);

                } else {

                    callBack.onDataNotAvailable();
                }

                Type listType = new TypeToken<List<BoardDetailVO>>(){}.getType();

                List<BoardDetailVO> boardList = new Gson().fromJson(new String(bytes), listType);

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }

        });

    }

    @Override
    public void getData(HashMap<String, String> map, final LoadDataCallBack callBack) {

        final String url = "/selectBoardArticle";

        String bbsId = map.get("bbs_id");
        String nttId = map.get("ntt_id");

        RequestParams params = new RequestParams();
        params.put("bbs_id", bbsId);
        params.put("ntt_id", nttId);

        final BoardDetailVO board = new BoardDetailVO();

        HttpClient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {

                BoardDetailVO board = new Gson().fromJson(new String(bytes), BoardDetailVO.class);

                Log.d(TAG, "board vo : " + board.toString());
                callBack.onDataLoaded(board);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @Override
    public void insertData(HashMap<String, Object> map, LoadDataCallBack callBack) {
        final String URL = "/insertBoardArticle";

        String bbsId = map.get("bbs_id").toString();
        String nttSj = map.get("ntt_sj").toString();
        String nttCn = map.get("ntt_cn").toString();
        String ntcrNm = "관리자";
        String ntcrId = "admin";
        File[] fileList = (File[]) map.get("file_list");
        List<String> contentList = (List<String>) map.get("content_list");

        RequestParams params = new RequestParams();
        params.put("bbs_id", bbsId);
        params.put("ntt_sj", nttSj);
        params.put("ntt_cn", nttCn);
        params.put("ntcr_nm", ntcrNm);
        params.put("ntcr_id", ntcrId);
        try {
            params.put("file", fileList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for( int i = 0 ; i < contentList.size() ; i++){
            params.put("fileCn_" + i, contentList.get(i));
        }

        HttpClient.uploadFiles(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void updateData(HashMap<String, Object> map, LoadDataCallBack callBack) {

    }

    @Override
    public void deleteData(HashMap<String, Object> map, LoadDataCallBack callBack) {

    }

}
