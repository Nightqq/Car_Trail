package com.zxdz.car.main.model.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lenovo on 2017/10/30.
 */

@Entity
public class AreaInfo {
    @Id
    private Long id;
    //区域id
    private int Area_Id;
    //区域名称
    private String Area_name;
    //所属监狱
    private String Area_police;
    //区域范围
    private String Area_range;
    //是否干警刷卡
    private boolean Area_card_police;
    //是否驾驶员刷卡
    private boolean Area_card_dricer;
    @Generated(hash = 1770303117)
    public AreaInfo(Long id, int Area_Id, String Area_name, String Area_police,
            String Area_range, boolean Area_card_police, boolean Area_card_dricer) {
        this.id = id;
        this.Area_Id = Area_Id;
        this.Area_name = Area_name;
        this.Area_police = Area_police;
        this.Area_range = Area_range;
        this.Area_card_police = Area_card_police;
        this.Area_card_dricer = Area_card_dricer;
    }
    @Generated(hash = 177146206)
    public AreaInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getArea_Id() {
        return this.Area_Id;
    }
    public void setArea_Id(int Area_Id) {
        this.Area_Id = Area_Id;
    }
    public String getArea_name() {
        return this.Area_name;
    }
    public void setArea_name(String Area_name) {
        this.Area_name = Area_name;
    }
    public String getArea_police() {
        return this.Area_police;
    }
    public void setArea_police(String Area_police) {
        this.Area_police = Area_police;
    }
    public String getArea_range() {
        return this.Area_range;
    }
    public void setArea_range(String Area_range) {
        this.Area_range = Area_range;
    }
    public boolean getArea_card_police() {
        return this.Area_card_police;
    }
    public void setArea_card_police(boolean Area_card_police) {
        this.Area_card_police = Area_card_police;
    }
    public boolean getArea_card_dricer() {
        return this.Area_card_dricer;
    }
    public void setArea_card_dricer(boolean Area_card_dricer) {
        this.Area_card_dricer = Area_card_dricer;
    }


}
