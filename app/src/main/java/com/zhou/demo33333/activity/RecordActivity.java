package com.zhou.demo33333.activity;

import android.os.Bundle;
import android.util.Base64;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhou.demo33333.BaseActivity;
import com.zhou.demo33333.R;
import com.zhou.demo33333.RecordRecycleAdapter;
import com.zhou.demo33333.bean.RecordData;
import com.zhou.logutils.LogUtil;
import com.zhou.logutils.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;

import butterknife.BindView;

public class RecordActivity extends BaseActivity {

    private final Logger logger = new Logger(RecordActivity.this);

    @BindView(R.id.recordList)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        String testStr = new String(Base64.encode("abcdefg".getBytes(), Base64.NO_WRAP));
        String testStr2 = new String(Base64.encode("abcdefg".getBytes(), Base64.DEFAULT));
        try {
            LogUtil.d(testStr);
            LogUtil.d(testStr2);
            LogUtil.d(URLEncoder.encode(testStr, "utf-8"));
            LogUtil.d(URLEncoder.encode(testStr2, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void InitData() {
        super.InitData();
        LinkedList<RecordData> linkedList = new LinkedList<>();
        RecordData recordData1 = new RecordData();
        recordData1.setType("每日打卡");

        RecordData recordData2 = new RecordData();
        recordData2.setType("多饮水");
        recordData2.setBgColor("#5CB4F1");

        RecordData recordData3 = new RecordData();
        recordData3.setType("忌口");
        recordData3.setBgColor("#5CB4F1");

        RecordData recordData4 = new RecordData();
        recordData4.setType("轻度运动");
        recordData4.setBgColor("#5CB4F1");

        RecordData recordData5 = new RecordData();
        recordData5.setType("好心情");
        recordData5.setBgColor("#5CB4F1");

        RecordData recordData6 = new RecordData();
        recordData6.setType("饮酒");
        recordData6.setBgColor("#FC6E7B");

        RecordData recordData7 = new RecordData();
        recordData7.setType("暴食");
        recordData7.setBgColor("#FC6E7B");

        RecordData recordData8 = new RecordData();
        recordData8.setType("疲劳");
        recordData8.setBgColor("#FC6E7B");

        RecordData recordData9 = new RecordData();
        recordData9.setType("剧烈运动");
        recordData9.setBgColor("#FC6E7B");

        RecordData recordData10 = new RecordData();
        recordData10.setType("坏心情");
        recordData10.setBgColor("#FC6E7B");

        RecordData nullData = new RecordData();
        RecordData nullData2 = new RecordData();
        nullData.setType("");
        nullData2.setType("");

        linkedList.add(recordData1);
        linkedList.add(nullData);

        linkedList.add(recordData2);
        linkedList.add(recordData3);
        linkedList.add(recordData4);
        linkedList.add(recordData5);
        linkedList.add(nullData2);

        linkedList.add(recordData6);
        linkedList.add(recordData7);
        linkedList.add(recordData8);
        linkedList.add(recordData9);
        linkedList.add(recordData10);

        showRecycleData(linkedList);
    }

    private void showRecycleData(LinkedList<RecordData> data) {
        if (data == null) {
            return;
        }
        logger.d("show data list");
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }
        RecordRecycleAdapter adapter = (RecordRecycleAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new RecordRecycleAdapter(R.layout.item_record);
            recyclerView.setAdapter(adapter);
            adapter.setNewData(data);

            adapter.setOnItemClickListener((adapter1, view, position) -> {
                logger.d("健康日志点击事件：" + position);
                logger.d("健康日志点击事件：" + LogUtil.objToString(view));
            });
//            recyclerView.addons
        } else {
//            adapter.setHeaderAndEmpty();
            adapter.setNewData(data);
        }
    }
}