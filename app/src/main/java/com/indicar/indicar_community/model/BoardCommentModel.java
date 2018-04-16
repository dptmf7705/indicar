package com.indicar.indicar_community.model;

import com.indicar.indicar_community.model.vo.BoardCommentVO;
import com.indicar.indicar_community.utils.DateUtil;
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

public class BoardCommentModel implements BaseModel<BoardCommentVO> {

    @Override
    public void getDataList(HashMap<String, String> map, final LoadDataListCallBack callBack) {
        final String URL = "/selectCommentList";

        String bbsId = map.get("bbs_id");
        String nttId = map.get("ntt_id");

        RequestParams params = new RequestParams();
        params.put("bbs_id", bbsId);
        params.put("ntt_id", nttId);

        final List<BoardCommentVO> commentList = new ArrayList<>();

        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONArray resultArray = null;

                try {
                    resultArray = new JSONArray(new String(responseBody));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(resultArray != null){

                    for (int i = 0; i < resultArray.length(); i++) {
                        try {
                            JSONObject resultJson = resultArray.getJSONObject(i);

                            BoardCommentVO vo = new BoardCommentVO();
                            vo.boardType.set(resultJson.getString("bbs_id")); // 게시판 id
                            vo.boardId.set(resultJson.getString("ntt_id")); // 게시글 id
                            vo.commentIndex.set(Integer.parseInt(resultJson.getString("answer_no"))); // 댓글 index
                            vo.content.set(resultJson.getString("answer")); // 댓글 내용
                            vo.userName.set(resultJson.getString("writer_nm")); // 작성자
                            vo.firstTime.set(DateUtil.convertStringToDate(resultJson.getString("first_time"))); // 작성 날짜
                            vo.lastUpdateTime.set(DateUtil.convertStringToDate(resultJson.getString("last_updt_time"))); // 수정 날짜

                            commentList.add(vo);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                callBack.onDataListLoaded(commentList);
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
    public void insertData(HashMap<String, Object> map) {

    }

    @Override
    public void updateData(HashMap<String, Object> map) {

    }
}
