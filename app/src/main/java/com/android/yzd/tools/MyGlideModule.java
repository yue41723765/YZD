package com.android.yzd.tools;

import android.content.Context;

import com.android.yzd.ui.ECApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 貌似是三级缓存/磁盘缓存
 */
//com.android.yzd.tools.MyGlideModule
public class MyGlideModule implements GlideModule {
    private static final int DISK_CACHE_SIZE = 100 * 1024 * 1024;
    public static final int MAX_MEMORY_CACHE_SIZE = 80 * 1024 * 1024;

    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        //设置磁盘缓存的路径 path
        String path = ECApplication.application.getCacheDir().toString();
        final File cacheDir = new File(path);
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(cacheDir, DISK_CACHE_SIZE);
            }
        });
        //设置内存缓存大小，一般默认使用glide内部的默认值
        builder.setMemoryCache(new LruResourceCache(MAX_MEMORY_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
