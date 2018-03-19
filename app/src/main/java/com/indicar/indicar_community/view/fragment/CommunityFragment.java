package com.indicar.indicar_community.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.utils.BoardListAdapter;
import com.indicar.indicar_community.view.activity.BoardWriteActivity;
import com.indicar.indicar_community.view.activity.BoardWriteFilterActivity;
import com.indicar.indicar_community.vo.BoardVO;

import java.util.ArrayList;

public class CommunityFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView btnFilter;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        context = view.getContext();

        recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);

        ArrayList<BoardVO> items = new ArrayList<>();
        items.add(new BoardVO(R.drawable.car_img_1, "DIY", "미키마우스", "하지만 잠재적인 브라우저의 구문 분석 오류를 해결 합니다. 자세한 내용은", 32, 10));
        items.add(new BoardVO(R.drawable.car_img_2, "DIY", "인어공주", "화면을 만들었으니 화면에 출력할 데이터를 담을 객체를 생성합니다.", 23, 3));
        items.add(new BoardVO(R.drawable.car_img_3, "DIY", "디즈니공주", "이미지와 제목으로 이루어진 앨범을 만드려고 합니다.", 36, 3));
        items.add(new BoardVO(R.drawable.car_img_4, "DIY", "토이스토리", "위에서 작성한 RecycleView에 들어갈 Item Layout을 작성해야합니다.", 2, 7));
        items.add(new BoardVO(R.drawable.car_img_5, "DIY", "니모를 찾아서", "필자는 그림과 제목을 넣었지만 원하는 형태로 넣어주셔도 됩니다. ", 23, 42));
        items.add(new BoardVO(R.drawable.car_img_6, "DIY", "피카추", "하지만 잠재적인 브라우저의 구문 분석 오류를 해결 합니다. 자세한 내용은", 85, 87));
        items.add(new BoardVO(R.drawable.car_img_7, "DIY", "파이리", "화면을 만들었으니 화면에 출력할 데이터를 담을 객체를 생성합니다.", 54, 6));
        items.add(new BoardVO(R.drawable.car_img_8, "DIY", "뚱이", "이미지와 제목으로 이루어진 앨범을 만드려고 합니다.", 21, 54));
        items.add(new BoardVO(R.drawable.car_img_9, "DIY", "보라돌이", "필자는 그림과 제목을 넣었지만 원하는 형태로 넣어주셔도 됩니다.", 8, 3));

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BoardListAdapter(context, items);
        recyclerView.setAdapter(adapter);

        ImageView iv_write_board = view.findViewById(R.id.iv_write_board);
        iv_write_board.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(context, BoardWriteFilterActivity.class);
                    context.startActivity(intent);
                }
                return true;
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //////////////////// 이거 안됨.........!!!!!!!!!!!!! ////////////////////
                Toast.makeText(context, "filter", Toast.LENGTH_LONG);
            }
        });

        return view;
    }

    public void setBtnFilter(ImageView btnFilter) {
        this.btnFilter = btnFilter;
    }

}
