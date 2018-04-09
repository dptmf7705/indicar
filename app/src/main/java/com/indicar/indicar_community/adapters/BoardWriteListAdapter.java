package com.indicar.indicar_community.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.view.fragment.BoardWriteItemFragment;
import com.indicar.indicar_community.vo.BoardWriteVO;
import com.indicar.indicar_community.vo.WriteFileAndTextVO;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.indicar.indicar_community.utils.Constants.PICK_FROM_ALBUM;
import static com.indicar.indicar_community.utils.Constants.PICK_FROM_CAMERA;
import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by yeseul on 2018-03-25.
 *
 * TODO
 * 1. ViewHolder 클래스 파일 따로 빼기
 * 2. -> popular, all board 도 마찬가지로. ViewHolder 만 따로만들고 어댑터 하나로 합치기
 *
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//sdk 24 이상, 누가(7.0)
 photoUri = FileProvider.getUriForFile(getApplicationContext(),// 7.0에서 바뀐 부분은 여기다.
 BuildConfig.APPLICATION_ID + ".provider", photoFile);
 } else {//sdk 23 이하, 7.0 미만
 photoUri = Uri.fromFile(photoFile);
 }
 *
 */

public class BoardWriteListAdapter extends RecyclerView.Adapter<BoardWriteListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<BoardWriteVO> items;  // write item list
    private int layoutSrc; // laout source
    private String mCurrentPhotoPath;
    private Uri photoUri;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_FROM_CAMERA = 3;

    public BoardWriteListAdapter(Context context, int layoutSrc){
        this.context = context;
        this.layoutSrc = layoutSrc;
        items = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutSrc, null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BoardWriteVO item = items.get(position);
        Bitmap bitmap = item.getBitmap();
        if(bitmap != null){
            holder.buttonCamera.setVisibility(View.GONE);
            holder.buttonAlbum.setVisibility(View.GONE);
            holder.imagePicked.setVisibility(View.VISIBLE);
            holder.imagePicked.setImageBitmap(item.getBitmap());
        } else {
            holder.buttonCamera.setVisibility(View.VISIBLE);
            holder.buttonCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //takePhoto();
                }
            });
            holder.buttonAlbum.setVisibility(View.VISIBLE);
            holder.buttonAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //goToAlbum();
                }
            });
            holder.imagePicked.setVisibility(View.GONE);
            holder.imagePicked.setImageBitmap(bitmap);
        }

        if(item.getText() != null) {
            holder.textWrite.setText("" + item.getText());
        }
    }
/*

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "indicar_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/indicar/");
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
            Toast.makeText(context, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            ((Activity)context).finish();
            e.printStackTrace();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(context,
                    "com.indicar.indicar_community.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            ((Activity)context).startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        ((Activity)context).startActivityForResult(intent, PICK_FROM_ALBUM);
    }
*/


    @Override
    public int getItemCount() {
        if(items != null){
            return  items.size();
        } else{
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton buttonAlbum;
        public ImageButton buttonCamera;
        public ImageButton imagePicked;
        public EditText textWrite;
        public View view;

        public ViewHolder(View view) {
            super(view);
            buttonAlbum = view.findViewById(R.id.buttonAlbum);
            buttonCamera = view.findViewById(R.id.buttonCamera);
            imagePicked = view.findViewById(R.id.imagePicked);
            textWrite = view.findViewById(R.id.textWrite);
            this.view = view;
        }
    }

    public void addItem(int index, BoardWriteVO item){
        items.add(index, item);
        notifyItemInserted(index);
    }

    public void removeItem(int index){
        items.remove(index);
        notifyItemRemoved(index);
    }

    public void setImagePicked(int index, Bitmap bitmap){
        items.get(index).setBitmap(bitmap);
        notifyItemChanged(index);
    }

}
