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
    public Observable<Comic.JsonResponse> getComics(int offset, String searchQuery, Long characterId) {
        ApiAuth apiAuth = new ApiAuth();
        return marvelApi.getComics(offset, characterId, searchQuery, ApiAuth.PUBLIC_KEY, apiAuth.getTimestamp(), apiAuth.getHash());
    }
}
