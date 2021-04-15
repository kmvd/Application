package com.zhou.demo33333;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhou.demo33333.utils.ACache;

import java.io.File;

import cn.bmob.v3.Bmob;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        File cacheDir = this.getExternalCacheDir();
        if(cacheDir != null){
            ACache.setDefaultCacheDir(cacheDir.getAbsolutePath());
        }
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init( this ); // 尽可能早，推荐在Application中初始化
    }
    @Override
    public void onTerminate() {
        ARouter.getInstance().destroy(); //销毁
        super.onTerminate();
    }
}
