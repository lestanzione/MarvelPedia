package com.empire.android.marvelpedia.di;

import com.empire.android.marvelpedia.characterlist.CharacterListActivity;
import com.empire.android.marvelpedia.characterlist.CharacterListModule;
import com.empire.android.marvelpedia.main.MainActivity;
import com.empire.android.marvelpedia.main.MainModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                MainModule.class,
                CharacterListModule.class
        }
)
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(CharacterListActivity activity);
}
