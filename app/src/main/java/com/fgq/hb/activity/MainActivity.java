package com.fgq.hb.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import com.fgq.hb.R;
import com.fgq.hb.base.BaseActivity;
import com.fgq.hb.util.T;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements AccessibilityManager.AccessibilityStateChangeListener {

    @BindView(R.id.tv_service_status)
    TextView tvServiceStatus;

    private AccessibilityManager accessibilityManager;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initSp();
    }

    private void initSp() {
        PreferenceManager.setDefaultValues(this, R.xml.frag_settings, false);

        accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.addAccessibilityStateChangeListener(this);
        updateUIStatus();
    }


    @OnClick({R.id.tv_start_service, R.id.tv_settings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_service://开启服务
                try {
                    T.show(R.string.turn_on_toast);
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));//打开系统设置---无障碍
                } catch (Exception e) {
                    T.show(getString(R.string.turn_on_error_toast));
                    e.printStackTrace();
                }
                break;
            case R.id.tv_settings://设置
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUIStatus();
    }


    @Override
    protected void onDestroy() {
        accessibilityManager.removeAccessibilityStateChangeListener(this);
        super.onDestroy();
    }


    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        updateUIStatus();
    }

    /**
     * 更新当前 UI 显示状态
     */
    private void updateUIStatus() {
        if (isServiceConnected()) {
            tvServiceStatus.setText(R.string.service_is_connected);
            tvServiceStatus.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        } else {
            tvServiceStatus.setText(R.string.service_un_connected);
            tvServiceStatus.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    /**
     * 获取 Service 是否启用状态
     */
    private boolean isServiceConnected() {
        List<AccessibilityServiceInfo> accessibilityServices = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(getPackageName() + "/.service.HongbaoService")) {
                return true;
            }
        }
        return false;
    }

}
