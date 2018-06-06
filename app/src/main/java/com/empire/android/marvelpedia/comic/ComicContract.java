package com.empire.android.marvelpedia.comic;

import com.empire.android.marvelpedia.data.Comic;

import io.reactivex.Observable;

public interface ComicContract {

    interface Repository{
        Observable<Comic.JsonResponse> getComicsByCharacterId(long characterId);
    }

}
