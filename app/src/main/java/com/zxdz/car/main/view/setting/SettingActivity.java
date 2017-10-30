package com.zxdz.car.main.view.setting;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.setting_toolbar)
    Toolbar settingToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(settingToolbar,R.string.setting);
    }
    @OnClick({R.id.setting_admin, R.id.setting_area, R.id.setting_swipe_card, R.id.setting_server_ip, R.id.setting_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_admin:
                startact(this,AdminActivity.class);
                break;
            case R.id.setting_area:
                startact(this,AreaActivity.class);
                break;
            case R.id.setting_swipe_card:
                startact(this,SwipeCardActivity.class);
                break;
            case R.id.setting_server_ip:
                startact(this,ServerIPActivity.class);
                break;
            case R.id.setting_change:
                startact(this,ChangeActivity.class);
                break;
        }
    }
}
