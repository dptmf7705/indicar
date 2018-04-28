package com.indicar.indicar_community;

import android.content.Intent;

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
