package com.indicar.indicar_community.b1_tunning;


import android.support.v4.app.Fragment;

import com.indicar.indicar_community.BaseFragment;
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
