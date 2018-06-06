package com.empire.android.marvelpedia.character;

import com.empire.android.marvelpedia.api.MarvelApi;
import com.empire.android.marvelpedia.comic.ComicContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CharacterModule {

    @Provides
    @Singleton
    CharacterContract.Presenter providesPresenter(CharacterContract.Repository characterRepository, ComicContract.Repository comicRepository){
        CharacterPresenter presenter = new CharacterPresenter(characterRepository, comicRepository);
        return presenter;
    }

    @Provides
    @Singleton
    CharacterContract.Repository providesRepository(MarvelApi marvelApi){
        CharacterRepository repository = new CharacterRepository(marvelApi);
        return repository;
    }

}
