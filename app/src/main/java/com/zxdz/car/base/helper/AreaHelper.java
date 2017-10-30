package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.AreaInfoDao;
import com.zxdz.car.main.model.domain.AreaInfo;
import com.zxdz.car.main.model.domain.CardInfo;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Lenovo on 2017/10/30.
 */

public class AreaHelper {
    private static AreaInfoDao areaInfoDao = CardDBUtil.getDaoSession().getAreaInfoDao();

    public static Observable<ArrayList<AreaInfo>> addCardInfo(final AreaInfo areaInfo) {
        return Observable.just("").subscribeOn(Schedulers.io()).map(new Func1<String, ArrayList<AreaInfo>>() {
            @Override
            public ArrayList<AreaInfo> call(String s) {
                areaInfoDao.insert(areaInfo);
                return (ArrayList<AreaInfo>) areaInfoDao.queryBuilder().list();
            }
        });
    }
    public static Observable<ArrayList<AreaInfo>> carInfoList() {
        return Observable.just("").subscribeOn(Schedulers.io()).map(new Func1<String, ArrayList<AreaInfo>>() {
            @Override
            public ArrayList<AreaInfo> call(String s) {
                return (ArrayList<AreaInfo>) areaInfoDao.queryBuilder().list();
            }
        });
    }

    public static List<AreaInfo> getAreaInfoListFromDB() {
        return (ArrayList<AreaInfo>) areaInfoDao.queryBuilder().list();
    }



    public static void saveAreaInfoToDB(AreaInfo areaInfo) {
        if (areaInfo != null) {
            areaInfoDao.insertOrReplace(areaInfo);
        }
    }
    public static void saveAreaInfoListToDB(List<AreaInfo> list) {
        if (list != null) {
            for (AreaInfo info : list) {
                areaInfoDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteAreaInfoInDB(AreaInfo areaInfo) {
        areaInfoDao.delete(areaInfo);
    }
}
