package com.example.sub_5_made_project_akhir.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sub_5_made_project_akhir.BuildConfig;
import com.example.sub_5_made_project_akhir.DB.DbHelper;
import com.example.sub_5_made_project_akhir.DB.helper.MovieHelper;
import com.example.sub_5_made_project_akhir.model.Movie;
import com.example.sub_5_made_project_akhir.third_party.RequestRetrofit;
import com.example.sub_5_made_project_akhir.third_party.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends AndroidViewModel {
    private final DbHelper dbHelper;
    private MovieHelper movieHelper;
    private RequestRetrofit api_helper;
    private MutableLiveData<ArrayList<Movie>> listMoviesViewModel = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> listTrailerMoviesViewModel = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> listFavMoviesViewModel = new MutableLiveData<>();


    public void setTrailersMovies() {

        api_helper = RetrofitClient.getRetrofitClient().create(RequestRetrofit.class);
        try {
            final ArrayList<Movie> listTrailerMoviesItem = new ArrayList<>();
            Call<String> API_USER = api_helper.getTrailerMovie(BuildConfig.APP_KEY, "en-US");
            API_USER.enqueue(new Callback<String>() {
                @Override
                public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {

                }

                @Override
                public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()));
                            JSONArray listTrailerMovie = jsonObject.getJSONArray("results");
                            for (int i = 0; i < listTrailerMovie.length(); i++) {
                                JSONObject trailerMovie = listTrailerMovie.getJSONObject(i);
                                Movie movie = new Movie(trailerMovie);
                                listTrailerMoviesItem.add(movie);
                            }
                            listTrailerMoviesViewModel.postValue(listTrailerMoviesItem);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("onFailure Json", Objects.requireNonNull(e.getMessage()));
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("onFailure Trailer ", Objects.requireNonNull(e.getMessage()));
        }
    }

    public void setRequestMovie() {
        api_helper = RetrofitClient.getRetrofitClient().create(RequestRetrofit.class);
        try {
            final ArrayList<Movie> listRequestMoviesItem = new ArrayList<>();
            Call<String> API_USER = api_helper.getDiscoverMovie(BuildConfig.APP_KEY, "en-US");
            API_USER.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()));
                            JSONArray listSetRequestMovie = jsonObject.getJSONArray("results");
                            for (int i = 0; i < listSetRequestMovie.length(); i++) {
                                JSONObject setRequestMovie = listSetRequestMovie.getJSONObject(i);
                                Movie movie = new Movie(setRequestMovie);
                                listRequestMoviesItem.add(movie);
                            }
                            listMoviesViewModel.postValue(listRequestMoviesItem);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("onFailure Json", Objects.requireNonNull(e.getMessage()));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("onFailure Movies ", Objects.requireNonNull(e.getMessage()));
        }
    }

    public void setSearchMovie(String keyword) {
        api_helper = RetrofitClient.getRetrofitClient().create(RequestRetrofit.class);
        try {
            final ArrayList<Movie> listRequestSearchMovie = new ArrayList<>();
            Call<String> API_USER = api_helper.getSearchMovie(BuildConfig.APP_KEY, "en-US", keyword);
            API_USER.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()));
                            JSONArray listSetSearchMovie = jsonObject.getJSONArray("results");
                            for (int i = 0; i < listSetSearchMovie.length(); i++) {
                                JSONObject setSearchMovie = listSetSearchMovie.getJSONObject(i);
                                Movie movie = new Movie(setSearchMovie);
                                listRequestSearchMovie.add(movie);
                            }
                            listMoviesViewModel.postValue(listRequestSearchMovie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("onFailure Json", Objects.requireNonNull(e.getMessage()));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public MovieViewModel(@NonNull Application application) {
        super(application);
        this.dbHelper = DbHelper.getInstance(application);
        this.movieHelper = new MovieHelper(application);

    }

    public void setDBFavMovies(String type) {
        ArrayList<Movie> dbFavMovies = movieHelper.getListFavoriteMovie(type);
        listFavMoviesViewModel.postValue(dbFavMovies);
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMoviesViewModel;
    }

    public LiveData<ArrayList<Movie>> getSearchMovies() {
        return listMoviesViewModel;
    }

    public LiveData<ArrayList<Movie>> getTrailerMovie() {
        return listTrailerMoviesViewModel;
    }

    public LiveData<ArrayList<Movie>> getMovieFavourite(String type) {
        setDBFavMovies(type);
        return listFavMoviesViewModel;
    }


}
