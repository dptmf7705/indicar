package com.indicar.indicar_community.viewmodel;

import android.app.Activity;
import android.net.Uri;

import java.util.List;

/**
 * Created by yeseul on 2018-04-14.
 */

public interface IPickPhotoHelper<T extends Uri> {

    void pickFromCamera(loadPhotoCallBack callBack);

    void pickFromAlbum(loadPhotoListCallBack callBack);

    interface loadPhotoCallBack<T> {

        void onPhotoLoaded(T photoUri);

        void onPhotoNotAvailable();
    }

    interface loadPhotoListCallBack<T> {

        void onPhotoListLoaded(List<T> photoUriList);

        void onPhotoNotAvailable();
    }
}
