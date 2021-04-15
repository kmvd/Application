package com.zhou.demo33333.fragment;

import android.content.Intent;

import com.zhou.demo33333.R;
import com.zhou.demo33333.activity.RecordActivity;
import com.zhou.demo33333.base.BaseFragment;
import com.zhou.logutils.Logger;

import butterknife.OnClick;

public class InfoFragment extends BaseFragment {

    private final Logger logger = new Logger(InfoFragment.this);

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_info;
    }

    @OnClick(R.id.habitView)
    public void enterNext() {
        logger.d("进入下一个页面，列表");
//        Toast.makeText(this.mActivity, "当前功能尚未实现", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(InfoFragment.this.mActivity, RecordActivity.class));
    }
}
