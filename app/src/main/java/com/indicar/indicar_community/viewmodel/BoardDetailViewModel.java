package com.indicar.indicar_community.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.commit451.teleprinter.Teleprinter;
import com.indicar.indicar_community.model.BaseModel;
import com.indicar.indicar_community.model.BoardCommentModel;
import com.indicar.indicar_community.model.BoardFileModel;
import com.indicar.indicar_community.model.BoardModel;
import com.indicar.indicar_community.model.vo.BoardDetailVO;
import com.indicar.indicar_community.view.adapter.BoardCommentAdapter;
import com.indicar.indicar_community.view.adapter.BoardDetailAdapter;

import java.util.HashMap;
import java.util.List;

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
    public final ObservableField<BoardDetailVO> header = new ObservableField<>();
    public final ObservableField<BoardCommentAdapter> commentAdapter = new ObservableField<>();
    public final ObservableBoolean showCommentBox = new ObservableBoolean(false);
    public final ObservableBoolean commentUpdating = new ObservableBoolean(false);
    public final ObservableBoolean isScrollDown = new ObservableBoolean(false);
    public final ObservableBoolean isScrollUp = new ObservableBoolean(false);
    public final ObservableField<String> commentWrite = new ObservableField<>();

    BoardModel boardModel;
    BoardFileModel fileModel;
    BoardCommentModel commentModel;
    String bbsId;
    String nttId;
    int answerNo;

    int commentCount;

    public Teleprinter keyboard = null;
    public NestedScrollView scrollView = null;

//    UserModel userModel;

    public BoardDetailViewModel() {
        this.boardModel = new BoardModel();
        this.fileModel = new BoardFileModel();
        this.commentModel = new BoardCommentModel();
//        this.userModel = new UserModel();
    }

    @Override
    public void onCreate() {

    }

    public void onCreate(String bbsId, String nttId, String commentCount){
        this.bbsId = bbsId;
        this.nttId = nttId;
        getBoardData(bbsId, nttId);
        this.commentCount = Integer.parseInt(commentCount);

    }

    public void getBoardData(final String bbsId, final String nttId){
        final HashMap<String, String> map = new HashMap<>();
        map.put("bbs_id", bbsId);
        map.put("ntt_id", nttId);

        boardModel.getData(map, new BaseModel.LoadDataCallBack() {
            @Override
            public void onDataLoaded(Object data) {

                adapter.get().boardHeader.set((BoardDetailVO) data);
                notifyObservers();

                String atchFileId = ((BoardDetailVO)data).getAtchFileId();
                if(!atchFileId.equals("")) {
                    getFileData(atchFileId);
                }

                if(commentCount > 0){
                    getCommentList(bbsId, nttId);
                }
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
                    adapter.get().addItems(list);
                }
            }
            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void getCommentList(String bbsId, String nttId) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("bbs_id", bbsId);
        map.put("ntt_id", nttId);

        commentModel.getDataList(map, new BaseModel.LoadDataListCallBack() {
            @Override
            public void onDataListLoaded(List list) {
                commentCount = list.size();
                commentAdapter.get().updateItems(list);
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
    public boolean onLikeButtonClick(View view, MotionEvent event){

        return true;
    }


    public boolean scrollBottom(View view, MotionEvent event, RecyclerView recyclerView){

        recyclerView.smoothScrollToPosition(commentAdapter.get().getItemCount() - 1);

        return true;
    }

    public boolean scrollTop(View view, MotionEvent event, RecyclerView recyclerView){

        recyclerView.smoothScrollToPosition(0);

        return true;
    }

    public void onSubmitClick(View view){
        if(commentUpdating.get()){
            updateComment();
        }else{
            insertComment();
        }
    }

    public void updateComment(){

        HashMap<String, Object> map = new HashMap<>();
        map.put("ntt_id", nttId);
        map.put("answer_no", answerNo);
        map.put("answer", commentWrite.get());

        commentModel.updateData(map, new BaseModel.LoadDataCallBack() {
            @Override
            public void onDataLoaded(Object data) {
                commentUpdating.set(false);
                getCommentList(bbsId, nttId);
                showCommentBox.set(false);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    public void insertComment(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("bbs_id", bbsId);
        map.put("ntt_id", nttId);
        map.put("answer", commentWrite.get());
        map.put("writer_nm", "이예슬");
        map.put("writer_id", "admin");

        commentModel.insertData(map, new BaseModel.LoadDataCallBack() {
            @Override
            public void onDataLoaded(Object data) {
                getCommentList(bbsId, nttId);
                showCommentBox.set(false);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    /* Data Binding
    *
    * 댓글 버튼의 onClick 메서드
    * */
    public boolean onCommentButtonClick(View view, MotionEvent event, EditText editText){

        if(keyboard == null) {
            keyboard = new Teleprinter((Activity) view.getContext());
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            keyboard.addKeyboardToggleListener(new Teleprinter.OnKeyboardToggleListener() {
                @Override
                public void onKeyboardShown(int keyboardSize) {
                    showCommentBox.set(true);
                }

                @Override
                public void onKeyboardClosed() {
                    showCommentBox.set(false);
                }
            });
            showCommentBox.set(true);
            editText.requestFocus();
            keyboard.showKeyboard(editText);
        }

        return true;
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

    public void openCommentBoxWithUpdateItem(int position) {
        showCommentBox.set(true);
        commentWrite.set(commentAdapter.get().getItem(position).getContent());
        answerNo = commentAdapter.get().getItem(position).getCommentIndex();
        commentUpdating.set(true);
    }

    public void deleteCommentItem(int position) {
        answerNo = commentAdapter.get().getItem(position).getCommentIndex();
        deleteComment();
    }

    private void deleteComment() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ntt_id", nttId);
        map.put("answer_no", answerNo);

        commentModel.deleteData(map, new BaseModel.LoadDataCallBack() {
            @Override
            public void onDataLoaded(Object data) {
                commentCount--;

                if(commentCount == 0){
                    commentAdapter.get().clearItems();
                }else {
                    getCommentList(bbsId, nttId);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }
}
