package com.empire.android.marvelpedia.api;

import com.empire.android.marvelpedia.data.Character;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelApi {

    @GET("characters")
    Observable<Character.JsonResponse> getCharacters(@Query("apikey") String apikey, @Query("ts") String timestamp, @Query("hash") String hash, @Query("offset") int offset);

}
