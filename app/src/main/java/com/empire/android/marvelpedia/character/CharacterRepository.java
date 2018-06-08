package com.empire.android.marvelpedia.character;

import com.empire.android.marvelpedia.api.ApiAuth;
import com.empire.android.marvelpedia.api.MarvelApi;
import com.empire.android.marvelpedia.data.Character;

import io.reactivex.Observable;

public class CharacterRepository implements CharacterContract.Repository {

    private MarvelApi marvelApi;

    public CharacterRepository(MarvelApi marvelApi){
        this.marvelApi = marvelApi;
    }

    @Override
    public Observable<Character.JsonResponse> getCharacter(long characterId) {
        ApiAuth apiAuth = new ApiAuth();
        return marvelApi.getCharacterById(characterId, ApiAuth.PUBLIC_KEY, apiAuth.getTimestamp(), apiAuth.getHash());
    }

}
