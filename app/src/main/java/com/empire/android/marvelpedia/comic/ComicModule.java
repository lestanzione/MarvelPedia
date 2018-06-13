package com.empire.android.marvelpedia.comic;

import com.empire.android.marvelpedia.api.MarvelApi;
import com.empire.android.marvelpedia.character.CharacterContract;
import com.empire.android.marvelpedia.serie.SerieContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicModule {

    @Provides
    @Singleton
    ComicContract.Presenter providesPresenter(ComicContract.Repository comicRepository,
                                              CharacterContract.Repository characterRepository){
        ComicPresenter presenter = new ComicPresenter(comicRepository, characterRepository);
        return presenter;
    }

    @Provides
    @Singleton
    ComicContract.Repository providesRepository(MarvelApi marvelApi){
        ComicRepository repository = new ComicRepository(marvelApi);
        return repository;
    }

}
