package com.zxdz.car.base.view;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.huayu.falconroid.utils.FalconException;
import com.huayu.halio.Tag125K;
import com.hwangjr.rxbus.RxBus;
import com.kk.utils.UIUitls;
import com.zxdz.car.base.presenter.BasePresenter;
import com.zxdz.car.main.model.domain.Constant;

import java.io.IOException;

import butterknife.ButterKnife;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView, IDialog {

    private static final String TAG = "BaseActivity";

    protected P mPresenter;

    protected LoadingDialog mLoadingDialog;

    private ToneGenerator mToneGenerator;

    private Tag125K sTag125k = new Tag125K();

    private boolean mbQueryExitFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }

        ScreenUtils.setPortrait(this);
        RxBus.get().register(this);
        setContentView(getLayoutId());
        mLoadingDialog = new LoadingDialog(this);
        try {
            ButterKnife.bind(this);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.i(this.getClass().getSimpleName() + " ButterKnife->初始化失败 原因:" + e);
        }

        mToneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, 100);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter.unsubscribe();
        }
        RxBus.get().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter.subscribe();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mbQueryExitFlag = true;
        sTag125k.closeTag125KPower();
        sTag125k.closeTag125KPort();
    }


    @Override
    public void showLoadingDialog(String msg) {
        mLoadingDialog.setMessage(msg);
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        UIUitls.post(new Runnable() {
            @Override
            public void run() {
                mLoadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startact(Context old, Class newact) {
        startActivity(new Intent(old, newact));
    }

    //toolbar
    public void basetoobar(Toolbar toolbar, int title) {
        setSupportActionBar(toolbar);
        ActionBar actiontoolbar = getSupportActionBar();
        actiontoolbar.setDisplayHomeAsUpEnabled(true);
        actiontoolbar.setTitle(title);
    }

    public void basetoobar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        ActionBar actiontoolbar = getSupportActionBar();
        actiontoolbar.setDisplayHomeAsUpEnabled(true);
        actiontoolbar.setTitle(title);
    }

    //Toolbar返回键设置
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void playTone() {
        if (mToneGenerator != null) {
            mToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 100);
        }
    }

    public class QueryUidT extends Thread {
        public void run() {
            mbQueryExitFlag = false;

            try {
                while (!mbQueryExitFlag) {
                    byte[] bUid = null;
                    try {
                        bUid = sTag125k.readTag125KUid();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        LogUtils.e(TAG, "readTag125KUid Exception:" + e.toString());
                    }
                    if (bUid != null) {
                        StringBuffer sb = new StringBuffer();
                        LogUtils.e(TAG, bUid.length + "");
                        for (int i = 0; i < bUid.length; i++) {
                            sb.append(" ");
                            sb.append(Integer.toHexString(bUid[i] & 0xFF));
                        }

                        RxBus.get().post(Constant.READ_CARD_NUMBER, sb.toString());

                        //play tone
                        playTone();
                        try {
                            this.sleep(200);//1000
                        } catch (java.lang.InterruptedException ie) {
                        }

                    } else {
                        LogUtils.e(TAG, "等待读卡中...");
                    }

                }
            } catch (Exception e) {
                LogUtils.e("QueryUidT error --->" + e.getMessage());
            }
        }
    }

    public class OpenT extends Thread {
        public void run() {

            sTag125k.closeTag125KPower();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            sTag125k.openTag125KPower();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                sTag125k.openTag125KPort();
            } catch (FalconException fae) {
                LogUtils.e(TAG, "openTag125KPort Exception:" + fae.toString());
            }

            new QueryUidT().start();
        }
    }

}
