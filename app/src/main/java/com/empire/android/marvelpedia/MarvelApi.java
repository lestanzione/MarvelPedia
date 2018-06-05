package com.empire.android.marvelpedia;

import com.empire.android.marvelpedia.data.Character;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelApi {

    String PUBLIC_KEY = "0b72e793ddaf90a3c2f2edae5d3dd02f";
    String PRIVATE_KEY = "6fad8353778938a3ac83c2bf6253248056be447b";

    @GET("characters")
    Observable<Character.JsonResponse> getCharacters(@Query("apikey") String apikey, @Query("ts") String timestamp, @Query("hash") String hash, @Query("offset") int offset);

}
