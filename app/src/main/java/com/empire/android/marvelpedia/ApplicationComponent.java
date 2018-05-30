package com.empire.android.marvelpedia;

import com.empire.android.marvelpedia.main.MainActivity;
import com.empire.android.marvelpedia.main.MainModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                MainModule.class
        }
)
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
}
