package com.indicar.indicar_community.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.viewmodel.BaseViewModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yeseul on 2018-04-16.
 *
 * TODO 프래그먼트 Life Cycle 정리
 *
 *     << ADDED >>
 *
 *      onAttach()                              Fragment 가 Activity 에 붙을 때 호출
 *
 *      onCreate()                              Activity 의 onCreate 와 비슷하나, UI 관련 작업은 할 수 없다
 *
 *      onCreateView()  ＜─────────────┐        Layout 을 inflate 한 후, View 작업을 실행
 *
 *      onActivityCreated()           │         (Activity setContentView 다음) UI 변경 작업이 가능하다.
 *
 *      onStart()                    │          Fragment 가 화면에 표시될 때 호출된다.
 *
 *      onResume()                  │           Action 과 상호작용 가능하다.
 *
 *     << ACTIVE >>          << back stack >>
 *
 *      onPause()                 │             Action 과 상호작용을 중지한다.
 *
 *      onStop()                 │              Fragment 가 화면에서 사라질 때 호출된다.
 *
 *      onDestroyView() ────────┘               View 리소스를 해제한다.
 *
 *      onDestroy()                             Fragment 를 완전히 종료할 때 호출
 *
 *      onDetach()                              Fragment 가 Activity 와 연결이 완전히 끊길 때 호출
 *
 *    << DESTROYED >>
 *
 */

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment implements Observer {

    protected B binding;
    protected Context context;

    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        context = binding.getRoot().getContext();

        return binding.getRoot();
    }

}
