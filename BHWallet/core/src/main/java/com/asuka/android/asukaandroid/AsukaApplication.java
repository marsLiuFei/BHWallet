package com.asuka.android.asukaandroid;

import android.app.Application;
import android.content.Context;

import com.asuka.android.asukaandroid.comm.AppManager;
import com.asuka.android.asukaandroid.comm.Constants;
import com.asuka.android.asukaandroid.exception.EgojitUncaughtExceptionHandler;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.lang.reflect.Method;

/**
 * Author:Asuka
 * Time:2016-9-9
 * Mask:基础Application
 */
public class AsukaApplication extends Application {

//    private static AsukaApplication instances;
//
//
//    public static AsukaApplication getInstances(){
//        return instances;
//    }

    public AsukaApplication(){
        super();
    }

    private static AppManager appManager;

    @Override
    public void onCreate() {
        super.onCreate();
        appManager=AppManager.getAppManager();
        // 注册全局异常处理
        EgojitUncaughtExceptionHandler.getInstance().init(this);
//        this.instances=this;
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .defaultDisplayImageOptions(defaultOptions)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(this, Constants.APP_IMAGE)))
                .diskCacheSize(100 * 1024 * 1024).tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)).memoryCacheSize(2 * 1024 * 1024)
                .threadPoolSize(3)
                .build();
        ImageLoader.getInstance().init(config);
        
    }


    public AppManager getAppManager(){
        return appManager;
    }


    public AsukaApplication(Context context){
        this.attachBaseContext(context);
    }



    public  void OnUncaughtException(Throwable ex){};



}
