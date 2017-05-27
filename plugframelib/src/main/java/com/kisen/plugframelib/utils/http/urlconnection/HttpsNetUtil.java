package com.kisen.plugframelib.utils.http.urlconnection;

import android.accounts.NetworkErrorException;


import com.kisen.plugframelib.utils.http.NetWorkCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 基于HttpsUrlConnection的网络请求工具类
 * Created by huang on 2017/4/17.
 */

public class HttpsNetUtil {

    public static String CHARSET_UTF8 = "utf-8";
    private static ExecutorService threadPool;

    private static final TrustManager mTrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

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
    public static void getByteFromNet(String path, int id, NetWorkCallback<byte[]> callBack) {
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

//    public static String postFromNet(String path) {
//        String result = null;
//
//        //使用此工具可以将键值对编码成"Key=Value&amp;Key2=Value2&amp;Key3=Value3&rdquo;形式的请求参数
//        try {
//            //设置SSLContext
//            SSLContext sslcontext = SSLContext.getInstance("TLS");
//            sslcontext.init(null, new TrustManager[]{mTrustManager}, null);
//
//            //打开连接
//            //要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
//            URL requestUrl = new URL(path);
//            HttpsURLConnection httpsConn = (HttpsURLConnection) requestUrl.openConnection();
//
//            //设置套接工厂
//            httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());
//
//            //加入数据
//            httpsConn.setRequestMethod("POST");
//            httpsConn.setDoOutput(true);
//            DataOutputStream out = new DataOutputStream(
//                    httpsConn.getOutputStream());
//
//            out.flush();
//            out.close();
//
//            //获取输入流
//            BufferedReader in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream()));
//            int code = httpsConn.getResponseCode();
//            if (HttpsURLConnection.HTTP_OK == code) {
//                String temp = in.readLine();
//                /*连接成一个字符串*/
//                while (temp != null) {
//                    if (result != null)
//                        result += temp;
//                    else
//                        result = temp;
//                    temp = in.readLine();
//                }
//            }
//        } catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    private static ResultBody<byte[]> getByteByConnection(String path) {
        try {
            //打开连接
            HttpsURLConnection urlConnection = initHttps(path, "GET", null);

            if (200 == urlConnection.getResponseCode()) {
                OutputStream out = urlConnection.getOutputStream();
                out.write(path.getBytes(CHARSET_UTF8));
                out.flush();
                out.close();
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
                urlConnection.disconnect();// 关闭连接
                return new ResultBody<>(bytes, ResultBody.SUCCESS, null);
            } else {
                return new ResultBody<>(null, ResultBody.FAIL, new NetworkErrorException("网络异常"));
            }
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException e) {
            return new ResultBody<>(null, ResultBody.FAIL, e);
        }
    }

    private static ResultBody<String> getStringByConnection(String path) {
        try {
            //打开连接
            HttpsURLConnection urlConnection = initHttps(path, "GET", null);

            if (200 == urlConnection.getResponseCode()) {
                OutputStream out = urlConnection.getOutputStream();
                out.write(path.getBytes(CHARSET_UTF8));
                out.flush();
                out.close();
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
                urlConnection.disconnect();// 关闭连接
                return new ResultBody<>(json, ResultBody.SUCCESS, null);
            } else {
                return new ResultBody<>(null, ResultBody.FAIL, new NetworkErrorException("网络异常"));
            }
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException e) {
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

    /**
     * 初始化http请求参数
     */
    private static HttpsURLConnection initHttps(String url, String method, Map<String, String> headers)
            throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        TrustManager[] tm = {mTrustManager};
        SSLContext sslContext = SSLContext.getInstance("TLSv1");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        URL _url = new URL(url);
        HttpsURLConnection http = (HttpsURLConnection) _url.openConnection();
        // 连接超时
        http.setConnectTimeout(25000);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(25000);
        http.setRequestMethod(method);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (null != headers && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                http.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        http.setSSLSocketFactory(ssf);
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();
        return http;
    }

}
