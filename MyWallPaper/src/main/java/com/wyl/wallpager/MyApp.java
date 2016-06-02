package com.wyl.wallpager;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;


/**
 * Created by Administrator on 2016/6/2.
 */
public class MyApp extends Application {
    private ImageLoader loader;

    public ImageLoader getLoader() {
        return loader;
    }

    //当程序启动后，第一个运行的方法，程序入口
    @Override
    public void onCreate() {
        super.onCreate();
        loader = ImageLoader.getInstance();

        //loader对象的对象的手动配置
        // 初始化ImageLoaderConfiguration类对象
        ImageLoaderConfiguration config = initConfiguration();
        loader.init(config);
    }

    private ImageLoaderConfiguration initConfiguration() {
        File file = getCacheDir(); //获取缓存文件夹，位于：data/data/程序包名/caches
        if (!file.exists()) {
            file.mkdirs();
        }

        DisplayImageOptions options = initDisplayOptions();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(3)  //最多可以同时下载多少张图片
                .memoryCacheSizePercentage(30)  //设置最大的内容大小，占用可用内存的百分比
                .memoryCache(new WeakMemoryCache()) //设置的缓存策略
                .diskCacheSize(10*1024*1024) //设置磁盘缓存的最大带下
                .diskCache(new UnlimitedDiskCache(file))  //设置磁盘缓存策略,参数中的file、：设置图片的存储地址
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) //设置文件的命名方式
                .defaultDisplayImageOptions(options)  //设置图片显示时的配置信息
                .build();
        return config;
    }

    private DisplayImageOptions initDisplayOptions() {

        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)  //是指图片下载后是否缓存到内存中
                .cacheOnDisk(true)//是指图片下载后是否到本地
                .showImageOnLoading(R.mipmap.ic_launcher)  // 设置图片下载过程中，控件上显示的图片
                .showImageOnFail(R.mipmap.ic_launcher) //设置图片下载失败，控件上显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }
}
