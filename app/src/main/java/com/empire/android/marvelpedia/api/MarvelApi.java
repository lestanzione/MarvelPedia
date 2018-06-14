package com.empire.android.marvelpedia.api;

import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Comic;
import com.empire.android.marvelpedia.data.Serie;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelApi {

    @GET("characters")
    Observable<Character.JsonResponse> getCharacters(@Query("offset") int offset,
                                                     @Query("comics") Long comicId,
                                                     @Query("nameStartsWith") String nameStartsWith,
                                                     @Query("apikey") String apikey,
                                                     @Query("ts") String timestamp,
                                                     @Query("hash") String hash);

    @GET("characters/{characterId}")
    Observable<Character.JsonResponse> getCharacterById(@Path("characterId") long characterId,
                                                        @Query("apikey") String apikey,
                                                        @Query("ts") String timestamp,
                                                        @Query("hash") String hash);

    @GET("characters/{characterId}/comics?orderBy=-issueNumber")
    Observable<Comic.JsonResponse> getComicsByCharacterId(@Path("characterId") long characterId,
                                                          @Query("offset") int offset,
                                                          @Query("apikey") String apikey,
                                                          @Query("ts") String timestamp,
                                                          @Query("hash") String hash);

    @GET("characters/{characterId}/series?orderBy=-modified")
    Observable<Serie.JsonResponse> getSeriesByCharacterId(@Path("characterId") long characterId,
                                                          @Query("apikey") String apikey,
                                                          @Query("ts") String timestamp,
                                                          @Query("hash") String hash);

    @GET("comics/{comicId}")
    Observable<Comic.JsonResponse> getComicById(@Path("comicId") long comicId,
                                                @Query("apikey") String apikey,
                                                @Query("ts") String timestamp,
                                                @Query("hash") String hash);

    @GET("comics/{comicId}/characters")
    Observable<Character.JsonResponse> getCharactersByComicId(@Path("comicId") long comicId,
                                                              @Query("offset") int offset,
                                                              @Query("apikey") String apikey,
                                                              @Query("ts") String timestamp,
                                                              @Query("hash") String hash);

}
