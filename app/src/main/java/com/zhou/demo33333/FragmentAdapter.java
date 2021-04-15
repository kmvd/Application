package com.zhou.demo33333;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zhou.demo33333.base.BaseFragment;
import com.zhou.logutils.LogUtil;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private final String TAG = "FragmentAdapter";

    private List<Class<? extends BaseFragment>> fragments;

    @SuppressWarnings("WeakerAccess")
    public FragmentAdapter(FragmentManager fm
            , List<Class<? extends BaseFragment>> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        try {
            return fragments.get(i).newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            LogUtil.e("get fragment error:" + LogUtil.objToString(e));
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
