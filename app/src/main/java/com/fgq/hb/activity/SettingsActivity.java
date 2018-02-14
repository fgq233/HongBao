package com.fgq.hb.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fgq.hb.R;
import com.fgq.hb.base.BaseActivity;
import com.fgq.hb.fragment.SettingsFragment;

import butterknife.BindView;


public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void init() {
        initToolBar();
        initFragment();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//展示系统返回键图标
    }

    private void initFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, new SettingsFragment());
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {//点击返回键返回
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
