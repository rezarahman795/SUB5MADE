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
import com.example.sub_5_made_project_akhir.DB.helper.TVhelper;
import com.example.sub_5_made_project_akhir.R;
import com.example.sub_5_made_project_akhir.model.TV;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class SerialTVDetailActivity extends AppCompatActivity {
    public static final String GET_DATA_TV = BuildConfig.GET_DATA;
    private TV dataTV;
    private DbHelper dbHelperDetailTV;
    private TVhelper tvHelper;
    private TextView tvNameTv, tvTglTv, tvDescTv, tvGenreTv;
    private RatingBar rateDetailTv;
    private ProgressBar pbTv;
    private ScrollView sViewTv;
    private ImageView posterTv, backDropTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_tvdetail);

        onBindViewDeclare();
        setDataTV();
    }

    private void onBindViewDeclare() {

        rateDetailTv = findViewById(R.id.RATE_TV_detail);
        tvGenreTv = findViewById(R.id.genre_tv_detail);
        posterTv = findViewById(R.id.image_detail_serial);
        backDropTv = findViewById(R.id.backdrop_detail_serial);
        pbTv= findViewById(R.id.loading_tv_detail);
        sViewTv= findViewById(R.id.scrollViewTV);
        tvNameTv = findViewById(R.id.title_detail_serial);
        tvTglTv = findViewById(R.id.tgl_detail_serial);
        tvDescTv = findViewById(R.id.detail_description_serial);
    }

    private void setDataTV() {
        Intent tvIntent = getIntent();
        tvHelper = new TVhelper(this);
        dataTV = tvIntent.getParcelableExtra(GET_DATA_TV);
        if (dataTV != null) {
            Glide.with(this)
                    .load(BuildConfig.BACKDROP_URL + dataTV.getBackdropPictTV())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(backDropTv);

            Glide.with(this)
                    .load(BuildConfig.POSTER_URL + dataTV.getPictureTV())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(posterTv);
            getGenreDatatv(Integer.parseInt(dataTV.getID_TV()));
            tvNameTv.setText(dataTV.getSerialNameTV());
            tvTglTv.setText(dataTV.getTglSerial());
            tvDescTv.setText(dataTV.getDescSerial());
            rateDetailTv.setRating((float) dataTV.getRateTV() / 2);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(dataTV.getSerialNameTV());
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
    }

    private void getGenreDatatv(int idTv) {
        String url = BuildConfig.GENRE_URL + idTv + "?api_key=" + BuildConfig.APP_KEY + "&language=en-US";

        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pbTv.setVisibility(View.GONE);
                sViewTv.setVisibility(View.VISIBLE);
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
                    tvGenreTv.setText(genres);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_icon, menu);
        dbHelperDetailTV = DbHelper.getInstance(getApplicationContext());
        if (dbHelperDetailTV.FlagFavourite(String.valueOf(dataTV.getID_TV()))) {
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

        if (item.getItemId() == R.id.favorite_icon) {
            if (!item.isChecked()) {
                dataTV.setType("entertainment");
                dbHelperDetailTV.open();
                long checkResult = tvHelper.insertTV(dataTV);
                dbHelperDetailTV.close();
                if (checkResult > 0) {
                    item.setIcon(R.drawable.ic_added_to_favorites);
                    Toast.makeText(getApplicationContext(), "Success " + dataTV.getSerialNameTV() + " Add to Favorite", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed " + dataTV.getSerialNameTV() + " Add to Favorite", Toast.LENGTH_LONG).show();
                }

            } else {
                dataTV.setType("entertainment");
                dbHelperDetailTV.open();
                long checkResult = tvHelper.deleteTV(dataTV.getID_TV());
                dbHelperDetailTV.close();
                if (checkResult > 0) {
                    item.setIcon(R.drawable.ic_add_to_favorites);
                    Toast.makeText(getApplicationContext(), "Success " + dataTV.getSerialNameTV() + " Delete from Favorite", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed " + dataTV.getSerialNameTV() + " Delete from Favorite", Toast.LENGTH_LONG).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
