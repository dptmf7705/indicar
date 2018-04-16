package com.indicar.indicar_community.viewmodel;

import java.util.Observable;

/**
 * Created by yeseul on 2018-04-14.
 *
 * << Observable Class >>
 * Data 변경시  setChanged() 호출하여 변경사항을 저장하고
 *
 * Observer 에게 변경사항을 알릴 때는 notifyObservers() 를 호출한다.
 *
 * notifyObservers() 호출되기 전 setChanged() 는 여러번 호출될 수 있으며
 *
 * clearChanged() 를 호출하면 모든 setChanged() 가 rollback 된다.
 *
 */

public abstract class BaseViewModel extends Observable implements IBaseViewModel {
    public static final int ACTIVITY_FINISH = 0;
    public static final int ACTIVITY_NEXT = 1;

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

}
