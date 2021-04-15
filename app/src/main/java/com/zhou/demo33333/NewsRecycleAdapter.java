package com.zhou.demo33333;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhou.demo33333.bean.News;

public class NewsRecycleAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
    public NewsRecycleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        helper.setText(R.id.newsTitleTv, item.getTitle())
                .setText(R.id.newsInfoTv, item.getKey1());
    }
}
