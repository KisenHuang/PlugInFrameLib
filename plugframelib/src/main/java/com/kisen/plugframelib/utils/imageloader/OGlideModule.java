package com.kisen.plugframelib.utils.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;
import com.kisen.plugframelib.utils.cache.FileUtils;

import java.io.File;

/**
 * 自定义Glide配置类
 * <p>
 * 在清单文件中配置
 * 该类不能被混淆
 * </p>
 * Created by huang on 2017/4/17.
 */
public class OGlideModule implements GlideModule {

    private static final int sizeInBytes = 100 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        File cacheLocation = FileUtils.getInstance().getCacheImageDir();
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, dir, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE));
        builder.setDiskCache(new DiskLruCacheFactory(FileUtils.getInstance().getCacheImageDir().getPath(), DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE));

        builder.setMemoryCache(new LruResourceCache(sizeInBytes / 2));
        builder.setBitmapPool(new LruBitmapPool(sizeInBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
// register ModelLoaders here.
    }
}
