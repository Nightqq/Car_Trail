package com.zxdz.car.main.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;

/**
 * Created by admin on 2017/10/29.
 */

public class InstallWaitActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.layout_install_wait)
    LinearLayout mLayoutInstallWait;

    @BindView(R.id.layout_over)
    LinearLayout mLayoutOver;

    @BindView(R.id.layout_success)
    LinearLayout mLayoutSuccess;

    @BindView(R.id.btn_install_success)
    Button mInstallSuccessButton;

    HeadsetPlugReceiver headsetPlugReceiver;

    private AudioManager audoManager;

    private boolean isFirstOpen = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_install_wait;
    }

    @Override
    public void init() {
        mToolBar.setTitle("等待安装设备");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerHeadsetPlugReceiver();
        audoManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    }

    private void registerHeadsetPlugReceiver() {
        headsetPlugReceiver = new HeadsetPlugReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetPlugReceiver, filter);
    }

    //监听
    public class HeadsetPlugReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
                if (intent.hasExtra("state")) {
                    if (intent.getIntExtra("state", 0) == 0) {
                        if(!isFirstOpen) {
                            ToastUtils.showLong("设备已拔出");
                        }
                        isFirstOpen = false;
                        mLayoutInstallWait.setVisibility(View.VISIBLE);
                        mLayoutOver.setVisibility(View.GONE);
                        mLayoutSuccess.setVisibility(View.GONE);
                    } else if (intent.getIntExtra("state", 0) == 1) {
                        ToastUtils.showLong("设备已安装");
                        mLayoutInstallWait.setVisibility(View.GONE);
                        mLayoutOver.setVisibility(View.VISIBLE);
                        mLayoutSuccess.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    private void unregisterReceiver() {
        this.unregisterReceiver(headsetPlugReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
