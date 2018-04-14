package com.indicar.indicar_community.viewmodel;

import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.indicar.indicar_community.model.BaseModel;
import com.indicar.indicar_community.model.BoardFileModel;
import com.indicar.indicar_community.model.BoardModel;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 * Created by yeseul on 2018-04-13.
 *
 * 게시물 정보, 게시물 리스트, 댓글 컨트롤
 *
 * TODO
 *  유저 정보 추가하기
 *
 */

public class BoardDetailViewModel extends Observable {

    private static final String TAG = BoardDetailViewModel.class.getSimpleName();

    BoardModel boardModel;
//    BoardFileModel fileModel;
//    UserModel userModel;

    public BoardDetailViewModel() {
        this.boardModel = new BoardModel();
//        this.fileModel = new BoardFileModel();
//        this.userModel = new UserModel();
    }

    public void start(String bbsId, String nttId, String atchFileId){
        getBoardData(bbsId, nttId);
        getFileData(atchFileId);
    }

    private void getFileData(String atchFileId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("atch_file_id", atchFileId);
/*
        fileModel.getDataList(map, new BaseModel.LoadDataListCallBack() {
            @Override
            public void onDataListLoaded(List list) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });*/
    }

    public void getBoardData(String bbsId, String nttId){
        HashMap<String, String> map = new HashMap<>();
        map.put("bbs_id", bbsId);
        map.put("ntt_id", nttId);

        boardModel.getData(map, new BaseModel.LoadDataCallBack() {
            @Override
            public void onDataLoaded(Object data) {

                Log.d(TAG, "update()");
                setChanged();
                notifyObservers(data);
            }

            @Override
            public void onDataNotAvailable() {

            }
        }); // 게시물 정보
/*
        fileModel.getDataList(map, new BaseModel.LoadDataListCallBack() {
            @Override
            public void onDataListLoaded(List list) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        }); // 게시글 items*/
    }

    /* Data Binding
    *
    * 좋아요 버튼 onClick 메서드
    * */
    public void onLikeButtonClick(View view){
        notifyObservers("hello");
    }

    /* Data Binding
    *
    * 좋아요 버튼의 onClick 메서드
    * */
    public void onCommentButtonClick(View view){

    }

}
