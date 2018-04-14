package com.indicar.indicar_community.view;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.indicar.indicar_community.utils.CustomActionBar;

/**
 * Created by yeseul on 2018-04-13.
 */

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    /* Data Binding
        *
        * layout 파일이름을 기준으로 파스칼표기법의 Binding 클래스가 자동 생성된다.
        * */
    protected B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Data Binding
        *
        * 1. setContentView 대신 DataBindingUtil 의 setContentView 를 사용한다.
        * */

        Log.d(TAG, "onCreate()");

        binding = DataBindingUtil.setContentView(this, getLayoutId());

        Log.d(TAG, "onCreate() with binding: " + binding.getRoot().toString());
    }

    protected abstract int getLayoutId();

}
