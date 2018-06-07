package com.empire.android.marvelpedia.serie;

import com.empire.android.marvelpedia.data.Serie;

import io.reactivex.Observable;

public interface SerieContract {

    interface Repository{
        Observable<Serie.JsonResponse> getStoriesByCharacterId(long characterId);
    }

}
