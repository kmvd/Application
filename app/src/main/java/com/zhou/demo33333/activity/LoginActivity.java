package com.zhou.demo33333.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhou.demo33333.BaseActivity;
import com.zhou.demo33333.R;
import com.zhou.demo33333.utils.PreUtils;
import com.zhou.logutils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/app/LoginActivity") //注解表示该页面的路由路径，需要跳转页面则增加
public class LoginActivity extends BaseActivity {


    @BindView(R.id.userName)
    EditText userName;

    @BindView(R.id.userPwd)
    EditText userPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void InitView() {
        super.InitView();
        // 将保存的用户名及密码还原到界面上
        userName.setText(PreUtils.get(this, "user_name", ""));
        userPwd.setText(PreUtils.get(this, "user_passwd", ""));
    }

    @OnClick(R.id.loginBtn)
    public void loginBtnClick() {
        LogUtil.d("登录按钮点击事件...");
        String userNameStr = userName.getText().toString();
        String userPwdStr = userPwd.getText().toString();
        if (TextUtils.isEmpty(userNameStr) || TextUtils.isEmpty(userPwdStr)) {
            Toast.makeText(this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
            return;
        }
        PreUtils.put(this, "user_name", userNameStr);
        PreUtils.put(this, "user_passwd", userPwdStr);


        // 用户输入账户名及密码后，存储到本地数据中，同时跳转到主页面
        startActivity(new Intent(this, MainActivity.class));
        // 关闭登录页面，同时移除 task ，使其不在页面堆栈中显示
        finish();
    }
}