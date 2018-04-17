package com.indicar.indicar_community.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.CommunityFragmentBinding;

import java.util.Observable;

import static android.view.inputmethod.InputMethodManager.SHOW_FORCED;

public class CommunityFragment extends BaseFragment<CommunityFragmentBinding> {

    public final ObservableField<String> textSearch = new ObservableField<>();
    public final ObservableBoolean isSearchBarOpen = new ObservableBoolean(false);

    @Override
    protected int getLayoutId() {
        return R.layout.community_fragment;
    }

    public CommunityFragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setFragment(this);

        binding.viewPagerBoard.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabBoardType));

        binding.tabBoardType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                binding.viewPagerBoard.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.buttonBoardWrite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getContext(), BoardWriteActivity.class);
                    getContext().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.enter_right_bottom, R.anim.exit_no_anim);
                }
                return true;
            }
        });

    }

    private void showKeyboard(EditText editText){
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(SHOW_FORCED, 0);
    }

    private void hideKeyboard(EditText editText){
        editText.clearFocus();
        InputMethodManager inputManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 검색창 열기, 닫기
     * */
    public void toggleSearchBar() {

        isSearchBarOpen.set( !(isSearchBarOpen.get()) );

        if(isSearchBarOpen.get()){ // 검색창 open 애니메이션

            Animation animSearchBar = AnimationUtils.loadAnimation(getContext(), R.anim.searchbar_enter);
            binding.searchBarLayout.startAnimation(animSearchBar);

            showKeyboard(binding.textSearch);

        } else { // 검색창 close 애니메이션

            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.icon_enter);
            binding.buttonSearch.startAnimation(anim);

            hideKeyboard(binding.textSearch);
        }
    }

    @Override
    public void update(Observable observable, Object o) {

    }
/*

    */
/**
     * 검색 창 입력 텍스트를 실시간으로 받는다
     * *//*

    public void setSearchText(CharSequence text){
        textSearch.set(text.toString());
    }

*/

}

