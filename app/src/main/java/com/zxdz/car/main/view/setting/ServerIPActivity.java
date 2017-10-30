package com.zxdz.car.main.view.setting;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServerIPActivity extends BaseActivity {


    @BindView(R.id.server_ip_toolbar)
    Toolbar serverIpToolbar;
    @BindView(R.id.server_ip)
    TextView serverIp;

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(serverIpToolbar, R.string.setting_server_IP);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_server_ip;
    }


    @OnClick(R.id.server_ip_change)
    public void onViewClicked() {
        showdialog();
    }

    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.server_dialog, null);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }
}
