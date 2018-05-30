package com.empire.android.marvelpedia.main;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    public MainPresenter(){

    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

}
