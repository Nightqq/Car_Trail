package com.zxdz.car.main.model.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by super on 2017/10/25.
 * 车辆进出记录
 */
@Entity
public class CarTravelRecord {
    @Id
    private Long id;
    private int zdjId;
    private int lsId;
    private int qyId;
    private Date currentDate;
    private int state;
    private String adminCardNumber;
    private Date adminSwipeTime;
    //领用
    private String usePoliceCardNumber;
    private Date usePoliceSwipeTime;
    private String driverCardNumber;
    private Date driverSwipeTime;
    private String carNumber;
    private String carType;
    private String driverName;
    private String driverSex;
    private String entryReasons;

    //安装
    private String installPoliceCardNumber;
    private Date installPoliceSwipeTime;
    //锁车
    private String lockPoliceCardNumber;
    private Date lockPoliceSwipeTime;
    //开锁
    private String openPoliceCardNumber;
    private Date openPoliceSwipeTime;
    //交还
    private String returnPoliceCardNumber;
    private Date returnPoliceSwipeTime;
    //数据交换状态
    private int dateState;

    private String remark;

    @Generated(hash = 113337938)
    public CarTravelRecord(Long id, int zdjId, int lsId, int qyId, Date currentDate,
            int state, String adminCardNumber, Date adminSwipeTime,
            String usePoliceCardNumber, Date usePoliceSwipeTime,
            String driverCardNumber, Date driverSwipeTime, String carNumber,
            String carType, String driverName, String driverSex,
            String entryReasons, String installPoliceCardNumber,
            Date installPoliceSwipeTime, String lockPoliceCardNumber,
            Date lockPoliceSwipeTime, String openPoliceCardNumber,
            Date openPoliceSwipeTime, String returnPoliceCardNumber,
            Date returnPoliceSwipeTime, int dateState, String remark) {
        this.id = id;
        this.zdjId = zdjId;
        this.lsId = lsId;
        this.qyId = qyId;
        this.currentDate = currentDate;
        this.state = state;
        this.adminCardNumber = adminCardNumber;
        this.adminSwipeTime = adminSwipeTime;
        this.usePoliceCardNumber = usePoliceCardNumber;
        this.usePoliceSwipeTime = usePoliceSwipeTime;
        this.driverCardNumber = driverCardNumber;
        this.driverSwipeTime = driverSwipeTime;
        this.carNumber = carNumber;
        this.carType = carType;
        this.driverName = driverName;
        this.driverSex = driverSex;
        this.entryReasons = entryReasons;
        this.installPoliceCardNumber = installPoliceCardNumber;
        this.installPoliceSwipeTime = installPoliceSwipeTime;
        this.lockPoliceCardNumber = lockPoliceCardNumber;
        this.lockPoliceSwipeTime = lockPoliceSwipeTime;
        this.openPoliceCardNumber = openPoliceCardNumber;
        this.openPoliceSwipeTime = openPoliceSwipeTime;
        this.returnPoliceCardNumber = returnPoliceCardNumber;
        this.returnPoliceSwipeTime = returnPoliceSwipeTime;
        this.dateState = dateState;
        this.remark = remark;
    }

    @Generated(hash = 75411764)
    public CarTravelRecord() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLsId() {
        return this.lsId;
    }

    public void setLsId(int lsId) {
        this.lsId = lsId;
    }

    public int getQyId() {
        return this.qyId;
    }

    public void setQyId(int qyId) {
        this.qyId = qyId;
    }

    public Date getCurrentDate() {
        return this.currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAdminCardNumber() {
        return this.adminCardNumber;
    }

    public void setAdminCardNumber(String adminCardNumber) {
        this.adminCardNumber = adminCardNumber;
    }

    public Date getAdminSwipeTime() {
        return this.adminSwipeTime;
    }

    public void setAdminSwipeTime(Date adminSwipeTime) {
        this.adminSwipeTime = adminSwipeTime;
    }

    public String getUsePoliceCardNumber() {
        return this.usePoliceCardNumber;
    }

    public void setUsePoliceCardNumber(String usePoliceCardNumber) {
        this.usePoliceCardNumber = usePoliceCardNumber;
    }

    public Date getUsePoliceSwipeTime() {
        return this.usePoliceSwipeTime;
    }

    public void setUsePoliceSwipeTime(Date usePoliceSwipeTime) {
        this.usePoliceSwipeTime = usePoliceSwipeTime;
    }

    public String getDriverCardNumber() {
        return this.driverCardNumber;
    }

    public void setDriverCardNumber(String driverCardNumber) {
        this.driverCardNumber = driverCardNumber;
    }

    public Date getDriverSwipeTime() {
        return this.driverSwipeTime;
    }

    public void setDriverSwipeTime(Date driverSwipeTime) {
        this.driverSwipeTime = driverSwipeTime;
    }

    public String getCarNumber() {
        return this.carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarType() {
        return this.carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverSex() {
        return this.driverSex;
    }

    public void setDriverSex(String driverSex) {
        this.driverSex = driverSex;
    }

    public String getEntryReasons() {
        return this.entryReasons;
    }

    public void setEntryReasons(String entryReasons) {
        this.entryReasons = entryReasons;
    }

    public String getInstallPoliceCardNumber() {
        return this.installPoliceCardNumber;
    }

    public void setInstallPoliceCardNumber(String installPoliceCardNumber) {
        this.installPoliceCardNumber = installPoliceCardNumber;
    }

    public Date getInstallPoliceSwipeTime() {
        return this.installPoliceSwipeTime;
    }

    public void setInstallPoliceSwipeTime(Date installPoliceSwipeTime) {
        this.installPoliceSwipeTime = installPoliceSwipeTime;
    }

    public String getLockPoliceCardNumber() {
        return this.lockPoliceCardNumber;
    }

    public void setLockPoliceCardNumber(String lockPoliceCardNumber) {
        this.lockPoliceCardNumber = lockPoliceCardNumber;
    }

    public Date getLockPoliceSwipeTime() {
        return this.lockPoliceSwipeTime;
    }

    public void setLockPoliceSwipeTime(Date lockPoliceSwipeTime) {
        this.lockPoliceSwipeTime = lockPoliceSwipeTime;
    }

    public String getOpenPoliceCardNumber() {
        return this.openPoliceCardNumber;
    }

    public void setOpenPoliceCardNumber(String openPoliceCardNumber) {
        this.openPoliceCardNumber = openPoliceCardNumber;
    }

    public Date getOpenPoliceSwipeTime() {
        return this.openPoliceSwipeTime;
    }

    public void setOpenPoliceSwipeTime(Date openPoliceSwipeTime) {
        this.openPoliceSwipeTime = openPoliceSwipeTime;
    }

    public String getReturnPoliceCardNumber() {
        return this.returnPoliceCardNumber;
    }

    public void setReturnPoliceCardNumber(String returnPoliceCardNumber) {
        this.returnPoliceCardNumber = returnPoliceCardNumber;
    }

    public Date getReturnPoliceSwipeTime() {
        return this.returnPoliceSwipeTime;
    }

    public void setReturnPoliceSwipeTime(Date returnPoliceSwipeTime) {
        this.returnPoliceSwipeTime = returnPoliceSwipeTime;
    }

    public int getDateState() {
        return this.dateState;
    }

    public void setDateState(int dateState) {
        this.dateState = dateState;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getZdjId() {
        return this.zdjId;
    }

    public void setZdjId(int zdjId) {
        this.zdjId = zdjId;
    }
}
