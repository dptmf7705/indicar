package com.indicar.indicar_community.viewmodel;

import android.content.Intent;

import com.indicar.indicar_community.model.BaseModel;
import com.indicar.indicar_community.model.BoardCommentModel;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 * Created by yeseul on 2018-04-14.
 */

public class BoardCommentViewModel extends BaseViewModel {

    BoardCommentModel commentModel;
//    UserModel userModel;

    public BoardCommentViewModel(){
        this.commentModel = new BoardCommentModel();
//        this.userModel = new UserModel();
    }

    public void start(String bbsId, String nttId){
        getCommentData(bbsId, nttId);
    }

    private void getCommentData(String bbsId, String nttId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("bbs_id", bbsId);
        map.put("ntt_id", nttId);

        commentModel.getDataList(map, new BaseModel.LoadDataListCallBack() {
            @Override
            public void onDataListLoaded(List list) {
                notifyObservers(list);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onBackPressed() {

    }
}
