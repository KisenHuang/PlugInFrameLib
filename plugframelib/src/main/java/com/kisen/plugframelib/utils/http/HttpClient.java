package com.kisen.plugframelib.utils.http;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * 第三方网络请求管理类
 * <p>
 * <h>目前支持第三方框架</h>
 * OkHttpUtils
 * </p>
 * Created by kisen on 2017/4/18.
 */
public class HttpClient {

    @SuppressWarnings("unused")
    public static void init(Application context, InputStream[] certificates,
                            InputStream bksFile, String password) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(certificates, bksFile, password);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //设置Https认证
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //cookie
                // PersistentCookieStore //持久化cookie
                // SerializableHttpCookie //持久化cookie
                // MemoryCookieStore //cookie信息存在内存中
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                .build();
        OkHttpUtils.initClient(client);
    }

    @SuppressWarnings("unused")
    public static void init(Application context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //cookie
                // PersistentCookieStore //持久化cookie
                // SerializableHttpCookie //持久化cookie
                // MemoryCookieStore //cookie信息存在内存中
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(context)))
                .build();
        OkHttpUtils.initClient(client);
    }

    @SuppressWarnings("unused")
    public static void get(Context context, String url, RequestParam params, int id,
                           final NetWorkCallback<String> callback) {
        OkHttpUtils
                .get()
                .url(url)
                .id(id)
                .tag(context)
                .params(params == null ? null : params.get())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callback != null)
                            callback.fail(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (callback != null)
                            callback.success(response, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        if (callback != null)
                            callback.finish(id);
                    }
                });
    }

    @SuppressWarnings("unused")
    public static void getBitmap(Context context, String url, RequestParam params, int id,
                                 final NetWorkCallback<Bitmap> callback) {
        OkHttpUtils
                .get()
                .url(url)
                .id(id)
                .tag(context)
                .params(params == null ? null : params.get())
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callback != null)
                            callback.fail(e, id);
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        if (callback != null)
                            callback.success(response, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        if (callback != null)
                            callback.finish(id);
                    }
                });
    }

    @SuppressWarnings("unused")
    public static void post(Context context, String url, RequestParam params, int id,
                            final NetWorkCallback<String> callback) {
        OkHttpUtils
                .post()
                .url(url)
                .id(id)
                .tag(context)
                .params(params == null ? null : params.get())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callback != null)
                            callback.fail(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (callback != null)
                            callback.success(response, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        callback.finish(id);
                    }
                });
    }

    @SuppressWarnings("unused")
    public static void postString(Context context, String url, String content,
                                  @HttpMediaType.ContentType String mediaType, int id,
                                  final NetWorkCallback<String> callback) {
        OkHttpUtils
                .postString()
                .tag(context)
                .id(id)
                .url(url)
                .content(content)
                .mediaType(getMediaType(mediaType))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callback != null)
                            callback.fail(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        callback.success(response, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        callback.finish(id);
                    }
                });
    }

    @SuppressWarnings("unused")
    public static void postFile(Context context, String url, File file, int id,
                                final NetWorkCallback<String> callback) {
        OkHttpUtils
                .postFile()
                .tag(context)
                .id(id)
                .url(url)
                .file(file)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callback != null)
                            callback.fail(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (callback != null)
                            callback.success(response, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        if (callback != null)
                            callback.finish(id);
                    }
                });
    }

    @SuppressWarnings("unused")
    public static void downloadFile(Context context, String url, int id, String savePath,
                                    String fileName, final DownloadCallback<File> callback) {
        OkHttpUtils
                .get()
                .tag(context)
                .id(id)
                .url(url)
                .build()
                .execute(new FileCallBack(savePath, fileName) {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        if (callback != null)
                            callback.progress(progress, total, id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callback != null)
                            callback.fail(e, id);
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        if (callback != null)
                            callback.success(response, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        if (callback != null)
                            callback.finish(id);
                    }
                });
    }

    @SuppressWarnings("unused")
    public static void put(Context context, String url, String content,
                           @HttpMediaType.ContentType String mediaType, int id,
                           final NetWorkCallback<String> callback) {
        OkHttpUtils
                .put()
                .url(url)
                .id(id)
                .tag(context)
                .requestBody(getRequestBody(content, mediaType))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callback != null)
                            callback.fail(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        callback.success(response, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        callback.finish(id);
                    }
                });
    }

    @SuppressWarnings("unused")
    public static void delete(Context context, String url, String content,
                              @HttpMediaType.ContentType String mediaType, int id,
                              final NetWorkCallback<String> callback) {
        OkHttpUtils
                .delete()
                .url(url)
                .id(id)
                .tag(context)
                .requestBody(getRequestBody(content, mediaType))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (callback != null)
                            callback.fail(e, id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        callback.success(response, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        callback.finish(id);
                    }
                });
    }

    private static RequestBody getRequestBody(String content, @HttpMediaType.ContentType String mediaType) {
        if (content == null)
            content = "";
        if (mediaType == null)
            content = HttpMediaType.TYPE_TEXT_PLAIN;
        return RequestBody.create(getMediaType(mediaType), content);
    }

    private static MediaType getMediaType(String mediaType) {
        return MediaType.parse(mediaType + HttpMediaType.CODE_UTF);
    }

    @SuppressWarnings("unused")
    public static void cancelByUrl(String url) {
        OkHttpUtils.get().url(url).build().cancel();
    }

    @SuppressWarnings("unused")
    public static void cancelByTag(Object tag) {
        OkHttpUtils.getInstance().cancelTag(tag);
    }


}
