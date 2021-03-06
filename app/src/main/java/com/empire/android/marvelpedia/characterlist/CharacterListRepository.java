package com.empire.android.marvelpedia.characterlist;

import com.empire.android.marvelpedia.api.ApiAuth;
import com.empire.android.marvelpedia.api.MarvelApi;
import com.empire.android.marvelpedia.data.Character;

import io.reactivex.Observable;

public class CharacterListRepository implements CharacterListContract.Repository {

    private MarvelApi marvelApi;

    public CharacterListRepository(MarvelApi marvelApi){
        this.marvelApi = marvelApi;
    }

    @Override
    public Observable<Character.JsonResponse> getCharacters(int offset, String searchQuery, Long comicId) {
        ApiAuth apiAuth = new ApiAuth();
        return marvelApi.getCharacters(offset, comicId, searchQuery, ApiAuth.PUBLIC_KEY, apiAuth.getTimestamp(), apiAuth.getHash());
    }

}
