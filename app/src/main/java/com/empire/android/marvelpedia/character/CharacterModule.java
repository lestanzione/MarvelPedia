package com.empire.android.marvelpedia.character;

import com.empire.android.marvelpedia.api.MarvelApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CharacterModule {

    @Provides
    @Singleton
    CharacterContract.Presenter providesPresenter(CharacterContract.Repository repository){
        CharacterPresenter presenter = new CharacterPresenter(repository);
        return presenter;
    }

    @Provides
    @Singleton
    CharacterContract.Repository providesRepository(MarvelApi marvelApi){
        CharacterRepository repository = new CharacterRepository(marvelApi);
        return repository;
    }

}
