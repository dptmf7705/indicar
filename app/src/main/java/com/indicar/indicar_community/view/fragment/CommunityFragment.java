package com.indicar.indicar_community.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.CommunityPagerAdapter;
import com.indicar.indicar_community.utils.HTTPClient;
import com.indicar.indicar_community.view.activity.BoardWriteActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.indicar.indicar_community.utils.Constants.NUM_OF_BOARD_BUTTONS;

public class CommunityFragment extends Fragment {
    private final String[] TAB_NAME = {"인기", "전체"};
    private String bbs_id;
    private Context context;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CommunityPagerAdapter pagerAdapter;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            bbs_id = savedInstanceState.getString("bbs_id");
        } else {
            selectBoardList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        context = view.getContext();

        ImageView iv_write_board = view.findViewById(R.id.iv_write_board);
        iv_write_board.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(context, BoardWriteActivity.class);
                    context.startActivity(intent);
                }
                return true;
            }
        });

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);

        pagerAdapter = new CommunityPagerAdapter(context, getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        for(int i = 0 ; i < NUM_OF_BOARD_BUTTONS ; i++){
            tabLayout.getTabAt(i).setText(TAB_NAME[i]);
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private void selectBoardList(){
        HTTPClient.get("/selectBoardList", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int k, Header[] headers, byte[] bytes) {
                String result = new String(bytes);
                try {
                    JSONArray array = new JSONArray(result);
                    for(int i = 0 ; i < array.length() ; i++){
                        JSONObject json = array.getJSONObject(0);
                        setBbs_id(json.getString("bbs_id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {}
        });
    }

    public void setBbs_id(String bbs_id) {
        this.bbs_id = bbs_id;
    }
}
