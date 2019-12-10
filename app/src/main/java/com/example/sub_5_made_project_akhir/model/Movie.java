package com.example.sub_5_made_project_akhir.model;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.sub_5_made_project_akhir.DB.DbContract;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static android.provider.BaseColumns._ID;
import static com.example.sub_5_made_project_akhir.DB.DbContract.getColumnDouble;
import static com.example.sub_5_made_project_akhir.DB.DbContract.getColumnString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Data
@SuppressWarnings("unused")
public class Movie implements Parcelable {
    private String descMovieDetail, detailNameMovie, tglMovieDetail, backdropPict, imageOrigin, genre, type, movieID_DETAIL;
    double star;

    public Movie(JSONObject objMovie) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd, mm, yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            String idMovie = objMovie.getString("id");
            String title = objMovie.getString("original_title");
            String toDate = objMovie.getString("release_date");
            double rating = objMovie.getDouble("vote_average");
            String detailDesc = objMovie.getString("overview");
            String poster = objMovie.getString("poster_path");
            String backDrop = objMovie.getString("backdrop_path");

            this.movieID_DETAIL = idMovie;
            this.detailNameMovie = title;
            this.tglMovieDetail = toDate;
            this.descMovieDetail = detailDesc;
            this.imageOrigin = poster;
            this.backdropPict = backDrop;
            this.star = rating;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Movie() {

    }


    public Movie(Cursor cursor) {
        this.movieID_DETAIL = getColumnString(cursor, _ID);
        this.type = getColumnString(cursor, DbContract.KatalogColumn.TYPE);
        this.detailNameMovie = getColumnString(cursor, DbContract.KatalogColumn.TITLE);
        this.tglMovieDetail = getColumnString(cursor, DbContract.KatalogColumn.DATE);
        this.descMovieDetail = getColumnString(cursor, DbContract.KatalogColumn.OVERVIEW);
        this.imageOrigin = getColumnString(cursor, DbContract.KatalogColumn.IMAGE);
        this.backdropPict = getColumnString(cursor, DbContract.KatalogColumn.BACKDROP_IMAGE);
        this.star = getColumnDouble(cursor, DbContract.KatalogColumn.RATE);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movieID_DETAIL);
        dest.writeString(this.type);
        dest.writeString(this.detailNameMovie);
        dest.writeString(this.tglMovieDetail);
        dest.writeString(this.descMovieDetail);
        dest.writeString(this.genre);
        dest.writeString(this.imageOrigin);
        dest.writeString(this.backdropPict);
        dest.writeDouble(this.star);
    }


    protected Movie(Parcel in) {
        this.movieID_DETAIL = in.readString();
        this.type = in.readString();
        this.detailNameMovie = in.readString();
        this.tglMovieDetail = in.readString();
        this.descMovieDetail = in.readString();
        this.genre = in.readString();
        this.imageOrigin = in.readString();
        this.backdropPict = in.readString();
        this.star = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
