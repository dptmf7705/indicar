package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.BoardDetailItemBinding;
import com.indicar.indicar_community.model.vo.BoardFileVO;

import java.util.List;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BoardDetailAdapter extends BaseRecyclerViewAdapter<BoardFileVO, BoardDetailAdapter.BoardDetailViewHolder> {

    private static final String TAG = BoardDetailAdapter.class.getSimpleName();

    public BoardDetailAdapter(Context context){
        super(context);
    }

    public BoardDetailAdapter(Context context, List<BoardFileVO> list){
        super(context, list);
    }

    @Override
    protected void onBindView(BoardDetailViewHolder holder, int position) {
        holder.binding.setBoardItem(itemList.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.board_detail_item, null);
        return new BoardDetailViewHolder(view);
    }

    public class BoardDetailViewHolder extends BaseViewHolder<BoardDetailItemBinding> {

        public BoardDetailViewHolder(View itemView) {
            super(itemView);
        }
    }

}
