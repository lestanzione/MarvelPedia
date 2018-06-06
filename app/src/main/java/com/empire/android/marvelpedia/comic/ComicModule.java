package com.empire.android.marvelpedia.comic;

import com.empire.android.marvelpedia.api.MarvelApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicModule {

    @Provides
    @Singleton
    ComicContract.Repository providesRepository(MarvelApi marvelApi){
        ComicRepository repository = new ComicRepository(marvelApi);
        return repository;
    }

}
