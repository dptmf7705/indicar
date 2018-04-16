package com.indicar.indicar_community.viewmodel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;

/**
 * Created by yeseul on 2018-04-13.
 */

public interface IBaseViewModel {
    void onCreate();
    void onResume();
    void onPause();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void onBackPressed();
}
