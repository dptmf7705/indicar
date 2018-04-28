package com.indicar.indicar_community.data.dao;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yeseul on 2018-04-13.
 */

public interface BaseDao<T> {

    void getDataList(HashMap<String, String> map, LoadDataListCallBack callBack);

    void getData(HashMap<String, String> map, LoadDataCallBack callBack);

    void insertData(HashMap<String, Object> map, LoadDataCallBack callBack);

    void updateData(HashMap<String, Object> map, LoadDataCallBack callBack);

    void deleteData(HashMap<String, Object> map, LoadDataCallBack callBack);

    interface LoadDataListCallBack<T> {

        void onDataListLoaded(List<T> list);

        void onDataNotAvailable();
    }

    interface LoadDataCallBack<T> {

        void onDataLoaded(T data);

        void onDataNotAvailable();
    }
}
