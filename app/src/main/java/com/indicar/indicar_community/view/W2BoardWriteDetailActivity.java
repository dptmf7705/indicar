package com.indicar.indicar_community.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.model.vo.BoardWriteVO;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.view.adapter.BoardWriteListAdapter;
import com.indicar.indicar_community.view.adapter.viewHolder.BoardWriteViewHolder;
import com.loopj.android.http.RequestParams;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.apptik.multiview.layoutmanagers.ViewPagerLayoutManager;

/**
 * TODO
 * 1. 페이지 하나 일 때 삭제 시, 내용이 있으면 reset
 * 2. 리사이클러 뷰 어댑터 : 페이지 추가, 사진 추가 기능 나눠서 구현
 *
 *
 *
 * */

public class W2BoardWriteDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private final int MAX_PAGE = 15;
    private int currentPage = -1;
    private int pageCount = 0;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICKER_REQUEST_CODE = 3;

    private List<Uri> selectedPhotoList;
    private List<String> selectedPhotoPath;

    private Uri photoUri;

    private CustomActionBar customActionBar;

    private ImageButton btnAdd;
    private ImageButton btnDelete;
    private TextView currentPageView;
    private TextView totalPageView;
    private ImageButton btnPrev;
    private ImageButton btnNext;

    private BoardWriteListAdapter adapter;
    private RecyclerView recyclerView;
    private ViewPagerLayoutManager layoutManager;

    private ImageButton btnSubmit;
    private ImageButton btnCancel;

    private ArrayList<BoardWriteVO> list;
    private BoardWriteViewHolder currentViewHolder;

    /**
     * 뒤로가기 버튼 눌렀을 때
     * 액티비티 전환 애니메이션 설정
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w2_activity_board_write_detail);

        // 키보드 input mode
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        initView();
        setupPager();
        addPage(); // 첫 페이지 추가
    }
/*

    private void initActionBar() {
        CustomActionBar.with(this).init(getSupportActionBar());
    }
*/


    private void initView() {

//        initActionBar();

        currentPageView = findViewById(R.id.currentPage);

        totalPageView = findViewById(R.id.totalPage);

        btnAdd = findViewById(R.id.btnAddSlide);
        btnAdd.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDeleteSlide);
        btnDelete.setOnClickListener(this);

        btnPrev = findViewById(R.id.buttonPrev);
        btnPrev.setOnClickListener(this);

        btnNext = findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(this);

        btnCancel = findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(this);

        btnSubmit = findViewById(R.id.buttonSubmit);
        btnSubmit.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onClick(View view) {

        /**
         * 리사이클러 뷰 관련 클릭 이벤트 처리
         */
        switch (view.getId()){
            case R.id.btnAddSlide: // 페이지 추가
                if(pageCount < MAX_PAGE) {
                    addPage();
                }
                break;
            case R.id.btnDeleteSlide: // 현재 페이지 삭제
                if(pageCount > 1) {
                    removePage();
                }
                break;
            case R.id.buttonPrev: // 이전 페이지로 이동
                if(currentPage > 0) {
                    pageChangeTo(currentPage - 1);
                }
                break;
            case R.id.buttonNext: // 다음 페이지로 이동
                if(currentPage < (pageCount - 1)) {
                    pageChangeTo(currentPage + 1);
                }
                break;
            case R.id.buttonCancel: // 글작성 취소

                break;
            case R.id.buttonSubmit: // 게시글 업로드
                list = adapter.getItems();
                insertBoardArticle();
                break;
        }

    }

    private void setupPager() {
        adapter = new BoardWriteListAdapter(this, R.layout.w2_layout_board_write_item,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /** 사진 추가 관련 클릭 이벤트 처리 */
                        if(view.getTag() != null){
                            int tag = Integer.parseInt(view.getTag().toString());

                            switch (tag) {
                                case 0: // 앨범에서 사진 선택
                                    openBottomPicker();
                                    break;
                                case 1: // 카메라 사진 촬영
                                    takePhoto();
                                    break;
                                case 2: // 적용된 이미지 삭제
                                    break;
                            }
                        }
                    }
                });
        layoutManager = new ViewPagerLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                currentPage = layoutManager.findFirstVisibleItemPosition();
                currentViewHolder = (BoardWriteViewHolder) recyclerView.findViewHolderForAdapterPosition(currentPage);
                currentPageView.setText("" + (currentPage + 1));
            }
        });
    }

    private void openBottomPicker() {
        Matisse.from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(15)
                .theme(R.style.photoPickerTheme)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(PICKER_REQUEST_CODE);
    }
    
    /**
     * currentPage 다음에 페이지 하나 추가한 후, 해당 페이지로 이동
     */
    private void addPage(){
        // pageCount 가 15보다 작을 때만 페이지를 추가한다.
        if(pageCount < MAX_PAGE) {
            /* 페이지 추가 */
           BoardWriteVO vo = new BoardWriteVO();
            adapter.addItem(currentPage + 1, vo); // 어댑터에 새로운 페이지 추가
            pageCount = adapter.getItemCount(); // 페이지 개수 어댑터에서 받아옴
            totalPageView.setText("" + pageCount); // 페이지 개수 출력

            pageChangeTo(currentPage + 1);
        } else {
            Toast.makeText(this, "페이지 수 최대", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  list 개수 만큼 페이지 추가 (앨범 이미지 다중 선택시 호출)
     * */
    private void addPageList(ArrayList<BoardWriteVO> list){
        if((pageCount + list.size()) <= MAX_PAGE){
            adapter.addItemList(currentPage, list);
            pageCount = adapter.getItemCount();
            totalPageView.setText("" + pageCount);
            
            pageChangeTo(currentPage);
        } else{
            Toast.makeText(this, "페이지 수 최대", Toast.LENGTH_SHORT).show();
        }
    }

    private void addPageList(int pageCount){
        ArrayList<BoardWriteVO> list = new ArrayList<>();
        for(int i = 0 ; i < pageCount ; i++){
            list.add(new BoardWriteVO());
        }
        addPageList(list);
    }

    /**
     * currentPage 삭제
     * */
    private void removePage(){
        // pageCount 가 1보다 클 때만 페이지를 삭제한다.
        if(pageCount > 1){
            adapter.removeItem(currentPage);
            pageCount = adapter.getItemCount();
            totalPageView.setText("" + pageCount);
            pageChangeTo(currentPage);
        }
    }

    /**
     * 해당 position 의 페이지로 이동
     * */
    private void pageChangeTo(int position){
        currentPage = position;
        recyclerView.smoothScrollToPosition(currentPage);
    }

    /**
     * 이미지 선택 후 로직 처리
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else if (requestCode == PICK_FROM_CAMERA) {
            cropImage();
            // 갤러리에 나타나게
            MediaScannerConnection.scanFile(this,
                    new String[]{photoUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
        } else if (requestCode == CROP_FROM_CAMERA) {
            //////////////////// 현재 페이지의 이미지 뷰에 뿌리기 ////////////////////
            /*Glide.with(this).load(photoUri).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    adapter.setImagePicked(currentPage, resource);
                    addPage();
                }
            });*/

            adapter.setImagePicked(currentPage, photoUri);
            addPage();

        } else if (requestCode == PICKER_REQUEST_CODE){
            selectedPhotoList = Matisse.obtainResult(data); // 앨범에서 받아온 uri 리스트
            final int IMAGE_COUNT = selectedPhotoList.size(); // 리스트 개수

            //////////////////// 페이지 하나씩 추가하면서 이미지 뷰에 뿌리기 ////////////////////

            ArrayList<BoardWriteVO> list = new ArrayList<>();
            for(int i = 0 ; i < IMAGE_COUNT ; i++){
                BoardWriteVO vo = new BoardWriteVO();
                vo.setUri(selectedPhotoList.get(i));
                list.add(vo);
                /*final int position = TEMP_CURRENT_PAGE + i;
                Glide.with(this).load(selectedPhotoList.get(i)).asBitmap().placeholder(R.drawable.app_icon).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        adapter.setImagePicked(position, selectedPhotoList.get());
                    }
                });*/
            }

            addPageList(list);
        }
    }


    // 이미지 크롭
    public void cropImage() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            grantUriPermission(list.get(0).activityInfo.packageName, photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            intent.putExtra("crop", "true");
            intent.putExtra("scale", true);

            File croppedFileName = null;
            try {
                croppedFileName = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File folder = new File(Environment.getExternalStorageDirectory() + "/indicar/");
            File tempFile = new File(folder.toString(), croppedFileName.getName());

            photoUri = FileProvider.getUriForFile(this,
                    "com.indicar.indicar_community.provider", tempFile);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                grantUriPermission(res.activityInfo.packageName, photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, CROP_FROM_CAMERA);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "nostest_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/indicar/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(this,
                    "com.indicar.indicar_community.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private void insertBoardArticle() {
        RequestParams params = new RequestParams();
        params.put("bbs_id", "test_main");
        params.put("ntt_sj", "제목");
        params.put("ntt_cn ", "내용");
        params.put("ntcr_nm", "글쓴이");

        for(int i = 0 ; i < list.size() ; i++) {
            if(list.get(i).getUri() != null) {
                try {
                    String filePath = list.get(i).getUri().getPath();
                    Log.d("", filePath);
                    params.put("file", new File(filePath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if(list.get(i).getText() != null) {
                params.put("fileCn_" + i, list.get(i).getText());
            }
        }

    }

    public String getRealPathFromURI(Uri contentUri)
    {
        String[] proj = { MediaStore.Audio.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
