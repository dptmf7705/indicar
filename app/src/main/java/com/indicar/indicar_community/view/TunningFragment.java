package com.indicar.indicar_community.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indicar.indicar_community.R;
import com.indicar.indicar_community.databinding.TunningFragmentBinding;

import java.util.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class TunningFragment extends BaseFragment<TunningFragmentBinding> {


    public TunningFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tunning_fragment;
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
