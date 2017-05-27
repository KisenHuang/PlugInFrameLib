package com.kisen.plugframelib.utils.http;

/**
 * 网络请求结果回调
 *
 * @param <D> 返回结果类型
 * Created by kisen on 2017/4/17.
 */
public interface NetWorkCallback<D> {

    /**
     * 网络请求成功结果回调
     *
     * @param result 返回结果
     * @param id     请求识别码，本地识别
     */
    void success(D result, int id);

    /**
     * 网络请求失败结果回调
     *
     * @param e 失败异常结果
     * @param id     请求识别码，本地识别
     */
    void fail(Exception e, int id);

    /**
     * 网络请求结束回调
     * 在success()方法和fail()方法之后调用
     * @param id     请求识别码，本地识别
     */
    void finish(int id);
}