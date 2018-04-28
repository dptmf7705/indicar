package com.indicar.indicar_community.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.data.vo.BoardWriteVO;
import com.indicar.indicar_community.databinding.BoardWriteItemBinding;
import com.indicar.indicar_community.utils.CustomAlertDialog;
import com.indicar.indicar_community.utils.IPickPhotoHelper;
import com.indicar.indicar_community.utils.PickPhotoHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeseul on 2018-03-25.
 *
 * TODO
 * 1. -> popular, all board 도 마찬가지로. ViewHolder 만 따로만들고 어댑터 하나로 합치기
 *
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//sdk 24 이상, 누가(7.0)
 photoUri = FileProvider.getUriForFile(getApplicationContext(),// 7.0에서 바뀐 부분은 여기다.
 BuildConfig.APPLICATION_ID + ".provider", photoFile);
 } else {//sdk 23 이하, 7.0 미만
 photoUri = Uri.fromFile(photoFile);
 }
 *
 */

public class BoardWriteAdapter extends BaseRecyclerViewAdapter<BoardWriteVO, BoardWriteAdapter.BoardWriteViewHolder> {

    private final int PICK_FROM_ALBUM = 0;
    private final int PICK_FROM_CAMERA = 1;

    public PickPhotoHelper pickPhotoHelper;

    public BoardWriteAdapter(Context context) {
        super(context);
    }

    public BoardWriteAdapter(Context context, PickPhotoHelper pickPhotoHelper) {
        super(context);
        this.pickPhotoHelper = pickPhotoHelper;
    }

    public BoardWriteAdapter(Context context, List<BoardWriteVO> list, PickPhotoHelper pickPhotoHelper) {
        super(context, list);
        this.pickPhotoHelper = pickPhotoHelper;
    }

    @Override
    protected void onBindView(BoardWriteViewHolder holder, int position) {
        final int pos = position;
        holder.binding.setWriteItem(itemList.get(position));
        holder.binding.buttonFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPickPhoto(pos, PICK_FROM_ALBUM);
            }
        });

        holder.binding.buttonFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processPickPhoto(pos, PICK_FROM_CAMERA);
            }
        });

        holder.binding.imageWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog(pos);
            }
        });

        holder.binding.textWrite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                itemList.get(pos).setWriteText(editable.toString());
            }
        });
    }

    public void showPhotoDialog(final int position){

        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final CustomAlertDialog dialog = new CustomAlertDialog(context);
        dialog.setImageId(R.drawable.button_trash)
                .setTitle("사진을 정말로 삭제하시겠습니까?")
                .setSubTitle("delete this photo.")
                .setPositiveButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removePhoto(position);
                        dialog.dismiss();
                    }
                })
                .setSize((int) (size.x * 0.9), (int) (size.y * 0.25))
                .show();

    }

    public void removePhoto(int position){
        itemList.get(position).setImageUrl(null);
        itemList.get(position).setHasImage(false);
        itemList.get(position).setFilePath("");
    }

    @Override
    public BoardWriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.board_write_item, null);
        return new BoardWriteViewHolder(view);
    }

    public class BoardWriteViewHolder extends RecyclerView.ViewHolder {

        BoardWriteItemBinding binding;

        public BoardWriteViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    private void processPickPhoto(final int position, int commend) {

        if(commend == PICK_FROM_ALBUM){

            pickPhotoHelper.pickFromAlbum(new IPickPhotoHelper.loadPhotoListCallBack<Uri>() {
                @Override
                public void onPhotoListLoaded(List<Uri> photoUriList) {

                    final int IMAGE_COUNT = photoUriList.size(); // 리스트 개수

                    ArrayList<BoardWriteVO> list = new ArrayList<>();

                    for(int i = 0 ; i < IMAGE_COUNT ; i++){
                        if(i == 0){
                            itemList.get(position).setImageUrl(photoUriList.get(i));
                            itemList.get(position).setHasImage(true);
                            itemList.get(position).setFilePath(getRealPathFromURI(photoUriList.get(i)));
                            continue;
                        }
                        BoardWriteVO vo = new BoardWriteVO();
                        vo.setImageUrl(photoUriList.get(i));
                        vo.setHasImage(true);
                        vo.setFilePath(getRealPathFromURI(photoUriList.get(i)));
                        list.add(vo);
                    }

                    addItems(position + 1, list);
                }

                @Override
                public void onPhotoNotAvailable() {

                }
            });

        } else if(commend == PICK_FROM_CAMERA){

            pickPhotoHelper.pickFromCamera(new IPickPhotoHelper.loadPhotoCallBack<Uri>() {
                @Override
                public void onPhotoLoaded(Uri photoUri) {

                    itemList.get(position).setImageUrl(photoUri);
                    itemList.get(position).setHasImage(true);
                }

                @Override
                public void onPhotoNotAvailable() {

                }
            });
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
//        CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj, null, null, null);
//        Cursor cursor = cursorLoader.loadInBackground();
        Cursor cursor = ((Activity)context).managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

}
