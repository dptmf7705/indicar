package com.indicar.indicar_community.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.view.fragment.BoardWriteItemFragment;
import com.indicar.indicar_community.vo.WriteFileAndTextVO;

import java.util.ArrayList;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by yeseul on 2018-03-25.
 */

public class BoardWritePagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private ArrayList<WriteFileAndTextVO> list;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_board_write_item, null);
        ((ViewPager)container).addView(view);

        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public BoardWritePagerAdapter(Context context, FragmentManager fm, ArrayList<WriteFileAndTextVO> list) {
        super(fm);
        this.context = context;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        BoardWriteItemFragment fragment = BoardWriteItemFragment.newInstance(list.get(position));
        Bundle args = fragment.getArguments();
        WriteFileAndTextVO vo = args.getParcelable("vo");

        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public ArrayList<WriteFileAndTextVO> getList() {
        return list;
    }

    public void setList(ArrayList<WriteFileAndTextVO> list) {
        this.list = list;
    }

}
