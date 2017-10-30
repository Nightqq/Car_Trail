package com.zxdz.car.main.model.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by super on 2017/10/19.
 * 终端管理人员卡号
 */

@Entity
public class CardInfo {

    @Id
    private Long id;

    /**
     * 终端机序号
     */
    private int zdjId;

    /**
     * 管理人员卡号
     */
    private String adminCardNumber;

    /**
     * 管理人员姓名
     */
    private String adminName;

    private String remark;

    @Generated(hash = 1917361521)
    public CardInfo(Long id, int zdjId, String adminCardNumber, String adminName,
                    String remark) {
        this.id = id;
        this.zdjId = zdjId;
        this.adminCardNumber = adminCardNumber;
        this.adminName = adminName;
        this.remark = remark;
    }

    @Generated(hash = 555217359)
    public CardInfo() {
    }

    public int getZdjId() {
        return zdjId;
    }

    public void setZdjId(int zdjId) {
        this.zdjId = zdjId;
    }

    public String getAdminCardNumber() {
        return adminCardNumber;
    }

    public void setAdminCardNumber(String adminCardNumber) {
        this.adminCardNumber = adminCardNumber;
    }
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
