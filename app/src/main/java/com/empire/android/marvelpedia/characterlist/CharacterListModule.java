package com.empire.android.marvelpedia.characterlist;

import com.empire.android.marvelpedia.api.MarvelApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CharacterListModule {

    @Provides
    @Singleton
    CharacterListContract.Presenter providesPresenter(CharacterListContract.Repository repository){
        CharacterListPresenter presenter = new CharacterListPresenter(repository);
        return presenter;
    }

    @Provides
    @Singleton
    CharacterListContract.Repository providesRepository(MarvelApi marvelApi){
        CharacterListRepository repository = new CharacterListRepository(marvelApi);
        return repository;
    }

}
