package com.empire.android.marvelpedia.comiclist;

import com.empire.android.marvelpedia.api.MarvelApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicListModule {

    @Provides
    @Singleton
    ComicListContract.Presenter providesPresenter(ComicListContract.Repository repository){
        ComicListPresenter presenter = new ComicListPresenter(repository);
        return presenter;
    }

    @Provides
    @Singleton
    ComicListContract.Repository providesRepository(MarvelApi marvelApi){
        ComicListRepository repository = new ComicListRepository(marvelApi);
        return repository;
    }

}
