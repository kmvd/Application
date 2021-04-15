package com.zhou.demo33333.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhou.logutils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    @SuppressWarnings("FieldCanBeLocal")
    private static final String TAG = "BaseFragment";

    protected View view;

    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.v(TAG, String.format("current create view fragment:%s", getClass()));
        view = inflater.inflate(this.getLayoutRes(), container, false);

        init();
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.v(TAG, String.format("fragment destroy view:%s", this.getClass()));
        unbinder.unbind();

        view = null;
    }

    private void init() {
        unbinder = ButterKnife.bind(this, view);

        InitModule();
        InitView(view);
        InitData();
    }

    /**
     * 初始化模块或者组件，如 log ，okhttp 等
     */
    protected void InitModule() {
    }

    /**
     * 初始化 view
     */
    protected void InitView(View view) {
    }

    /**
     * 获取数据等
     */
    protected void InitData() {
    }

    /**
     * 子fragment 返回这个 fragment的 layout id，由父类统一实现填充加载
     *
     * @return 返回布局
     */
    public abstract int getLayoutRes();

}
