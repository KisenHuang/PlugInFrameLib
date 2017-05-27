package com.kisen.plugframelib.mvp.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kisen.plugframelib.mvp.presenter.BasePresenter;
import com.kisen.plugframelib.utils.LogUtil;
import com.kisen.plugframelib.utils.http.HttpClient;
import com.kisen.plugframelib.utils.http.HttpMediaType;
import com.kisen.plugframelib.utils.http.NetWorkCallback;
import com.kisen.plugframelib.utils.http.RequestParam;


/**
 * 标题：MVP 模式下 Model基类
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/18 11:42.
 * <p>
 * <h>主要负责网络请求</h>
 * 封装okHttp的get、post请求
 * <h>通知BaseView</h>
 * {@link MvpModel#getCallback(NetWorkCallback)}方法，将callback进行封装，并通知BaseView网络请求状态
 * </p>
 */
public abstract class MvpModel {

    protected BasePresenter presenter;
    protected Context context;
    private static final String TAG = "MvpModel";

    public MvpModel(BasePresenter presenter) {
        this.presenter = presenter;
        context = presenter.getView().getContext();
    }

    /**
     * post String 请求
     *
     * @param url      请求url
     * @param content  body
     * @param reqCode  请求码
     * @param callback 请求回调
     */
    protected void postString(String url, String content, @HttpMediaType.ContentType String contentType,
                              int reqCode, final NetWorkCallback<String> callback) {
        LogUtil.d(TAG, "postString reqCode :" + reqCode + " URL：" + url + content);
        HttpClient.postString(context, url, content, contentType, reqCode, getCallback(callback));
    }

    /**
     * post请求
     *
     * @param url      请求url
     * @param param    请求参数
     * @param reqCode  请求码
     * @param callback 请求回调
     */
    protected void post(String url, RequestParam param, int reqCode, final NetWorkCallback<String> callback) {
        LogUtil.d(TAG, "post reqCode :" + reqCode + " URL：" + url + (param == null ? "" : param.toString()));
        HttpClient.post(context, url, param, reqCode, getCallback(callback));
    }

    /**
     * get请求
     *
     * @param url      请求url
     * @param param    请求参数
     * @param reqCode  请求码
     * @param callback 请求回调
     */
    protected void get(String url, RequestParam param, int reqCode, final NetWorkCallback<String> callback) {
        LogUtil.d(TAG, "get reqCode :" + reqCode + " URL：" + url + (param == null ? "" : param.toString()));
        HttpClient.get(context, url, param, reqCode, getCallback(callback));
    }

    /**
     * put请求
     *
     * @param url      请求url
     * @param content  body
     * @param reqCode  请求码
     * @param callback 请求回调
     */
    protected void put(String url, String content, @HttpMediaType.ContentType String contentType,
                       int reqCode, final NetWorkCallback<String> callback) {
        LogUtil.d(TAG, "put type: " + contentType + " reqCode :" + reqCode + " URL：" + url + "/" + content);
        HttpClient.put(context, url, content, contentType, reqCode, getCallback(callback));
    }

    /**
     * delete请求
     *
     * @param url      请求url
     * @param content  body
     * @param reqCode  请求码
     * @param callback 请求回调
     */
    protected void delete(String url, String content, @HttpMediaType.ContentType String contentType,
                          int reqCode, final NetWorkCallback<String> callback) {
        LogUtil.d(TAG, "delete type: " + contentType + " reqCode :" + reqCode + " URL：" + url + "/" + content);
        HttpClient.delete(context, url, content, contentType, reqCode, getCallback(callback));
    }

    /**
     * 封装callback
     * 所有请求的回调必须经过这一层封装
     * 它会通知BaseView的请求回调
     *
     * @param callback 返回的数据
     * @return 封装后的callback
     */
    @NonNull
    protected NetWorkCallback<String> getCallback(final NetWorkCallback<String> callback) {
        return new NetWorkCallback<String>() {
            @Override
            @SuppressWarnings("unchecked")
            public void success(String result, int id) {
                LogUtil.d(TAG, "reqCode :" + id + "result: " + result);
                presenter.getView().handleSuccess(result, id);
                callback.success(result, id);
            }

            @Override
            public void fail(Exception e, int id) {
                LogUtil.d(TAG, "reqCode :" + id + "result: " + (e == null ? "" : e.toString()));
                presenter.getView().handleFail(e, id);
                callback.fail(e, id);
            }

            @Override
            public void finish(int id) {
                presenter.getView().handleFinish(id);
                callback.finish(id);
            }
        };
    }

    public static abstract class MvpNetWorkCallBack<D> implements NetWorkCallback<D> {

        private final NetWorkCallback callback;

        protected MvpNetWorkCallBack(NetWorkCallback callback) {
            this.callback = callback;
        }

        @Override
        public void fail(Exception e, int id) {
            callback.fail(e, id);
        }

        @Override
        public void finish(int id) {
            callback.finish(id);
        }
    }

}
