package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.databinding.BoardAllItemBinding;
import com.indicar.indicar_community.model.vo.BoardVO;

import java.util.List;

/**
 * Created by yeseul on 2018-04-17.
 */

public class BoardAllAdapter extends BaseRecyclerViewAdapter<BoardVO, BoardAllAdapter.BoardAllViewHolder>{

    public BoardAllAdapter(Context context) {
        super(context);
    }

    public BoardAllAdapter(Context context, List<BoardVO> list){
        super(context, list);
    }

    @Override
    protected void onBindView(BoardAllViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public class BoardAllViewHolder extends BaseViewHolder<BoardAllItemBinding>{

        public BoardAllViewHolder(View itemView) {
            super(itemView);
        }
    }

}
