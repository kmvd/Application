package com.zhou.demo33333.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zhou.demo33333.R;
import com.zhou.demo33333.activity.LoginActivity;
import com.zhou.demo33333.base.BaseFragment;
import com.zhou.demo33333.utils.PreUtils;
import com.zhou.logutils.Logger;


import butterknife.BindView;
import butterknife.OnClick;

public class MeFragment extends BaseFragment {

    private final Logger logger = new Logger(MeFragment.class.getSimpleName());

    @BindView(R.id.avatorImg)
    ImageView avatorIv;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    protected void InitView(View view) {
        /**
         * 加载头像，这里的链接是用于测试的，后续需要替换
         */
        Glide.with(this).load("https://picture.zwc365.com/getbing.jpg")
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(avatorIv);
    }

    @OnClick(R.id.logoutLl)
    public void logoutClick() {
        logger.d("用户登出");

        // 登出时，直接清空密码，然后重新跳转到登录页面即可
        PreUtils.put(this.mActivity, "user_passwd", "");
        startActivity(new Intent(this.mActivity, LoginActivity.class));
//            finishAndRemoveTask();
        if (this.mActivity != null) {
            this.mActivity.finish();
        }
    }
}
