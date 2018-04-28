package com.indicar.indicar_community.data.vo;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.indicar.indicar_community.BR;

/**
 * Created by yeseul on 2018-04-14.
 */

public class UserVO extends BaseObservable{

    @SerializedName("_id") private String userKey = "";
    @SerializedName("name") private String userName = "";
    @SerializedName("email") private String userEmail = "";
    @SerializedName("profile_img_url") private String profileImageUrl = "";

    @Bindable
    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
        notifyPropertyChanged(BR.userKey);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        notifyPropertyChanged(BR.userEmail);
    }

    @Bindable
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
        notifyPropertyChanged(BR.profileImageUrl);
    }
}
