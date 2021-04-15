package com.zhou.demo33333;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhou.demo33333.bean.RecordData;

import java.util.List;

public class RecordRecycleAdapter extends BaseQuickAdapter<RecordData, BaseViewHolder> {

    public RecordRecycleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(BaseViewHolder helper, RecordData item) {
        //TODO 实现数据填充
        if (!TextUtils.isEmpty(item.getBgColor()) && item.getBgColor().startsWith("#")) {
            helper.itemView.setBackgroundColor(Color.parseColor(item.getBgColor()));
        } else {
            helper.itemView.setBackgroundColor(Color.parseColor("#64CA6A"));
        }
        // 勾选框
        if (item.getSelectType() == 1) {
            helper.setImageResource(R.id.recordSelectIv, R.drawable.checked);
        } else {
            helper.setImageResource(R.id.recordSelectIv, R.drawable.checked);
        }
        //文本内容设定
        if (TextUtils.isEmpty(item.getType())) {
            ViewGroup.LayoutParams layoutparams = helper.itemView.getLayoutParams();
            layoutparams.height = 20;
            helper.itemView.setLayoutParams(layoutparams);
            helper.itemView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
//            helper.itemView.setBackgroundColor(R.color.green);
        } else {
            helper.setText(R.id.recordTv, item.getType());
        }
    }
}
