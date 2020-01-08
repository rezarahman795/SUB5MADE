package com.example.sub_5_made_project_akhir.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sub_5_made_project_akhir.BuildConfig;
import com.example.sub_5_made_project_akhir.DB.DbHelper;
import com.example.sub_5_made_project_akhir.DB.helper.TVhelper;
import com.example.sub_5_made_project_akhir.model.TV;
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

public class TvViewModel extends AndroidViewModel {
    private DbHelper dbHelperTv;
    private TVhelper tVhelper;
    private RequestRetrofit api_helper;
    private MutableLiveData<ArrayList<TV>> listTVViewModel = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TV>> listFavouriteTVViewModel = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TV>> listTrailerTVViewModel = new MutableLiveData<>();

    public void setSearchTV(String keywordTV) {
        api_helper = RetrofitClient.getRetrofitClient().create(RequestRetrofit.class);
        try {
            final ArrayList<TV> listSearchTvItems = new ArrayList<>();
            Call<String> API_USER = api_helper.getSearchTV(BuildConfig.APP_KEY, "en-US", keywordTV);
            API_USER.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObjectSearchTv = new JSONObject(Objects.requireNonNull(response.body()));
                            JSONArray listSearchJsonArray = jsonObjectSearchTv.getJSONArray("results");
                            for (int a = 0; a < listSearchJsonArray.length(); a++) {
                                JSONObject listJsonObjectSearchTV = listSearchJsonArray.getJSONObject(a);
                                TV searchTV = new TV(listJsonObjectSearchTV);
                                listSearchTvItems.add(searchTV);
                            }
                            listTVViewModel.postValue(listSearchTvItems);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("onFailure Json", Objects.requireNonNull(e.getMessage()));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("OnFailure Search", Objects.requireNonNull(e.getMessage()));
        }
    }

    public void setRequestTV() {
        api_helper = RetrofitClient.getRetrofitClient().create(RequestRetrofit.class);
        try {
            final ArrayList<TV> listRequestTVItems = new ArrayList<>();
            Call<String> API_USER = api_helper.getDiscoverTv(BuildConfig.APP_KEY, "en-US");
            API_USER.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()));
                            JSONArray listSetRequestTV = jsonObject.getJSONArray("results");
                            for (int i = 0; i < listSetRequestTV.length(); i++) {
                                JSONObject setRequestTV = listSetRequestTV.getJSONObject(i);
                                TV tvRequest = new TV(setRequestTV);
                                listRequestTVItems.add(tvRequest);
                            }
                            listTVViewModel.postValue(listRequestTVItems);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("onFailure Json", Objects.requireNonNull(e.getMessage()));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("onFailure Movies ", Objects.requireNonNull(e.getMessage()));
        }
    }

    public void setTrailerTV() {
        api_helper = RetrofitClient.getRetrofitClient().create(RequestRetrofit.class);
        try {
            final ArrayList<TV> listRequestTrailerTV = new ArrayList<>();
            Call<String> API_USER = api_helper.getTrailerTv(BuildConfig.APP_KEY, "en-US");
            API_USER.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()));
                            JSONArray listSetRequestTrailerTV = jsonObject.getJSONArray("results");
                            for (int i = 0; i < listSetRequestTrailerTV.length(); i++) {
                                JSONObject setRequestTrailerTV = listSetRequestTrailerTV.getJSONObject(i);
                                TV movie = new TV(setRequestTrailerTV);
                                listRequestTrailerTV.add(movie);
                            }
                            listTrailerTVViewModel.postValue(listRequestTrailerTV);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("onFailure Json", Objects.requireNonNull(e.getMessage()));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("onFailure Movies ", Objects.requireNonNull(e.getMessage()));
        }
    }


    public TvViewModel(@NonNull Application application) {
        super(application);
        this.tVhelper = new TVhelper(application);
        this.dbHelperTv = DbHelper.getInstance(application);
    }

    public void setDBFavTv(String type) {
        ArrayList<TV> dbFavTV = tVhelper.getListFavoriteTV(type);
        listFavouriteTVViewModel.postValue(dbFavTV);
    }

    public LiveData<ArrayList<TV>> getTvFavourite(String type) {
        setDBFavTv(type);
        return listFavouriteTVViewModel;
    }

    public LiveData<ArrayList<TV>> getTrailerTV() {
        return listTrailerTVViewModel;
    }

    public LiveData<ArrayList<TV>> getRequestTV() {
        return listTVViewModel;
    }

    public LiveData<ArrayList<TV>>getSearchTV(){
        return listTVViewModel;
    }
}
