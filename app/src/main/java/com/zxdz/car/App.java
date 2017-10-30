package com.zxdz.car;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.zxdz.car.base.helper.CardDBUtil;

/**
 * Created by super on 2017/10/21.
 */

public class App extends Application {

    /**
     * 是否使用带RFID的设备测试
     */
    public static boolean isRFID = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(App.this);
        CardDBUtil.init(App.this);
    }
}
