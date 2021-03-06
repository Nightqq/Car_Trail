package com.zxdz.car.base.helper;


import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;



public class GlideConfiguration extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,
                DiskCache.Factory
                        .DEFAULT_DISK_CACHE_DIR,
                DiskCache.Factory
                        .DEFAULT_DISK_CACHE_SIZE));


    }

}