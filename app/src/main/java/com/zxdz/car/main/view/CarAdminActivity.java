package com.zxdz.car.main.view;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.Constant;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

/**
 * Created by super on 2017/10/22.
 * 带车干警刷卡界面
 */

public class CarAdminActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.btn_success)
    Button mSuccessButton;

    @BindView(R.id.tv_car_number)
    TextView mCardNumberTextView;

    @BindView(R.id.tv_auth_state)
    TextView mCardStateTextView;

    @BindView(R.id.layout_card_read_wait)
    LinearLayout mReadWaitLayout;

    @BindView(R.id.layout_card_info)
    LinearLayout mCardInfoLayout;

    @BindView(R.id.layout_next)
    LinearLayout mNextLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_car_admin;
    }

    @Override
    public void init() {
        mToolBar.setTitle("带领干警刷卡");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //带领干警刷卡(模拟刷卡成功)
        RxView.clicks(mSuccessButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //ToastUtils.showLong("带车干警刷卡成功");
                new SweetAlertDialog(CarAdminActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("驾驶员刷卡?")
                        .setContentText("驾驶员是否需要刷卡")
                        .setConfirmText("需要")
                        .setCancelText("不需要")
                        .setCustomImage(R.mipmap.card_icon)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent intent = new Intent(CarAdminActivity.this, DriverSwipeCardActivity.class);
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

        //模拟测试步骤，后期删除
        //TODO
        if (!App.isRFID) {
            mNextLayout.setVisibility(View.VISIBLE);
            saveAdminCard("ZX00002");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        new OpenT().start();
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(Constant.READ_CARD_NUMBER)
            }
    )
    /**
     * 显示读取到的卡号
     */
    public void showCardNumber(String carNumber) {
        mCardNumberTextView.setText(carNumber);
        mReadWaitLayout.setVisibility(View.GONE);

        //存储卡号到本地数据库
        saveAdminCard(carNumber);
        mCardInfoLayout.setVisibility(View.VISIBLE);
        mNextLayout.setVisibility(View.VISIBLE);
        mCardStateTextView.setText(getResources().getText(R.string.card_auth_success_text));
    }

    /**
     * 读卡成功保存【带车干警卡号】
     *
     * @param cardNumber
     */
    public void saveAdminCard(String cardNumber) {

        if (CarTravelHelper.carTravelRecord != null) {
            CarTravelHelper.carTravelRecord.setUsePoliceCardNumber(cardNumber);
            CarTravelHelper.carTravelRecord.setUsePoliceSwipeTime(new Date());
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
        }

        LogUtils.e("读取到的带车干警卡号--->" + CarTravelHelper.carTravelRecord.getUsePoliceCardNumber());
    }
}
