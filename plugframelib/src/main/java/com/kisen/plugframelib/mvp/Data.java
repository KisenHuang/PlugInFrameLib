package com.kisen.plugframelib.mvp;

import android.os.Parcelable;

/**
 * 标题：标示性父类，没有实际意义
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/15 14:16.
 * <p>
 * 使用第三方解析工具后，被解析的数据类不能被混淆
 * 在混淆代码时，数据类只需要混淆Data子类就可以了
 * </p>
 */

public abstract class Data implements Parcelable {
}
