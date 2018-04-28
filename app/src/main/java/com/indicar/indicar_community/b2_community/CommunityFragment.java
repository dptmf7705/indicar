package com.indicar.indicar_community.b2_community;

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
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.indicar.indicar_community.BaseFragment;
import com.indicar.indicar_community.R;
import com.indicar.indicar_community.b2_community.boardWrite.BoardWriteActivity;
import com.indicar.indicar_community.databinding.CommunityFragmentBinding;

import java.util.Observable;

import static android.view.inputmethod.InputMethodManager.SHOW_FORCED;
import static com.indicar.indicar_community.Constant.BoardWrite.NEW;

public class CommunityFragment extends BaseFragment<CommunityFragmentBinding> {

    public final ObservableField<String> textSearch = new ObservableField<>();
    public final ObservableBoolean isSearchBarOpen = new ObservableBoolean(false);
    public final ObservableBoolean showButton = new ObservableBoolean(true);

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
                    intent.putExtra("command", NEW);
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

        if(!isSearchBarOpen.get()){ // 검색창 open 애니메이션

            openSearchBar();

        } else { // 검색창 close 애니메이션

            closeSearchBar();

        }
    }

    public void openSearchBar(){

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1,
                Animation.RELATIVE_TO_SELF, 0
                );
        animation.setDuration(300);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                isSearchBarOpen.set(true);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                showKeyboard(binding.textSearch);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        binding.searchBarLayout.startAnimation(animation);

    }

    public void closeSearchBar() {

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1
        );
        animation.setDuration(300);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                hideKeyboard(binding.textSearch);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                isSearchBarOpen.set(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        binding.searchBarLayout.startAnimation(animation);

    }

    @Override
    public void update(Observable observable, Object o) {

    }

}

