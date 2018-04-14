package com.indicar.indicar_community.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.view.adapter.CommunityPagerAdapter;
import com.indicar.indicar_community.utils.HttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;
import static android.view.inputmethod.InputMethodManager.SHOW_FORCED;
import static com.indicar.indicar_community.utils.Constants.NUM_OF_BOARD_BUTTONS;

public class M2CommunityFragment extends Fragment {
    private final String[] TAB_NAME = {"인기", "전체"};
    private String bbs_id;
    private Context context;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CommunityPagerAdapter pagerAdapter;
    private ImageButton searchButtonToggle;
    private LinearLayout searchBar;
    private ImageButton searchButton;
    private EditText searchText;

    public M2CommunityFragment() {
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
        View view = inflater.inflate(R.layout.m2_fragment_community, container, false);
        context = view.getContext();

        ImageView iv_write_board = view.findViewById(R.id.iv_write_board);
        iv_write_board.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(context, W1BoardWriteActivity.class);
                    context.startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.enter_right_bottom, R.anim.exit_no_anim);
                }
                return true;
            }
        });

        searchText = view.findViewById(R.id.searchBarText);
        searchButtonToggle = view.findViewById(R.id.searchButtonToggle);
        searchButton = view.findViewById(R.id.searchButton);
        searchBar = view.findViewById(R.id.searchBarLayout);
        searchButtonToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchBar.getVisibility() == View.VISIBLE) {
                    searchBar.setVisibility(View.GONE);
                } else if(searchBar.getVisibility() == View.GONE){
                    Animation animSearchBar = AnimationUtils.loadAnimation(context, R.anim.searchbar_enter);
                    searchBar.startAnimation(animSearchBar);
                    searchBar.setVisibility(View.VISIBLE);
                    showKeyboard(searchText);
                    hideTab();
                    hideWriteButton();
                    searchButtonToggle.setVisibility(View.INVISIBLE);
                }
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.icon_enter);
                searchButtonToggle.startAnimation(anim);
                searchButtonToggle.setVisibility(View.VISIBLE);
                searchBar.setVisibility(View.GONE);
            }
        });

        searchText.setImeOptions(IME_ACTION_SEARCH);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId){
                    case IME_ACTION_SEARCH:
                        hideKeyboard(searchText);
                        showTab();
                        showWriteButton();
                }
                return false;
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

    private void hideTab(){

    }

    private void showTab(){

    }

    private void hideWriteButton(){

    }

    private void showWriteButton(){

    }

    private void showKeyboard(EditText editText){
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(SHOW_FORCED, 0);
    }

    private void hideKeyboard(EditText editText){
        editText.clearFocus();
        InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void selectBoardList(){
        HttpClient.get("/selectBoardList", null, new AsyncHttpResponseHandler() {
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
