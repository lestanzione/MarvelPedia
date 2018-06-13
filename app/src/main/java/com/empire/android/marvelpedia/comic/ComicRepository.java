package com.empire.android.marvelpedia.comic;

import com.empire.android.marvelpedia.api.ApiAuth;
import com.empire.android.marvelpedia.api.MarvelApi;
import com.empire.android.marvelpedia.data.Comic;

import io.reactivex.Observable;

public class ComicRepository implements ComicContract.Repository {

    private MarvelApi marvelApi;

    public ComicRepository(MarvelApi marvelApi){
        this.marvelApi = marvelApi;
    }

    @Override
    public Observable<Comic.JsonResponse> getComicById(long comicId) {
        ApiAuth apiAuth = new ApiAuth();
        return marvelApi.getComicById(comicId, ApiAuth.PUBLIC_KEY, apiAuth.getTimestamp(), apiAuth.getHash());
    }

    @Override
    public Observable<Comic.JsonResponse> getComicsByCharacterId(long characterId) {
        ApiAuth apiAuth = new ApiAuth();
        return marvelApi.getComicsByCharacterId(characterId, 0, ApiAuth.PUBLIC_KEY, apiAuth.getTimestamp(), apiAuth.getHash());
    }
}
