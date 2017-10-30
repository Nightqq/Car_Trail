package com.zxdz.car.main.view.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeCardActivity extends BaseActivity {


    @BindView(R.id.swipecard_toolbar)
    Toolbar swipecardToolbar;

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(swipecardToolbar,R.string.swipe_setting);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_swipe_card;
    }

}
