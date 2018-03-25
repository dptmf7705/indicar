package com.indicar.indicar_community.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.indicar.indicar_community.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadTestActivity extends AppCompatActivity {
    private static final int PICK_FROM_ALBUM = 0;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_IMAGE = 2;

    ImageView image;
    EditText etText;
    Button btnAlbum;
    Button btnCamera;
    Button btnUpload;
    String inputText;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_test);

        image = findViewById(R.id.image);
        etText = findViewById(R.id.etText);
        btnAlbum = findViewById(R.id.btnAlbum);
        btnCamera = findViewById(R.id.btnCamera);
        btnUpload = findViewById(R.id.btnUpload);

        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, PICK_FROM_CAMERA);
            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputText = etText.getText().toString();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent;

        if(resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case PICK_FROM_ALBUM:
                /*uri = data.getData();
                intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image*//*");

                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);*/

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case PICK_FROM_CAMERA:
                intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");

                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;

            case CROP_FROM_IMAGE:
                if(resultCode != RESULT_OK){
                    return;
                }

                final Bundle extras = data.getExtras();
                //String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/" + System.currentTimeMillis() + ".jpg";
                if(extras != null){
                    Bitmap bitmap = extras.getParcelable("data");
                    //storeCropImage(bitmap, filePath);

                    image.setImageBitmap(bitmap);
                }

                File file = new File(uri.getPath());
                if(file.exists()){
                    file.delete();
                }
                break;
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;
        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
