package com.empire.android.marvelpedia;

import android.app.Application;

import com.empire.android.marvelpedia.character.CharacterModule;
import com.empire.android.marvelpedia.characterlist.CharacterListModule;
import com.empire.android.marvelpedia.di.ApplicationComponent;
import com.empire.android.marvelpedia.di.DaggerApplicationComponent;
import com.empire.android.marvelpedia.di.NetworkModule;
import com.empire.android.marvelpedia.main.MainModule;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule())
                .mainModule(new MainModule())
                .characterListModule(new CharacterListModule())
                .characterModule(new CharacterModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
