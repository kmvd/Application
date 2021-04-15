package com.zhou.demo33333.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zhou.demo33333.BaseActivity;
import com.zhou.demo33333.R;

@Route(path = "/app/HeartRateActivity") //注解表示该页面的路由路径，需要跳转页面则增加
public class HeartRateActivity extends BaseActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        btn = (Button) findViewById(R.id.autoInputHeartRateBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转需要指定地址
                ARouter.getInstance().build("/heartrate/MainActivity")
                        //.withString("names", "123")//传递参数
                        .navigation();
            }
        });
    }
}