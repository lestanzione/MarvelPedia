package com.empire.android.marvelpedia.di;

import com.empire.android.marvelpedia.character.CharacterActivity;
import com.empire.android.marvelpedia.character.CharacterModule;
import com.empire.android.marvelpedia.characterlist.CharacterListActivity;
import com.empire.android.marvelpedia.characterlist.CharacterListModule;
import com.empire.android.marvelpedia.comic.ComicModule;
import com.empire.android.marvelpedia.main.MainActivity;
import com.empire.android.marvelpedia.main.MainModule;
import com.empire.android.marvelpedia.serie.SerieModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                NetworkModule.class,
                MainModule.class,
                CharacterListModule.class,
                CharacterModule.class,
                ComicModule.class,
                SerieModule.class
        }
)
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(CharacterListActivity activity);
    void inject(CharacterActivity activity);
}
