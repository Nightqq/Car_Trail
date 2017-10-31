package com.zxdz.car.main.model.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Lenovo on 2017/10/31.
 */
@Entity
public class ChangePolice {
    @Id
    private int change_id;
    private String police_num;
    private String driver_num;
}
