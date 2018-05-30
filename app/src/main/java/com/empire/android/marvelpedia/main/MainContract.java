package com.empire.android.marvelpedia.main;

public interface MainContract {

    interface View {

    }

    interface Presenter {
        void attachView(MainContract.View view);
    }

}
