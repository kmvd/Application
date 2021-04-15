package com.zhou.demo33333.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.zhou.demo33333.BaseActivity;
import com.zhou.demo33333.FragmentAdapter;
import com.zhou.demo33333.R;
import com.zhou.demo33333.base.BaseFragment;
import com.zhou.demo33333.fragment.HomeFragment;
import com.zhou.demo33333.fragment.InfoFragment;
import com.zhou.demo33333.fragment.MeFragment;
import com.zhou.demo33333.utils.PreUtils;
import com.zhou.logutils.Logger;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;
import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {


    private final Logger logger = new Logger(MainActivity.class.getSimpleName());

    @BindView(R.id.fragmentViewPage)
    ViewPager viewPager;

    @BindView(R.id.barView)
    MagicIndicator barView;


//    @BindView(R.id.logoImg)
//    ImageView logoImg;
//
//    @BindView(R.id.messageBtn)
//    ImageView messageBtn;

//    @BindView(R.id.main_activity_top_view)
//    View topView;

    private AlertDialog networkTipDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化 Bmob
        Bmob.initialize(this, "8de4d71f9c49cbed0d920ae51ed2e6c8");

//        PreUtils.put(this, "user_passwd", "");
        // 打开首页的时候，检查用户是否登录，如果未登录，则跳转到登录页面
        // 此种处理逻辑并不推荐使用。仅为了开发效率而已
        if (TextUtils.isEmpty(PreUtils.get(this, "user_name", ""))
                || TextUtils.isEmpty(PreUtils.get(this, "user_passwd", ""))) {
            startActivity(new Intent(this, LoginActivity.class));
//            finishAndRemoveTask();
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void InitModule() {
        super.InitModule();
    }

    @Override
    protected void InitData() {
        ArrayList<Class<? extends BaseFragment>> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.class);
        fragmentList.add(InfoFragment.class);
        fragmentList.add(MeFragment.class);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));
//        给 底部bar 绑定 view page
//        barView.setViewPager(viewPager);
        CommonNavigator commonNavigator = new CommonNavigator(MainActivity.this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                logger.d("create title view,index:" + index);
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

                final View view = getLayoutInflater().inflate(R.layout.bar_item_view_layout, null, false);

                final ImageView defImg = view.findViewById(R.id.bar_item_def_img);
                final ImageView showImg = view.findViewById(R.id.bar_item_show_img);
//                switch (index) {
//                    case 0:
//                        defImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.home_default));
//                        showImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.home_select));
//                        break;
//                    case 1:
//                        defImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.bed_default));
//                        showImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.bed_select));
//                        break;
//                    case 2:
//                        defImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.doctor_default));
//                        showImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.doctor_select));
//                        break;
//                    case 3:
//                        defImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.surgery_default));
//                        showImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.surgery_select));
//                        break;
//                    case 4:
//                        defImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.remark_default));
//                        showImg.setImageDrawable(ContextCompat.getDrawable(com.example.hospitalscreen.activity.MainActivity.this, R.drawable.remark_select));
//                        break;
//                }

                commonPagerTitleView.setContentView(view);
                commonPagerTitleView.setOnClickListener(v -> {
                    logger.d("bar view click,index:" + index);
                    if (viewPager != null) {
                        viewPager.setCurrentItem(index, true);
                    } else {
                        logger.i("view page is null,can not to :" + index);
                    }
                });
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        logger.d("on select ,index:" + index);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        logger.d("onDeselected ,index:" + index);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                        Drawable image = defImg.getDrawable();
                        image.mutate().setAlpha((int) ((leavePercent * 255)));
                        defImg.setImageDrawable(image);

                        image = showImg.getDrawable();
                        image.mutate().setAlpha((int) (255 - (leavePercent * 255)));
                        showImg.setImageDrawable(image);

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                        Drawable image = showImg.getDrawable();
                        image.mutate().setAlpha((int) ((enterPercent * 255)));
                        showImg.setImageDrawable(image);

                        image = defImg.getDrawable();
                        image.mutate().setAlpha((int) (255 - (enterPercent * 255)));
                        defImg.setImageDrawable(image);
                    }
                });
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });

        barView.setNavigator(commonNavigator);
        ViewPagerHelper.bind(barView, viewPager);

//        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void InitView() {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

}
