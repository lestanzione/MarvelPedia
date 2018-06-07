package com.empire.android.marvelpedia.serie;

import com.empire.android.marvelpedia.api.ApiAuth;
import com.empire.android.marvelpedia.api.MarvelApi;
import com.empire.android.marvelpedia.data.Serie;

import io.reactivex.Observable;

public class SerieRepository implements SerieContract.Repository {

    private MarvelApi marvelApi;

    public SerieRepository(MarvelApi marvelApi){
        this.marvelApi = marvelApi;
    }

    @Override
    public Observable<Serie.JsonResponse> getStoriesByCharacterId(long characterId) {
        ApiAuth apiAuth = new ApiAuth();
        return marvelApi.getSeriesByCharacterId(characterId, ApiAuth.PUBLIC_KEY, apiAuth.getTimestamp(), apiAuth.getHash());
    }
}
