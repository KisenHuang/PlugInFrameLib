package com.kisen.plugframelib.utils.http;

/**
 * 网络下载回调
 * Created by huang on 2017/4/18.
 */

public interface DownloadCallback<D> extends NetWorkCallback<D> {
    void progress(float progress, long total, int id);
}
