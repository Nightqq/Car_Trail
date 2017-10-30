package com.zxdz.car.main.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.AreaHelper;
import com.zxdz.car.base.helper.CardHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.AreaInfo;
import com.zxdz.car.main.model.domain.CardInfo;
import com.zxdz.car.main.view.lock.LockActivity;
import com.zxdz.car.main.view.setting.SettingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

/**
 * Created by super on 2017/10/16.
 */

public class MainActivity extends BaseActivity {

    //读取ID卡
    @BindView(R.id.btn_read_card)
    Button mReadCardButton;

    @BindView(R.id.btn_car_in)
    Button mCardInButton;

    //读取IC卡
    @BindView(R.id.btn_read_ic_card)
    Button mReadIcCardButton;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {


        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("是否使用带RFID的设备测试?")
                .setContentText("为了确保读卡正确，请使用RFID设备测试")
                .setConfirmText("RFID设备测试")
                .setCancelText("手机测试")
                .setCustomImage(R.mipmap.card_icon)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        App.isRFID = true;
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        App.isRFID = false;
                    }
                })
                .show();


        //读ID卡信息(测试)
        RxView.clicks(mReadCardButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startact(MainActivity.this, ReadCardActivity.class);
            }
        });

        //读IC卡信息(测试)
        RxView.clicks(mReadIcCardButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startact(MainActivity.this, ReadIcCardActivity.class);
            }
        });

        //车辆进入
        RxView.clicks(mCardInButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("值班人员是否有管理员卡?")
                        .setContentText("请刷管理员授权卡")
                        .setConfirmText("有卡")
                        .setCancelText("无卡")
                        .setCustomImage(R.mipmap.card_icon)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent intent = new Intent(MainActivity.this, AuthCardActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

    }


    @OnClick({R.id.main_setting, R.id.main_lock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_setting:
                startact(this, SettingActivity.class);
                break;
            case R.id.main_lock:
                startact(this, LockActivity.class);
                break;
        }
    }
}
