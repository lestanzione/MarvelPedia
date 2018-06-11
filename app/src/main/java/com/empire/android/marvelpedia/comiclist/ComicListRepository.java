package com.empire.android.marvelpedia.comiclist;

import com.empire.android.marvelpedia.api.ApiAuth;
import com.empire.android.marvelpedia.api.MarvelApi;
import com.empire.android.marvelpedia.data.Comic;

import io.reactivex.Observable;

public class ComicListRepository implements ComicListContract.Repository {

    private MarvelApi marvelApi;

    public ComicListRepository(MarvelApi marvelApi){
        this.marvelApi = marvelApi;
    }

    @Override
    public Observable<Comic.JsonResponse> getComics(long characterId, int offset, String searchQuery) {
        ApiAuth apiAuth = new ApiAuth();
        return marvelApi.getComicsByCharacterId(characterId, 0, ApiAuth.PUBLIC_KEY, apiAuth.getTimestamp(), apiAuth.getHash());
    }
}
