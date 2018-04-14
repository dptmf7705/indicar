package com.indicar.indicar_community.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yeseul on 2018-04-13.
 */

public interface BaseModel<T> {

    void getDataList(HashMap<String, String> map, LoadDataListCallBack callBack);

    void getData(HashMap<String, String> map, LoadDataCallBack callBack);

    void insertDataList(HashMap<String, String> map);

    void insertData(HashMap<String, String> map);

    interface LoadDataListCallBack<T> {

        void onDataListLoaded(List<T> list);

        void onDataNotAvailable();
    }

    interface LoadDataCallBack<T> {

        void onDataLoaded(T data);

        void onDataNotAvailable();
    }
}
