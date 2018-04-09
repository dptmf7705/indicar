package com.indicar.indicar_community.view.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.adapters.BoardWriteListAdapter;
import com.indicar.indicar_community.utils.CustomActionBar;
import com.indicar.indicar_community.vo.BoardWriteVO;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import io.apptik.multiview.layoutmanagers.ViewPagerLayoutManager;

public class W2BoardWriteDetailActivity extends AppCompatActivity{
    private final int MAX_PAGE = 15;
    private int currentPage = -1;
    private int pageCount = 0;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_FROM_CAMERA = 3;

    private Uri photoUri;
    private String mCurrentPhotoPath;

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

    private TedBottomPicker bottomPicker;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        setupActionBar();
        setupView();
    }

    private void setupActionBar() {
        customActionBar = new CustomActionBar(this, getSupportActionBar());
        customActionBar.setBackgroundImage(R.drawable.logo_write);
        customActionBar.setLeftButtonImage(R.drawable.btn_back);
        customActionBar.commit();

        ImageView leftButton = customActionBar.getLeftButton();
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter_no_anim, R.anim.exit_no_anim);
            }
        });
    }

    private void setupView() {
        currentPageView = findViewById(R.id.currentPage);
        totalPageView = findViewById(R.id.totalPage);

        btnAdd = findViewById(R.id.btnAddSlide);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageCount < MAX_PAGE) {
                    addPage();
                }
            }
        });
        btnDelete = findViewById(R.id.btnDeleteSlide);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageCount > 1) {
                    removePage();
                }
            }
        });

        btnPrev = findViewById(R.id.buttonPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage > 0) {
                    pageChangeTo(currentPage - 1);
                }
            }
        });
        btnNext = findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage < (pageCount - 1)) {
                    pageChangeTo(currentPage + 1);
                }
            }
        });

        setupPager();
    }

    private void setupPager() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new BoardWriteListAdapter(this, R.layout.w2_layout_board_write_item);
        layoutManager = new ViewPagerLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                currentPage = layoutManager.findFirstVisibleItemPosition();

                BoardWriteListAdapter.ViewHolder holder = (BoardWriteListAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(currentPage);
                holder.buttonAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //goToAlbum();
                        openBottomPicker();
                    }
                });
                holder.buttonCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        takePhoto();
                    }
                });
                currentPageView.setText("" + (currentPage + 1));
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        addPage();
    }

    private void openBottomPicker() {
        bottomPicker = new TedBottomPicker.Builder(this)
                .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(ArrayList<Uri> uriList) {

                    }
                })
                .showCameraTile(false)
                .setPreviewMaxCount(Integer.MAX_VALUE)
                .setSelectMaxCount(15)
                .setSelectedForeground(R.drawable.selected_image_foreground)
                .setSpacing(2)
                .setPeekHeight(R.dimen.tedbottompicker_selected_image_height*3)
                .create();

        bottomPicker.show(getSupportFragmentManager());
    }


    /**
     * currentPage 다음에 페이지 하나 추가한 후 추가된 페이지로 이동
     */
    private void addPage(){
        // pageCount 가 15보다 작을 때만 페이지를 추가한다.
        if(pageCount < MAX_PAGE) {
            /* 페이지 추가 */
            adapter.addItem(currentPage + 1, new BoardWriteVO()); // 어댑터에 새로운 페이지 추가
            pageCount = adapter.getItemCount(); // 페이지 개수 어댑터에서 받아옴
            totalPageView.setText("" + pageCount); // 페이지 개수 출력

            pageChangeTo(currentPage + 1);
        } else {
            Toast.makeText(this, "페이지 수 최대", Toast.LENGTH_SHORT).show();
        }
    }

    private void removePage(){
        // pageCount 가 1보다 클 때만 페이지를 삭제한다.
        if(pageCount > 1){
            adapter.removeItem(currentPage);
            pageCount = adapter.getItemCount();
            totalPageView.setText("" + pageCount);
            pageChangeTo(currentPage);
        }
    }

    private void pageChangeTo(int position){
        currentPage = position;
        recyclerView.smoothScrollToPosition(currentPage);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == PICK_FROM_ALBUM) {
            if (data == null) {
                return;
            }
            photoUri = data.getData();
            cropImage();
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
            Bitmap bitmap = null;
            try {
               bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            adapter.setImagePicked(currentPage, bitmap);
        }
        recyclerView.smoothScrollToPosition(currentPage);
    }

    //Android N crop image
    public void cropImage() {
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.grantUriPermission("com.android.camera", photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }*/
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

            File folder = new File(Environment.getExternalStorageDirectory() + "/NOSTest/");
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
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/NOSTest/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
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

    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

}
