package com.kisen.plugframelib.utils.http;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/25 16:08.
 */
public class HttpMediaType {

    public static final String TYPE_TEXT_PLAIN = "text/plain;";//纯文本
    public static final String TYPE_TEXT_HTML = "text/html;";//html格式
    public static final String TYPE_TEXT_XML = "text/xml;";//xml格式
    public static final String TYPE_IMAGE_GIF = "image/gif;";//gif图片格式
    public static final String TYPE_IMAGE_JPG = "image/jpg;";//jpg图片格式
    public static final String TYPE_IMAGE_PNG = "image/png;";//png图片格式

    public static final String TYPE_APP_JSON = "application/json;";//JSON数据格式
    public static final String TYPE_APP_XHTML = "application/xhtml+xml;";//XHTML格式
    public static final String TYPE_APP_XML = "application/xml;";//XML格式
    public static final String TYPE_APP_ATOM_XML = "application/atom+xml;";//Atom XML聚合格式
    public static final String TYPE_APP_PDF = "application/pdf;";//pdf格式
    public static final String TYPE_APP_WORD = "application/msword;";//Word文档格式
    public static final String TYPE_APP_STREAM = "application/octet-stream;";//二进制流数据
    public static final String TYPE_APP_FROM = "application/x-www-form-urlencoded;";//表单默认的提交数据的格式
    public static final String TYPE_APP_MUL_FROM = "multipart/form-data;";//在表单中进行文件上传


    public static final String CODE_UTF = "charset=utf-8";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({TYPE_TEXT_HTML,
            TYPE_TEXT_PLAIN,
            TYPE_TEXT_XML,
            TYPE_IMAGE_GIF,
            TYPE_IMAGE_JPG,
            TYPE_IMAGE_PNG,
            TYPE_APP_JSON,
            TYPE_APP_XHTML,
            TYPE_APP_XML,
            TYPE_APP_ATOM_XML,
            TYPE_APP_PDF,
            TYPE_APP_WORD,
            TYPE_APP_STREAM,
            TYPE_APP_FROM,
            TYPE_APP_MUL_FROM})
    public @interface ContentType {
    }
}
