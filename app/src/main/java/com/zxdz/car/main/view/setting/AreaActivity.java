package com.zxdz.car.main.view.setting;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.zxdz.car.R;
import com.zxdz.car.base.helper.AreaHelper;
import com.zxdz.car.base.helper.CardHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.AreaInfo;
import com.zxdz.car.main.model.domain.CardInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AreaActivity extends BaseActivity {

    @BindView(R.id.area_toolbar)
    Toolbar areaToolbar;
    @BindView(R.id.area_id)
    TextView areaId;
    @BindView(R.id.area_name)
    TextView areaName;
    @BindView(R.id.area_prison)
    TextView areaPrison;
    @BindView(R.id.area_tgb_police)
    ToggleButton areaTgbPolice;
    @BindView(R.id.area_tgb_dricer)
    ToggleButton areaTgbDricer;
    @BindView(R.id.area_range)
    TextView areaRange;
    private long mLong = 1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_area;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(areaToolbar, R.string.area_setting);
        initdata();
    }

    private void initdata() {
        List<AreaInfo> areaInfoList = AreaHelper.getAreaInfoListFromDB();
        if (areaInfoList != null) {
            AreaInfo areaInfo = areaInfoList.get(0);
            //读取数据库信息，显示数据
            areaId.setText(areaInfo.getArea_Id()+"");
            areaName.setText(areaInfo.getArea_name());
            areaPrison.setText(areaInfo.getArea_police());
            areaRange.setText(areaInfo.getArea_range());
            areaTgbPolice.setChecked(areaInfo.getArea_card_police());
            areaTgbDricer.setChecked(areaInfo.getArea_card_dricer());
        } else {
            Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.toobar_upload, R.id.area_id_change, R.id.area_range, R.id.area_tgb_police, R.id.area_tgb_dricer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toobar_upload:
                Toast.makeText(this, "暂未实现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.area_id_change:
                //弹出弹窗
                showdialog();
                break;
            case R.id.area_range:
                Toast.makeText(this, "跳转至轨迹页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.area_tgb_police:
                break;
            case R.id.area_tgb_dricer:
                break;
        }
    }

    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.area_dialog, null);
        dialog.setView(view, 0, 0, 0, 0);
        final EditText area_id = (EditText) view.findViewById(R.id.area_dialog_id);
        final EditText area_name = (EditText) view.findViewById(R.id.area_dialog_name);
        final EditText area_prison = (EditText) view.findViewById(R.id.area_dialog_prison);
        final EditText area_range = (EditText) view.findViewById(R.id.area_dialog_range);
        Button btnOK = (Button) view.findViewById(R.id.area_btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.area_btn_cancel);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = area_id.getText().toString().trim();
                int mid = Integer.parseInt(id);
                String name = area_name.getText().toString().trim();
                String prison = area_prison.getText().toString().trim();
                String range = area_range.getText().toString().trim();
                if (name.equals("") || id.equals("") || prison.equals("")||range.equals("")) {
                    Toast.makeText(AreaActivity.this, "区域id、名称、所属监狱和描述不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                AreaInfo areaInfo = new AreaInfo(mLong, mid, name, prison, range, true, false);
                AreaHelper.saveAreaInfoToDB(areaInfo);
                initdata();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();// 隐藏dialog
            }
        });
        dialog.show();
    }

}
