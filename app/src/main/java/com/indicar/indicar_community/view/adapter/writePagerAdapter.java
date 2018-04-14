package com.indicar.indicar_community.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.model.vo.WriteFileAndTextVO;

import java.util.ArrayList;

/**
 * Created by yeseul on 2018-03-25.
 */

public class writePagerAdapter extends PagerAdapter {

    private ArrayList<WriteFileAndTextVO> list;

    public writePagerAdapter(Context context, ArrayList<WriteFileAndTextVO> list) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;

        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
