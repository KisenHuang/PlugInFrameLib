package com.kisen.plugframelib.utils.http.urlconnection;

import android.accounts.NetworkErrorException;


import com.kisen.plugframelib.utils.http.NetWorkCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 基于HttpUrlConnection的网路请求工具类
 * Created by huang on 2017/4/17.
 */
public class HttpNetUtil {

    private static ExecutorService threadPool;

    private static void initThreadPool() {
        if (threadPool == null) {
            threadPool = Executors.newCachedThreadPool();
        }
    }

    @SuppressWarnings("unused")
    public static void getJsonFromNet(final String path, int id, final NetWorkCallback<String> callBack) {
        initThreadPool();
        FutureTask<ResultBody<String>> task = getStringTask(path);
        threadPool.execute(task);
        try {
            ResultBody<String> body = task.get(10000L, TimeUnit.MILLISECONDS);
            if (body.getStatus() == ResultBody.FAIL) {
                callBack.fail(body.getException(), id);
            } else if (body.getResult() == null) {
                callBack.fail(body.getException(), id);
            } else {
                callBack.success(body.getResult(), id);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            task.cancel(true);
            callBack.fail(e, id);
        } finally {
            callBack.finish(id);
        }
    }

    @SuppressWarnings("unused")
    public static void getByteFromNet(final String path, int id, final NetWorkCallback<byte[]> callBack) {
        initThreadPool();
        FutureTask<ResultBody<byte[]>> task = getByteArrayTask(path);
        threadPool.execute(task);
        try {
            ResultBody<byte[]> body = task.get(10000L, TimeUnit.MILLISECONDS);
            if (body.getStatus() == ResultBody.FAIL) {
                callBack.fail(body.getException(), id);
            } else if (body.getResult() == null) {
                callBack.fail(body.getException(), id);
            } else {
                callBack.success(body.getResult(), id);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            task.cancel(true);
            callBack.fail(e, id);
        } finally {
            callBack.finish(id);
        }
    }

    private static ResultBody<byte[]> getByteByConnection(String path) {
        try {
            URL url = new URL(path.trim());
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if (200 == urlConnection.getResponseCode()) {
                //得到输入流
                InputStream is = urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while (-1 != (len = is.read(buffer))) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }
                byte[] bytes = baos.toByteArray();
                baos.close();
                is.close();
                return new ResultBody<>(bytes, ResultBody.SUCCESS, null);
            } else {
                return new ResultBody<>(null, ResultBody.FAIL, new NetworkErrorException("网络异常"));
            }
        } catch (IOException e) {
            return new ResultBody<>(null, ResultBody.FAIL, e);
        }
    }

    private static ResultBody<String> getStringByConnection(String path) {
        try {
            URL url = new URL(path.trim());
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if (200 == urlConnection.getResponseCode()) {
                //得到输入流
                InputStream is = urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while (-1 != (len = is.read(buffer))) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }
                String json = baos.toString("UTF-8");
                baos.close();
                is.close();
                return new ResultBody<>(json, ResultBody.SUCCESS, null);
            } else {
                return new ResultBody<>(null, ResultBody.FAIL, new NetworkErrorException("网络异常"));
            }
        } catch (IOException e) {
            return new ResultBody<>(null, ResultBody.FAIL, e);
        }
    }

    private static FutureTask<ResultBody<String>> getStringTask(final String path) {
        return new FutureTask<>(new Callable<ResultBody<String>>() {
            @Override
            public ResultBody<String> call() throws Exception {
                return getStringByConnection(path);
            }
        });
    }

    private static FutureTask<ResultBody<byte[]>> getByteArrayTask(final String path) {
        return new FutureTask<>(new Callable<ResultBody<byte[]>>() {
            @Override
            public ResultBody<byte[]> call() throws Exception {
                return getByteByConnection(path);
            }
        });
    }

}
