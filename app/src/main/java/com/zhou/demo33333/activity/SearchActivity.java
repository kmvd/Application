package com.zhou.demo33333.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhou.demo33333.BaseActivity;
import com.zhou.demo33333.R;
import com.zhou.logutils.Logger;


import butterknife.BindView;
import butterknife.OnEditorAction;

public class SearchActivity extends BaseActivity {

    private final Logger logger = new Logger(SearchActivity.this);

    @BindView(R.id.searchEt)
    EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void InitView() {

//        searchInput.setOnEditorActionListener();
    }

    @OnEditorAction(R.id.searchEt)
    public boolean searchEnter(TextView tv, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            logger.d("点击搜索按钮");
            Toast.makeText(this, "搜索功能暂未实现：" + searchInput.getText().toString(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}