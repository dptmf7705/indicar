package com.indicar.indicar_community.viewmodel;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.model.vo.BoardWriteVO;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yeseul on 2018-04-14.
 */

public class PickPhotoHelper implements IPickPhotoHelper<Uri> {

    public static final String TAG =  PickPhotoHelper.class.getSimpleName();

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_IMAGE = 2;
    private static final int PICK_FROM_ALBUM = 3;

    Activity context;

    Uri cameraPhotoUri;

    loadPhotoCallBack cameraCallBack;
    loadPhotoListCallBack albumCallBack;

    public PickPhotoHelper(Activity context){
        this.context = context;
    }

    @Override
    public void pickFromCamera(loadPhotoCallBack callBack) {
        this.cameraCallBack = callBack;
        takePhoto();
    }

    @Override
    public void pickFromAlbum(loadPhotoListCallBack callBack) {
        this.albumCallBack = callBack;
        openPhotoPicker();
    }

    private void openPhotoPicker(){
        Matisse.from(context)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(15)
                .theme(R.style.photoPickerTheme)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(PICK_FROM_ALBUM);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(context, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            context.finish();
            e.printStackTrace();
        }
        if (photoFile != null) {
            cameraPhotoUri = FileProvider.getUriForFile(context,
                    "com.indicar.indicar_community.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPhotoUri);
            context.startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode){
            case PICK_FROM_CAMERA:

                cropImage();

                MediaScannerConnection.scanFile(context,
                        new String[]{cameraPhotoUri.getPath()}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {

                            }
                        });
                break;

            case CROP_IMAGE:

                cameraCallBack.onPhotoLoaded(cameraPhotoUri);

                break;

            case PICK_FROM_ALBUM:

                List<Uri> selectedPhotoList = Matisse.obtainResult(data); // 앨범에서 받아온 uri 리스트

                albumCallBack.onPhotoListLoaded(selectedPhotoList);

                break;
        }
    }

    public void cropImage() {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(cameraPhotoUri, "image/*");

        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.grantUriPermission(list.get(0).activityInfo.packageName, cameraPhotoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        if (list.size() == 0) {
            Toast.makeText(context, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
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

            cameraPhotoUri = FileProvider.getUriForFile(context,
                    "com.indicar.indicar_community.provider", tempFile);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }

            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPhotoUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

            Intent intnt = new Intent(intent);
            ResolveInfo res = list.get(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intnt.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intnt.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                context.grantUriPermission(res.activityInfo.packageName, cameraPhotoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intnt.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            context.startActivityForResult(intnt, CROP_IMAGE);
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

}
