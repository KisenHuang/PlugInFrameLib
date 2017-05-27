package com.kisen.plugframelib.utils.http.urlconnection;

/**
 * UrlConnection 请求返回结果
 * Created by huang on 2017/4/18.
 */
public class ResultBody<D> {

    public static final int SUCCESS = 1;
    public static final int FAIL = 2;

    private final D result;
    private final int status;
    private final Exception exception;

    public ResultBody(D result, int status, Exception e) {
        this.result = result;
        this.status = status;
        this.exception = e;
    }

    public D getResult() {
        return result;
    }

    public int getStatus() {
        return status;
    }

    public Exception getException() {
        return exception;
    }
}
