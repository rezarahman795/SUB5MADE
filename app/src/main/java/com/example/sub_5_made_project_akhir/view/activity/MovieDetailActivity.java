package com.example.sub_5_made_project_akhir.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sub_5_made_project_akhir.BuildConfig;
import com.example.sub_5_made_project_akhir.DB.DbHelper;
import com.example.sub_5_made_project_akhir.DB.helper.MovieHelper;
import com.example.sub_5_made_project_akhir.R;
import com.example.sub_5_made_project_akhir.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String GET_DATA_MOVIE = BuildConfig.GET_DATA_MOVIE;
    private Movie dataMovie;
    private DbHelper dbHelperDetailMovie;
    private MovieHelper movieHelper;
    private Boolean flagFavourite;
    private TextView tvNameMovie, tvTglMovie, tvDescMovie, tvGenreMovie;
    private RatingBar rateDetailMovie;
    private ProgressBar pbMovie;
    private ScrollView sViewDetail;
    private ImageView posterMovie, backDropMovie;
    private String movieNameDetail, movieTglDetail, movieDescDetail, imgDetail, imgBackDrop;
    private float rateDetailMovie1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        onBindViewDeclare();
        setDataMovie();

    }

    private void onBindViewDeclare() {

        rateDetailMovie = findViewById(R.id.RATE_MOVIE_detail);
        tvGenreMovie = findViewById(R.id.genre_movie_detail);
        posterMovie = findViewById(R.id.image_detail_movie);
        backDropMovie = findViewById(R.id.backdrop_detail_movie);
        pbMovie = findViewById(R.id.loading_movie_detail);
        sViewDetail = findViewById(R.id.scrollView);
        tvNameMovie = findViewById(R.id.title_detail_movie);
        tvTglMovie = findViewById(R.id.tgl_detail_movie);
        tvDescMovie = findViewById(R.id.detail_description_movie);
    }

    private void setDataMovie() {
        Intent moveIntent = getIntent();
        dataMovie = moveIntent.getParcelableExtra(GET_DATA_MOVIE);
        if (dataMovie != null) {
            Glide.with(this)
                    .load(BuildConfig.BACKDROP_URL + dataMovie.getBackdropPict())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(backDropMovie);

            Glide.with(this)
                    .load(BuildConfig.POSTER_URL + dataMovie.getImageOrigin())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(posterMovie);
            getGenreDataMovie(Integer.parseInt(dataMovie.getMovieID_DETAIL()));
            tvNameMovie.setText(dataMovie.getDetailNameMovie());
            tvTglMovie.setText(dataMovie.getTglMovieDetail());
            tvDescMovie.setText(dataMovie.getDescMovieDetail());
            rateDetailMovie.setRating((float) dataMovie.getStar() / 2);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(dataMovie.getDetailNameMovie());
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_icon, menu);
        dbHelperDetailMovie = DbHelper.getInstance(getApplicationContext());
        if (dbHelperDetailMovie.FlagFavourite(String.valueOf(dataMovie.getMovieID_DETAIL()))) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites));
            menu.getItem(0).setChecked(true);
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites));
            menu.getItem(0).setChecked(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Movie favMovie = new Movie();

        favMovie.setMovieID_DETAIL(dataMovie.getMovieID_DETAIL());
        favMovie.setDescMovieDetail(dataMovie.getDescMovieDetail());
        favMovie.setTglMovieDetail(dataMovie.getTglMovieDetail());
        favMovie.setImageOrigin(dataMovie.getImageOrigin());
        favMovie.setStar((float)dataMovie.getStar()/2);
        favMovie.setBackdropPict(dataMovie.getBackdropPict());

        dbHelperDetailMovie = DbHelper.getInstance(getApplicationContext());
        if (item.getItemId() == R.id.favorite_icon) {
            if (item.isChecked()) {
                item.setChecked(false);
                dataMovie.setType("entertainment");
                dbHelperDetailMovie.open();
                long checkResult = movieHelper.deleteMovie(dataMovie.getMovieID_DETAIL());
                System.out.println("result insert : "+checkResult);
                dbHelperDetailMovie.close();
                if (checkResult > 0) {
                    item.setIcon(R.drawable.ic_add_to_favorites);
                    Toast.makeText(getApplicationContext(), "Success " + dataMovie.getDetailNameMovie() + " Add from Favorite", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed " + dataMovie.getDetailNameMovie() + " Add to Favorite", Toast.LENGTH_LONG).show();
                }
            } else {
                item.setChecked(true);
                dataMovie.setType("entertainment");
                dbHelperDetailMovie.open();
                long checkResult = movieHelper.insertMovie(favMovie);
                System.out.println(checkResult);
                dbHelperDetailMovie.close();
                if (checkResult > 0) {
                    item.setIcon(R.drawable.ic_added_to_favorites);
                    Toast.makeText(getApplicationContext(), "Success " + dataMovie.getDetailNameMovie() + " Delete from Favorite", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed " + dataMovie.getDetailNameMovie() + " Delete to Favorite", Toast.LENGTH_LONG).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void getGenreDataMovie(int idMovie) {
        String url = BuildConfig.GENRE_URL + idMovie + "?api_key=" + BuildConfig.APP_KEY + "&language=en-US";

        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pbMovie.setVisibility(View.GONE);
                sViewDetail.setVisibility(View.VISIBLE);
                try {
                    String responeAPI = new String(responseBody);
                    JSONObject objectAPI = new JSONObject(responeAPI);

                    JSONArray jsonArray = objectAPI.getJSONArray("genres");
                    List<String> genreList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String genreName = jsonObject.getString("name");
                        genreList.add(genreName);
                    }
                    String genres = TextUtils.join(", ", genreList);
                    tvGenreMovie.setText(genres);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("onFailure", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }
}
