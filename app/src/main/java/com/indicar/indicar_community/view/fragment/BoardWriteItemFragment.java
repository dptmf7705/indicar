package com.indicar.indicar_community.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.WriteFilterRadio;
import com.indicar.indicar_community.vo.WriteFileAndTextVO;

public class BoardWriteItemFragment extends Fragment {
    private WriteFileAndTextVO vo;
    private ImageView imagePicked;
    private ImageButton buttonAlbum;
    private ImageButton buttonCamera;
    private TextView textWrite;

    public BoardWriteItemFragment() {

    }

    public static BoardWriteItemFragment newInstance(WriteFileAndTextVO vo) {
        Bundle args = new Bundle();

        BoardWriteItemFragment fragment = new BoardWriteItemFragment();
        args.putParcelable("vo", vo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board_write_item, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        imagePicked = view.findViewById(R.id.imagePicked);
        buttonAlbum = view.findViewById(R.id.buttonAlbum);
        buttonCamera = view.findViewById(R.id.buttonCamera);
        textWrite = view.findViewById(R.id.textWrite);

        vo = getArguments().getParcelable("vo");

        setView();

        return view;
    }

    public void setView(){
        if(vo.getBitmap() != null){
            imagePicked.setVisibility(View.VISIBLE);
            imagePicked.setImageBitmap(vo.getBitmap());
            buttonAlbum.setVisibility(View.GONE);
            buttonCamera.setVisibility(View.GONE);
        }else{
            imagePicked.setVisibility(View.GONE);
            buttonAlbum.setVisibility(View.VISIBLE);
            buttonCamera.setVisibility(View.VISIBLE);
        }

        if(vo.getBitmap() != null) {
            textWrite.setText("" + vo.getText());
        }else{
            textWrite.setText("");
        }
    }


}
