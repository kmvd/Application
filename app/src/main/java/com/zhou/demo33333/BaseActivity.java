package com.zhou.demo33333;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
//        ScreenUtil.setFullScreen(this);
        super.setContentView(layoutResID);
        init();
    }

    @Override
    public void setContentView(View view) {
//        ScreenUtil.setFullScreen(this);
        super.setContentView(view);
        init();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
//        ScreenUtil.setFullScreen(this);
        super.setContentView(view, params);
        init();
    }

    /**
     * 初始化模块或者组件，如 log ，okhttp 等
     */
    protected void InitModule() {
    }

    /**
     * 初始化 view
     */
    protected void InitView() {
    }

    /**
     * 获取数据等
     */
    protected void InitData() {
    }

    private void init() {
        ButterKnife.bind(this);

        InitModule();
        InitView();
        InitData();

    }
}
