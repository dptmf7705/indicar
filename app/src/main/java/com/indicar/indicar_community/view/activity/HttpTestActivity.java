package com.indicar.indicar_community.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.HTTPClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class HttpTestActivity extends AppCompatActivity {
    String bbs_id;
    String ntt_id;
    String atch_file_id;
    TextView textView;
    Button btn;
    int cnt = 0;
    int len = 0;
    StringBuffer[] files;
    FileOutputStream outputStream;
    File file = new File("/download/img.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        textView = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (cnt){
                    case 0:
                        HTTPClient.get("/selectBoardList", null, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                String result = new String(bytes);
                                try {
                                    JSONArray array = new JSONArray(result);
                                    JSONObject json = array.getJSONObject(0);
                                    textView.setText(json.toString());
                                    bbs_id = json.getString("bbs_id");
                                    btn.setText(bbs_id);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                cnt++;
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        });
                        break;
                    case 1:
                        RequestParams params = new RequestParams();
                        params.put("bbs_id", bbs_id);
                        params.put("searchCnd", "");
                        HTTPClient.post("/selectBoardArticles", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                String result = new String(bytes);
                                try {
                                    JSONArray array = new JSONArray(result);
                                    JSONObject json = array.getJSONObject(0);
                                    textView.setText(json.toString());
                                    ntt_id = json.getString("ntt_id");
                                    btn.setText(ntt_id);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                cnt++;
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        });
                        break;
                    case 2:
                        RequestParams params2 = new RequestParams();
                        params2.put("ntt_id", ntt_id);
                        params2.put("bbs_id", bbs_id);
                        HTTPClient.post("/selectBoardArticle", params2, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                String result = new String(bytes);
                                try {
                                    JSONObject json = new JSONObject(result);
                                    textView.setText(json.toString());
                                    atch_file_id = json.getString("atch_file_id");
                                    btn.setText(atch_file_id);
                                    cnt++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        });
                        break;
                    case 3:
                        RequestParams params1 = new RequestParams();
                        params1.put("ntt_id", ntt_id);
                        params1.put("bbs_id", bbs_id);
                        HTTPClient.post("/selectCommentList", params1, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                String result = new String(bytes);
                                textView.setText(result);
                                /*
                                board = new StringBuffer("");
                                try {
                                    JSONArray array = new JSONArray(result);
                                    for(int j = 0; j < array.length(); j++){
                                        board.append(array.getJSONObject(j) + "\n");
                                    }
                                    textView.setText(board.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
                                cnt++;
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        });
                        break;
                    case 4:
                        RequestParams params3 = new RequestParams("atch_file_id", atch_file_id);
                        HTTPClient.post("/selectFileInfs", params3, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                String result = new String(bytes);
                                try {
                                    JSONArray array = new JSONArray(result);
                                    StringBuffer text = new StringBuffer("");
                                    len = array.getInt(array.length() - 1);
                                    files = new StringBuffer[len];
                                    textView.setText(len+"\n");
                                    for(int j = 0 ; j < len; j++) {
                                        JSONObject json = array.getJSONObject(j);
                                        files[j] = new StringBuffer(json.getString("file_stre_cours") + "\n");
                                        files[j].delete(0,24);
                                        textView.append(files[j].toString());
                                    }
                                    cnt++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        });
                        break;
                    case 5:
                        LinearLayout linear = findViewById(R.id.linear);
                        final ImageView[] imageViews = new ImageView[len];
                        for(int j = 0 ; j < len ; j++){
                            final int k = j;
                            imageViews[j] = new ImageView(HttpTestActivity.this);
                            imageViews[j].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,500));
                            imageViews[j].setAdjustViewBounds(true);
                            imageViews[j].setScaleType(ImageView.ScaleType.CENTER_CROP);
                            linear.addView(imageViews[j]);

                            HTTPClient.get(files[j].toString(), null, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] fileData) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
                                    if(bitmap != null) {
                                        imageViews[k].setImageBitmap(bitmap);
                                    }
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                }
                            });
                        }

                        break;
                }

            }
        });
    }
}
