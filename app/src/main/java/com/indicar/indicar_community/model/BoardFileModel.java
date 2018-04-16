package com.indicar.indicar_community.model;

import com.indicar.indicar_community.model.vo.BoardFileVO;
import com.indicar.indicar_community.utils.HttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BoardFileModel implements BaseModel<BoardFileVO> {

    @Override
    public void getDataList(HashMap<String, String> map, final LoadDataListCallBack callBack) {
        final String URL = "/selectFileInfs";

        String atchFileId = map.get("atch_file_id");

        RequestParams params = new RequestParams();
        params.put("atch_file_id", atchFileId);

        final List<BoardFileVO> fileList = new ArrayList<>();

        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {
                JSONArray resultArray = null;

                try {
                    resultArray = new JSONArray(new String(bytes));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(resultArray != null) {

                    for(int i = 0 ; i < resultArray.length() ; i++) {
                        try {
                            JSONObject resultJson = resultArray.getJSONObject(i);

                            BoardFileVO vo = new BoardFileVO();

                            vo.atchFileId.set(resultJson.getString("atch_file_id")); // 파일 id
                            vo.fileIndex.set(resultJson.getInt("file_sn")); // 파일 index
                            vo.fileUrl.set(resultJson.getString("file_stre_cours")); // 파일 url
                            vo.storeFileName.set(resultJson.getString("stre_file_nm")); // 파일 이름 (DB)
                            vo.originalFileName.set(resultJson.getString("orignl_file_nm")); // 파일 이름 (원본)
                            vo.fileExtension.set(resultJson.getString("file_extsn")); // 파일 확장자
                            vo.content.set(resultJson.getString("file_cn")); // 글 내용

                            fileList.add(vo);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                callBack.onDataListLoaded(fileList);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @Override
    public void getData(HashMap<String, String> map, final LoadDataCallBack callBack) {
        final String URL = "/selectFileInfs";

        String atchFileId = map.get("atch_file_id");
        final String fileIndex = map.get("file_sn");

        RequestParams params = new RequestParams();
        params.put("atch_file_id", atchFileId);

        final BoardFileVO file = new BoardFileVO();

        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {
                JSONArray resultArray = null;

                try {
                    resultArray = new JSONArray(new String(bytes));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(resultArray != null) {

                    for(int i = 0 ; i < resultArray.length() ; i++) {
                        try {
                            JSONObject resultJson = resultArray.getJSONObject(i);

                            String file_sn = resultJson.getString("file_sn"); // 파일 index

                            if(fileIndex.equals(file_sn)) {
                                file.atchFileId.set(resultJson.getString("atch_file_id")); // 파일 id
                                file.fileIndex.set(resultJson.getInt("file_sn")); // 파일 index
                                file.fileUrl.set(resultJson.getString("file_stre_cours")); // 파일 url
                                file.storeFileName.set(resultJson.getString("stre_file_nm")); // 파일 이름 (DB)
                                file.originalFileName.set(resultJson.getString("orignl_file_nm")); // 파일 이름 (원본)
                                file.fileExtension.set(resultJson.getString("file_extsn")); // 파일 확장자
                                file.content.set(resultJson.getString("file_cn")); // 글 내용
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                callBack.onDataLoaded(file);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }


    @Override
    public void insertData(HashMap<String, Object> map) {

    }

    @Override
    public void updateData(HashMap<String, Object> map) {

    }

}
