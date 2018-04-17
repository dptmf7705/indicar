package com.indicar.indicar_community.viewmodel;

import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.indicar.indicar_community.model.BaseModel;
import com.indicar.indicar_community.model.BoardFileModel;
import com.indicar.indicar_community.model.BoardModel;
import com.indicar.indicar_community.model.vo.BoardDetailVO;
import com.indicar.indicar_community.model.vo.BoardFileVO;
import com.indicar.indicar_community.view.adapter.BoardDetailAdapter;

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

public class BoardDetailViewModel extends BaseViewModel {

    private static final String TAG = BoardDetailViewModel.class.getSimpleName();

    public final ObservableField<BoardDetailAdapter> adapter = new ObservableField<>();
    public final ObservableField<StaggeredGridLayoutManager> layoutManager = new ObservableField<>();
    public final ObservableField<BoardDetailVO> board = new ObservableField<>();
    public final ObservableField<BoardFileVO> boardItem = new ObservableField<>();

    BoardModel boardModel;
    BoardFileModel fileModel;
//    UserModel userModel;

    public BoardDetailViewModel() {
        this.boardModel = new BoardModel();
        this.fileModel = new BoardFileModel();
//        this.userModel = new UserModel();
    }

    @Override
    public void onCreate() {

    }

    public void onCreate(String bbsId, String nttId){
        getBoardData(bbsId, nttId);
    }

    public void getBoardData(String bbsId, String nttId){
        HashMap<String, String> map = new HashMap<>();
        map.put("bbs_id", bbsId);
        map.put("ntt_id", nttId);

        boardModel.getData(map, new BaseModel.LoadDataCallBack() {
            @Override
            public void onDataLoaded(Object data) {

                board.set((BoardDetailVO) data);
                notifyObservers();

                String atchFileId = ((BoardDetailVO)data).atchFileId.get();
                getFileData(atchFileId);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void getFileData(String atchFileId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("atch_file_id", atchFileId);

        Log.d("getFileData", "atch_file_id: " + atchFileId);

        fileModel.getDataList(map, new BaseModel.LoadDataListCallBack() {
            @Override
            public void onDataListLoaded(List list) {

                if (list != null) {
                    boardItem.set((BoardFileVO) list.get(0));
                    notifyObservers();

                    list.remove(0);
                    adapter.get().addItems(list);

                    Log.d("getFileData", "test" + adapter.get().getItem(0).atchFileId);

                }
            }
            @Override
            public void onDataNotAvailable() {

            }
        });
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
