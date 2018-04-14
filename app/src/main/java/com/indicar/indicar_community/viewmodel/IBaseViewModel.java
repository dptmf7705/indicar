package com.indicar.indicar_community.viewmodel;

import android.databinding.Observable;
import android.databinding.ViewDataBinding;

/**
 * Created by yeseul on 2018-04-13.
 */

public interface IBaseViewModel {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();
}
