package com.empire.android.marvelpedia.main;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    @Singleton
    MainContract.Presenter providesPresenter(){
        MainPresenter presenter = new MainPresenter();
        return presenter;
    }

}
