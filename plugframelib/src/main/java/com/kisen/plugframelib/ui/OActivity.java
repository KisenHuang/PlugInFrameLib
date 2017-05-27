package com.kisen.plugframelib.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.kisen.plugframelib.utils.http.HttpClient;

import butterknife.ButterKnife;

/**
 * 抽象库基类
 * Created by huang on 2017/4/18.
 */

public abstract class OActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mContext = this;
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    protected abstract int getContentView();

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpClient.cancelByTag(this);
    }
}
