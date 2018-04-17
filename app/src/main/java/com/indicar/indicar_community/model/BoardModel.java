package com.indicar.indicar_community.model;

import android.util.Log;

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

        final List<BoardDetailVO> boardList = new ArrayList<>();

        HttpClient.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {
                JSONArray resultArray = null;

                try {
                    resultArray = new JSONArray(new String(bytes));
                } catch (JSONException e) {
                    e.printStackTrace();
                    /**
                     *  if, last page
                     *  response)
                     *
                     *      {
                     *          "error" : "out of range"
                     *      }
                     *
                     * */

                    try {
                        JSONObject json = new JSONObject(new String(bytes));
                        if (json.getString("error").equals("out of range")) {
                            callBack.onDataListLoaded(null);
                            return;
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                }

                if (resultArray != null) {
                    for (int i = 0; i < resultArray.length(); i++) {

                        try {

                            JSONObject resultJson = resultArray.getJSONObject(i);

                        /* TODO. "error":"out of range" --> 더이상 게시물 존재하지않음 */


                            BoardDetailVO vo = new BoardDetailVO();

                            vo.boardType.set(resultJson.getString("bbs_id")); // 게시판 id
                            vo.boardId.set(resultJson.getString("ntt_id")); // 게시글 id
                            vo.atchFileId.set(resultJson.getString("atch_file_id")); // 파일 id
                            vo.boardTitle.set(resultJson.getString("ntt_sj")); // 제목
                            vo.boardContent.set(resultJson.getString("ntt_cn")); // 내용
                            vo.userName.set(resultJson.getString("ntcr_nm")); // 작성자
                            vo.firstDate.set(DateUtil.convertStringToDate(resultJson.getString("frst_time"))); // 작성 날짜
                            vo.lastUpdateDate.set(DateUtil.convertStringToDate(resultJson.getString("last_updt_time"))); // 수정 날짜
                            vo.likeCount.set(resultJson.getString("like")); // 좋아요 count
                            vo.commentCount.set(resultJson.getString("CntCOMMENT")); // 댓글 count
                            vo.readCount.set(resultJson.getString("rdcnt")); // 조회수 count

                            boardList.add(vo);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }

                callBack.onDataListLoaded(boardList);
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
                try {
                    JSONObject resultJson = new JSONObject(new String(bytes));

                    board.boardType.set(resultJson.getString("bbs_id")); // 게시판 id
                    board.boardId.set(resultJson.getString("ntt_id")); // 게시글 id
                    board.atchFileId.set(resultJson.getString("atch_file_id")); // 파일 id
                    board.boardTitle.set(resultJson.getString("ntt_sj")); // 제목
                    board.boardContent.set(resultJson.getString("ntt_cn")); // 내용
                    board.userName.set(resultJson.getString("ntcr_nm")); // 작성자
//                    board.firstDate.set(DateUtil.convertStringToDate(resultJson.getString("frst_time"))); // 작성 날짜
//                    board.lastUpdateDate.set(DateUtil.convertStringToDate(resultJson.getString("last_updt_time"))); // 수정 날짜
                    board.likeCount.set(resultJson.getString("like")); // 좋아요 count
                    board.readCount.set(resultJson.getString("rdcnt")); // 조회수 count

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callBack.onDataLoaded(board);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @Override
    public void insertData(HashMap<String, Object> map) {
        final String URL = "/insertBoardArticle";

        List<BoardWriteVO> list = (List<BoardWriteVO>) map.get("item_list");

        String bbsId = map.get("bbs_id").toString();
        String nttSj = map.get("ntt_sj").toString();
        String nttCn = map.get("ntt_cn").toString();
        String ntcrNm = "관리자";
        String ntcrId = "admin";

        RequestParams params = new RequestParams();
        params.put("bbs_id", bbsId);
        params.put("ntt_sj", nttSj);
        params.put("ntt_cn", nttCn);
        params.put("ntcr_nm", ntcrNm);
        params.put("ntcr_id", ntcrId);


        for (int i = 0 ; i < list.size() ; i++) {

            String path = list.get(i).filePath.get();

            Log.d(TAG, "file path: " + path);

            try {
                params.put("file", new File(path), "image/jpeg");
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
            }
            params.put("fileCn_" + i, list.get(i).writeText.get());

        }

        HttpClient.uploadFiles(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e(TAG, responseBody.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, error.toString());
                Log.e(TAG, responseBody.toString());
            }
        });

    }

    @Override
    public void updateData(HashMap<String, Object> map) {

    }
}
