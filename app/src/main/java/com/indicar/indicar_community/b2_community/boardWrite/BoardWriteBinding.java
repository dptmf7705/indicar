package com.indicar.indicar_community.b2_community.boardWrite;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.indicar.indicar_community.adapter.BoardWriteAdapter;
import com.indicar.indicar_community.data.vo.BoardWriteVO;

import java.util.List;

/**
 * Created by yeseul on 2018-04-29.
 */

public class BoardWriteBinding {

    @BindingAdapter(value = {"items"})
    public static void setItems(RecyclerView recyclerView,
                                List<BoardWriteVO> list){

        BoardWriteAdapter adapter = (BoardWriteAdapter) recyclerView.getAdapter();
        if( adapter != null){
            adapter.updateItems(list);
        }
    }

    @BindingAdapter(value = {"totalPage"})
    public static void totalPageBinding(TextView textView,
                                        List<BoardWriteVO> list){

        if(list != null) {
            textView.setText("" + list.size());
        }
    }

    @BindingAdapter(value = {"pageChange"})
    public static void setPageChangeListener(final RecyclerView recyclerView,
                                             final BoardWriteViewModel viewModel){

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                viewModel.onScrollChanged(recyclerView, newState);
            }
        });

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                viewModel.getCurrentItem(recyclerView.getLayoutManager());
            }
        });
    }
}
