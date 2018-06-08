package com.empire.android.marvelpedia.serie;

import com.empire.android.marvelpedia.api.MarvelApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SerieModule {

    @Provides
    @Singleton
    SerieContract.Repository providesRepository(MarvelApi marvelApi){
        SerieRepository repository = new SerieRepository(marvelApi);
        return repository;
    }

}
