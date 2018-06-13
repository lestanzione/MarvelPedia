package com.empire.android.marvelpedia.comic;

import com.empire.android.marvelpedia.api.MarvelApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicModule {

    @Provides
    @Singleton
    ComicContract.Presenter providesPresenter(ComicContract.Repository repository){
        ComicPresenter presenter = new ComicPresenter(repository);
        return presenter;
    }

    @Provides
    @Singleton
    ComicContract.Repository providesRepository(MarvelApi marvelApi){
        ComicRepository repository = new ComicRepository(marvelApi);
        return repository;
    }

}
