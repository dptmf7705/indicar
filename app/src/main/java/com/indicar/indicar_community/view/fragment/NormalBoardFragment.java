package com.indicar.indicar_community.view.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.BoardListAdapter;
import com.indicar.indicar_community.utils.HTTPClient;
import com.indicar.indicar_community.vo.BbsVO;
import com.indicar.indicar_community.vo.FileDetailVO;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NormalBoardFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private BoardListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public NormalBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_normal_board, container, false);
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recycler_view);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BoardListAdapter(context);
        recyclerView.setAdapter(adapter);

        selectBoardArticles();

        return view;
    }

    private void selectBoardArticles(){
        RequestParams params = new RequestParams();
        params.put("bbs_id", "all");
        params.put("searchCnd", "");
        HTTPClient.post("/selectBoardArticles", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int index, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                ArrayList<BbsVO> list = new ArrayList<>();
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
                        list.add(vo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setTextView(list);

                for(int i = 0 ; i < list.size() ; i++) {
                    selectFileInfs(i, list.get(i).getAtch_file_id());
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }

    private void selectFileInfs(final int index, String atch_file_id){
        RequestParams params3 = new RequestParams("atch_file_id", atch_file_id);
        HTTPClient.post("/selectFileInfs", params3, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                ArrayList<FileDetailVO> list = new ArrayList<>();
                String result = new String(bytes);
                try {
                    JSONArray array = new JSONArray(result);

                    for(int j = 0 ; j < array.length(); j++) {
                        JSONObject json = array.getJSONObject(j);
                        FileDetailVO vo = new FileDetailVO();
                        vo.set_id(json.getString("_id"));
                        vo.setAtch_file_id(json.getString("atch_file_id"));
//                        vo.setFile_sn(json.getInt("file_sn"));
                        vo.setFile_stre_cours(json.getString("file_stre_cours"));
                        vo.setStre_file_nm(json.getString("stre_file_nm"));
                        vo.setOrignl_file_nm(json.getString("orignl_file_nm"));
                        vo.setFile_extsn(json.getString("file_extsn"));
                        vo.setFile_cn(json.getString("file_cn"));
                        vo.setFile_size(json.getInt("file_size"));
                        vo.set__v(json.getInt("__v"));

                        StringBuffer sb = new StringBuffer(json.getString("file_stre_cours") + "\n");
                        sb.delete(0,24);
                        vo.setFileStoreUri(sb.toString());

                        int filesn = json.getInt("file_sn");
                        vo.setFile_sn(filesn);
                        if(filesn == 0){
                            getImage(index, vo);
                        }
                        list.add(vo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.addFileList(index, list);
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
                Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
                if(bitmap != null) {
                    setImageView(index, bitmap);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }


    private void setTextView(ArrayList<BbsVO> list){
        adapter.addList(list);
        adapter.notifyDataSetChanged();
    }

    private void setImageView(int index, Bitmap bitmap){
        adapter.addImage(index, bitmap);
        adapter.notifyDataSetChanged();
    }
}
