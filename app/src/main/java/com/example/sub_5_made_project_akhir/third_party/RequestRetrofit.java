package com.example.sub_5_made_project_akhir.third_party;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestRetrofit {

    @GET("discover/movie")
    Call<String> getDiscoverMovie(@Query("api_key") String API_KEY,
                                  @Query("language") String language);

    @GET("search/movie")
    Call<String> getSearchMovie(@Query("api_key") String API_KEY,
                                @Query("language") String language,
                                @Query("query") String keyword);

    @GET("discover/movie")
    Call<String> getRelease(@Query("api_key") String API_KEY,
                            @Query("primary_release_date.gte") String ReleaseDate,
                            @Query("primary_release_date.lte") String TodayDate);

    @GET("movie/now_playing")
    Call<String> getTrailerMovie(@Query("api_key") String API_KEY,
                                    @Query("language") String language);

    @GET("discover/tv")
    Call<String> getDiscoverTv(@Query("api_key") String API_KEY,
                               @Query("language") String language);

    @GET("tv/on_the_air")
    Call<String> getTrailerTv(@Query("api_key") String API_KEY,
                                 @Query("language") String language);

    @GET("search/tv")
    Call<String> getSearchTV(@Query("api_key") String API_KEY,
                                     @Query("language") String language,
                                     @Query("query") String keyword);

}
