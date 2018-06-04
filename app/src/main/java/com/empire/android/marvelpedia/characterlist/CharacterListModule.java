package com.empire.android.marvelpedia.characterlist;

import com.empire.android.marvelpedia.MarvelApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CharacterListModule {

    @Provides
    @Singleton
    CharacterListContract.Presenter providesPresenter(MarvelApi marvelApi){
        CharacterListPresenter presenter = new CharacterListPresenter(marvelApi);
        return presenter;
    }

}
