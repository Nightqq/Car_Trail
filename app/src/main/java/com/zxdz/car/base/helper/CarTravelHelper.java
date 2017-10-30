package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.CarTravelRecordDao;
import com.zxdz.car.main.model.domain.CarTravelRecord;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by super on 2017/10/19.
 */

public class CarTravelHelper {

    /**
     * 一次完整记录的车辆进入信息
     */
    public static CarTravelRecord carTravelRecord;

    private static CarTravelRecordDao carTravelRecordDao = CardDBUtil.getDaoSession().getCarTravelRecordDao();

    public static CarTravelRecord getCarTravelRecord(Long key) {
        return carTravelRecordDao.load(key);
    }

    public static List<CarTravelRecord> getCarTravelRecordListFromDB() {
        return (ArrayList<CarTravelRecord>) carTravelRecordDao.queryBuilder().list();
    }

    public static void saveCarTravelRecordToDB(CarTravelRecord CarTravelRecord) {
        if (CarTravelRecord != null) {
            carTravelRecordDao.insertOrReplace(CarTravelRecord);
        }
    }

    public static void saveCarTravelRecordListToDB(List<CarTravelRecord> list) {
        if (list != null) {
            for (CarTravelRecord info : list) {
                carTravelRecordDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteCarTravelRecordInDB(CarTravelRecord CarTravelRecord) {
        carTravelRecordDao.delete(CarTravelRecord);
    }

    public static void deleteAllCarTravelRecordList() {
        carTravelRecordDao.deleteAll();
    }

}
