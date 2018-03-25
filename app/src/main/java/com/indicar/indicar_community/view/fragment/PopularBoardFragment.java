package com.indicar.indicar_community.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.BoardListAdapter;
import com.indicar.indicar_community.utils.HTTPClient;
import com.indicar.indicar_community.vo.BbsVO;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PopularBoardFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView btnFilter;
    private ArrayList<BbsVO> items;


    private TabLayout tabLayout;
    private ViewPager viewPager;

    public PopularBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_normal_board, container, false);

        context = view.getContext();

        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        selectBoardArticles();

        adapter = new BoardListAdapter(context);
        recyclerView.setAdapter(adapter);

        return view;
    }



    public void setBtnFilter(ImageView btnFilter) {
        this.btnFilter = btnFilter;
    }

    private void selectBoardArticles(){
        RequestParams params = new RequestParams();
        params.put("bbs_id", "test_main");
        params.put("searchCnd", "");
        HTTPClient.post("/selectBoardArticles", null, new AsyncHttpResponseHandler() {
            ArrayList<BbsVO> list = new ArrayList<>();
            @Override
            public void onSuccess(int k, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    JSONArray array = new JSONArray(result);
                    for(int i = 0 ; i < array.length() ; i++){
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

                setItems(list);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }

    public ArrayList<BbsVO> getItems() {
        return items;
    }

    public void setItems(ArrayList<BbsVO> items) {
        this.items = items;
    }
}
