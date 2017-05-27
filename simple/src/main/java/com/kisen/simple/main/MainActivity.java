package com.kisen.simple.main;

import com.kisen.plugframelib.mvp.presenter.BasePresenter;
import com.kisen.simple.BaseActivity;
import com.kisen.simple.R;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(BasePresenter presenter) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new MainFragment())
                .commit();
    }

    @Override
    public BasePresenter newPresenter() {
        return null;
    }

}
