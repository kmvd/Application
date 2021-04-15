package com.zhou.demo33333.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;
import com.zhou.demo33333.NewsRecycleAdapter;
import com.zhou.demo33333.R;
import com.zhou.demo33333.activity.HeartRateActivity;
import com.zhou.demo33333.activity.SearchActivity;
import com.zhou.demo33333.activity.WebViewActivity;
import com.zhou.demo33333.base.BaseFragment;
import com.zhou.demo33333.bean.News;
import com.zhou.demo33333.utils.ACache;
import com.zhou.logutils.LogUtil;
import com.zhou.logutils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class HomeFragment extends BaseFragment {

    private final Logger logger = new Logger(HomeFragment.this);

    @BindView(R.id.newsRv)
    RecyclerView recyclerView;

    private View headerView;

    private XBanner mXBanner;

    // 当前显示页（数据分页）
    private int curPage = 0;
    // 一页数据显示量
    private final int pageLimit = 10;
    // 当查询到的数据没有更多时，不再请求下一页
    private boolean noMore = false;
    // 新闻数据 list
    List<News> newsBeans = new ArrayList<>();

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void InitView(View view) {
        super.InitView(view);
        // header view 略有特殊，手动填充和找到控件
        // (使用 黄油刀 自动找控件也是可以的，但那样反而更麻烦了)
        headerView = getLayoutInflater().inflate(R.layout.fragment_home_header, null, false);
        mXBanner = headerView.findViewById(R.id.xbanner);

        headerView.findViewById(R.id.heartRateCl).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this.mActivity, HeartRateActivity.class));
            }
        });
    }

    /**
     * 搜索按钮点击，跳转到独立的搜索页面
     */
//    @OnClick(R.id.searchBg)
    public void searchBtnClick() {
        logger.v("click search btn");
        startActivity(new Intent(HomeFragment.this.getContext(), SearchActivity.class));
    }

    /**
     * 初始化数据，三个轮播图片，使用测试图片：
     * https://picture.zwc365.com/getbing.jpg
     */
    @Override
    protected void InitData() {
        super.InitData();
        // 头部轮播图
        List<BaseBannerInfo> bannerInfos = new ArrayList<>();
        bannerInfos.add(new BannerInfoUrl("https://picture.zwc365.com/getbing.jpg", "图片一"));
        bannerInfos.add(new BannerInfoUrl("https://picture.zwc365.com/getbing.jpg", "图片二"));
        bannerInfos.add(new BannerInfoUrl("https://picture.zwc365.com/getbing.jpg", "图片三"));
        mXBanner.setBannerData(bannerInfos);
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                logger.d("object: " + LogUtil.objToString(model));
                Glide.with(HomeFragment.this).load(((BannerInfoUrl) model).getXBannerUrl())
                        .into((ImageView) view);
            }
        });
        // 搜索框点击
        headerView.findViewById(R.id.searchBg).setOnClickListener(view -> searchBtnClick());

        // 构造列表数据
//        List<News> newsBeans = new ArrayList<>();
//        newsBeans.add(new News());
//        newsBeans.add(new News());
//        newsBeans.add(new News());
//        showRecycleData(newsBeans);

        ArrayList<News> cacheList = null;

        Object cacheData = ACache.getObject("news_key");
        if (cacheData != null) {
            // 缓存工具中存储的数据是 Object 类型，需要将 Object 强转为列表
            // 强转过程进行错误捕获，防止出现意料之外的错误
            try {
                cacheList = (ArrayList<News>) cacheData;
            } catch (Exception e) {
                // 从缓存中获取数据并强转失败
                logger.w("from cache get data faild");
            }
        }
        if(cacheList != null){
            showRecycleData(cacheList);
        }


        // 在页面加载的时候，请求网络数据
        requestData(curPage);
    }

    private void requestNextData() {
        if (noMore) {
            Toast.makeText(HomeFragment.this.mActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(HomeFragment.this.mActivity, "加载数据中,请稍后...", Toast.LENGTH_SHORT).show();
        // 加载下一页
        requestData(++curPage);
    }

    private void requestData(int page) {
        BmobQuery<News> query = new BmobQuery<News>();
        query.setLimit(pageLimit);
        query.setSkip(page);

        query.findObjects(this.mActivity, new FindListener<News>() {
            @Override
            public void onSuccess(List<News> list) {
                logger.d("success get query news:" + LogUtil.objToString(list));
                if (list != null) {
                    // 如果返回的数据条数低于限定值，则说明没有更多数据了。不让它再上拉加载
                    if (list.size() < pageLimit) {
                        noMore = true;
                    }
                    // 如果是第一页，将第一页的数据存储到缓存中，下次打开直接加载缓存数据
                    if (page == 0) {
                        ArrayList<News> cacheList = new ArrayList();
                        cacheList.addAll(list);
                        ACache.putObject("news_key", cacheList);
                    }
                    // 将 Bmob 查询到的数据，追加到 list 中，随后显示出来即可
                    newsBeans.addAll(list);
                    showRecycleData(newsBeans);
                }

            }

            @Override
            public void onError(int i, String s) {
                logger.e("find object error :" + s);
            }
        });
    }

    private void showRecycleData(List<News> data) {
        if (data == null) {
            return;
        }
        logger.d("show data list");
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this.mActivity);
            recyclerView.setLayoutManager(layoutManager);
        }
        NewsRecycleAdapter adapter = (NewsRecycleAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new NewsRecycleAdapter(R.layout.item_news);
            recyclerView.setAdapter(adapter);


            adapter.setOnItemClickListener((adapter1, view, position) -> {
                News newsBean = (News) adapter1.getItem(position);
                logger.d("新闻点击事件:" + LogUtil.objToString(newsBean));
                // 在点击新闻的时候，跳转打开对于的
                if (TextUtils.isEmpty(newsBean.getURL())) {
                    Intent intent = new Intent(this.mActivity, WebViewActivity.class);
                    intent.putExtra("url", "http://www.baidu.com");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this.mActivity, WebViewActivity.class);
                    intent.putExtra("url", newsBean.getURL());
                    startActivity(intent);
                }
            });

            // 添加头部的控件
            adapter.addHeaderView(headerView);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                        /**
                         * 上拉触顶，则重新加载
                         */
                        if (recyclerView.computeVerticalScrollOffset() == 0) {
                            logger.d("上拉触顶");
                        }
                        /**
                         * 下拉触底，加载更多
                         */
                        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
                            logger.d("下拉触底");
                            requestNextData();
                        }
                    }
                }
            });

            adapter.setNewData(data);
        } else {
            adapter.setNewData(data);
        }
    }

    private class BannerInfoUrl implements BaseBannerInfo {

        private String url;
        private String title;

        private BannerInfoUrl(String url, String title) {
            this.url = url;
            this.title = title;
        }

        @Override
        public String getXBannerUrl() {
            return this.url;
        }

        @Override
        public String getXBannerTitle() {
            return this.title;
        }
    }
}
