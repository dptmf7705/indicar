package com.indicar.indicar_community.adapter;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.data.vo.BoardDetailVO;
import com.indicar.indicar_community.data.vo.BoardFileVO;
import com.indicar.indicar_community.databinding.BoardDetailHeaderBinding;
import com.indicar.indicar_community.databinding.BoardDetailItemBinding;

import java.util.List;

/**
 * Created by yeseul on 2018-04-13.
 */

public class BoardDetailAdapter extends BaseRecyclerViewAdapter<BoardFileVO, RecyclerView.ViewHolder> {

    private static final String TAG = BoardDetailAdapter.class.getSimpleName();

    public final ObservableField<BoardDetailVO> boardHeader = new ObservableField<>();

    private static final int BOARD_HEADER = 0;
    private static final int BOARD_ITEM = 1;
    private OnMenuClickListener onMenuClickListener;
    private OnUpdateClickListener onUpdateClickListener;

    public BoardDetailAdapter(Context context, BoardDetailVO header){
        super(context);
        this.boardHeader.set(header);
    }

    public BoardDetailAdapter(Context context, BoardDetailVO header, List<BoardFileVO> list){
        super(context, list);
        this.boardHeader.set(header);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return BOARD_HEADER;

        else
            return BOARD_ITEM;
    }

    @Override
    protected void onBindView(final RecyclerView.ViewHolder holder, int position) {

        final int pos = position;

        if(holder instanceof BoardItemViewHolder){

            ((BoardItemViewHolder)holder).binding.setBoardItem(itemList.get(position));

//            ((BoardItemViewHolder) holder).binding.buttonUpdate.setOnClickListener();

        } else if(holder instanceof BoardHeaderViewHolder){

            ((BoardHeaderViewHolder) holder).binding.setBoard(boardHeader.get());
            ((BoardHeaderViewHolder) holder).binding.buttonMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(onMenuClickListener != null){
                        onMenuClickListener.onMenuClick(view, pos);
                    }

                }
            });

            if(itemList != null && itemList.size() > 0) {
                ((BoardHeaderViewHolder) holder).binding.setItem(itemList.get(0));
            }
        }
    }
    
    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener){
        this.onMenuClickListener = onMenuClickListener;
    }

    public interface OnMenuClickListener{

        void onMenuClick(View view, int position);
    }

    public void setOnUpdateClickListener(OnUpdateClickListener OnUpdateClickListener){
        this.onUpdateClickListener = OnUpdateClickListener;
    }

    public interface OnUpdateClickListener{

        void onUpdateClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == BOARD_HEADER){
            View view = LayoutInflater.from(context).inflate(R.layout.board_detail_header, null);
            return new BoardHeaderViewHolder(view);

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.board_detail_item, null);
            return new BoardItemViewHolder(view);

        }
    }


    public class BoardItemViewHolder extends BaseViewHolder<BoardDetailItemBinding> {

        public BoardItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class BoardHeaderViewHolder extends BaseViewHolder<BoardDetailHeaderBinding> {

        public BoardHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

}
