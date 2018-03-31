package com.indicar.indicar_community.view.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ScrollingView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.adapters.BoardListAdapter;
import com.indicar.indicar_community.utils.HTTPClient;
import com.indicar.indicar_community.utils.ScrollBottonAction;
import com.indicar.indicar_community.vo.BbsVO;
import com.indicar.indicar_community.vo.FileDetailVO;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class C12AllBoardFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private BoardListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<BbsVO> boardVoList;
    private SwipeRefreshLayout refreshView;

    public C12AllBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_c02_all_board, container, false);
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recycler_view);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BoardListAdapter(context, R.layout.layout_c02_all_board_list_item);
        recyclerView.setAdapter(adapter);

        selectBoardArticles();

        new ScrollBottonAction(recyclerView).setOnScrollBottomListener(new ScrollBottonAction.onScrollEndListener() {
            @Override
            public void onScrollBottom(ScrollingView view) {
                Toast.makeText(context, "bottom", Toast.LENGTH_SHORT).show();
                selectBoardArticles();
            }
        });

        refreshView = view.findViewById(R.id.refreshView);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.removeAll();
                selectBoardArticles();
            }
        });
        return view;
    }

    /**
     * 게시글 15개씩 가져오기
     * */
    private void selectBoardArticles(){
        boardVoList = new ArrayList<>(); // 게시글 리스트 초기화

        RequestParams params = new RequestParams();
        params.put("bbs_id", "all");
        params.put("searchCnd", "");
        HTTPClient.post("/selectBoardArticles", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    JSONArray array = new JSONArray(result);

                    for(int i = 0 ; i < array.length() ; i++) {
                        BbsVO vo = new BbsVO();
                        JSONObject json = array.getJSONObject(i);
                        vo.set_id(json.getString("_id"));
                        vo.setBbs_id(json.getString("bbs_id"));
                        vo.setNtt_sj(json.getString("ntt_sj"));
                        vo.setNtt_cn(json.getString("ntt_cn"));
                        vo.setNtcr_nm(json.getString("ntcr_nm"));
                        vo.setAtch_file_id(json.getString("atch_file_id"));
                        vo.setFrst_time(json.getString("frst_time"));
                        vo.setLast_updt_time(json.getString("last_updt_time"));
                        vo.setRdcnt(json.getInt("rdcnt"));
                        vo.setLike(json.getInt("like"));
                        vo.setNtt_id(json.getString("ntt_id"));
                        vo.set__v(json.getInt("__v"));
                        vo.setCntCOMMENT(json.getInt("CntCOMMENT"));

                        boardVoList.add(vo);
                    }

                    /* 가져온 게시물 개수만큼 이미지 파일 저장 경로 가져오기 */
                    for (BbsVO vo: boardVoList) {
                        selectFileInfs(vo);
                    }

                    adapter.addList(boardVoList); // 게시글 목록을 어댑터에 추가
                    refreshView.setRefreshing(false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void selectFileInfs(BbsVO board){
        final int index = boardVoList.indexOf(board);
        RequestParams params = new RequestParams("atch_file_id", board.getAtch_file_id());
        HTTPClient.post("/selectFileInfs", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int ii, Header[] headers, byte[] bytes) {
                ArrayList<FileDetailVO> fileVoList = new ArrayList<>(); // 사진 정보 리스트

                BbsVO board = boardVoList.get(index);
                String result = new String(bytes);
                try {
                    JSONArray array = new JSONArray(result);

                    for(int i = 0 ; i < array.length() ; i++) {
                        JSONObject json = array.getJSONObject(i);
                        FileDetailVO vo = new FileDetailVO();
                        vo.set_id(json.getString("_id"));
                        vo.setAtch_file_id(json.getString("atch_file_id"));
                        vo.setFile_sn(json.getInt("file_sn"));
                        vo.setFile_stre_cours(json.getString("file_stre_cours"));
                        vo.setStre_file_nm(json.getString("stre_file_nm"));
                        vo.setOrignl_file_nm(json.getString("orignl_file_nm"));
                        vo.setFile_extsn(json.getString("file_extsn"));
                        vo.setFile_cn(json.getString("file_cn"));
                        vo.setFile_size(json.getInt("file_size"));
                        vo.set__v(json.getInt("__v"));

                        StringBuffer sb = new StringBuffer(json.getString("file_stre_cours") + "\n");
                        sb.delete(0, 24); // 앞에 IP는 자름.
                        vo.setFileStoreUri(sb.toString());

                        if(json.getInt("file_sn") == 0){ // 메인 사진인경우에 이미지를 가져온다
                            getImage(index, vo);
                        }

                        fileVoList.add(vo);
//                        adapter.addFiles(board, fileVoList);
                        board.setFileList(fileVoList);
                    }

                    boardVoList.get(index).setFileList(fileVoList); // 파일 정보 해당 게시글 vo에 저장

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
    private void getImage(final int index, FileDetailVO vo){
        HTTPClient.get(vo.getFileStoreUri(), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] fileData) {
                BbsVO board = boardVoList.get(index);
                Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
                if(bitmap != null) {
                    adapter.addImage(board, bitmap);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }


}
