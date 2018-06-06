package com.empire.android.marvelpedia.api;

import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelApi {

    @GET("characters")
    Observable<Character.JsonResponse> getCharacters(@Query("offset") int offset,
                                                     @Query("nameStartsWith") String nameStartsWith,
                                                     @Query("apikey") String apikey,
                                                     @Query("ts") String timestamp,
                                                     @Query("hash") String hash);

    @GET("characters/{characterId}")
    Observable<Character.JsonResponse> getCharacterById(@Path("characterId") long characterId,
                                                        @Query("apikey") String apikey,
                                                        @Query("ts") String timestamp,
                                                        @Query("hash") String hash);

    @GET("characters/{characterId}/comics")
    Observable<Comic.JsonResponse> getComicsByCharacterId(@Path("characterId") long characterId,
                                                          @Query("apikey") String apikey,
                                                          @Query("ts") String timestamp,
                                                          @Query("hash") String hash);

}
