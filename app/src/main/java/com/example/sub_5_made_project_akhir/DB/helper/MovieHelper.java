package com.example.sub_5_made_project_akhir.DB.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sub_5_made_project_akhir.DB.DbHelper;
import com.example.sub_5_made_project_akhir.model.Movie;


import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.MOVIE_ID;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.BACKDROP_IMAGE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.DATE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.IMAGE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.OVERVIEW;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.RATE;

import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.TITLE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.TYPE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.TABLE_NAME;

public class MovieHelper {

    private static final String DB_TABLE = TABLE_NAME;
    private static DbHelper databaseHelper;
    private static SQLiteDatabase sqLiteDatabase;

    public MovieHelper(Context context) {

        databaseHelper = new DbHelper(context);
    }
    public ArrayList<Movie> getListFavoriteMovie(String type) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DB_TABLE,
                new String[]{_ID, TITLE, TYPE, OVERVIEW, RATE, DATE, IMAGE, BACKDROP_IMAGE},
                TYPE + "=?",
                new String[]{type},
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setMovieID_DETAIL(cursor.getString(cursor.getColumnIndexOrThrow(_ID)));
                movie.setDetailNameMovie(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                movie.setDescMovieDetail(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setStar(cursor.getDouble(cursor.getColumnIndexOrThrow(RATE)));
                movie.setTglMovieDetail(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setImageOrigin(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                movie.setBackdropPict(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_IMAGE)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public long insertMovie(Movie movie) {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getMovieID_DETAIL());
        args.put(MOVIE_ID, movie.getMovieID_DETAIL());
        args.put(TITLE, movie.getDetailNameMovie());
        args.put(TYPE, movie.getType());
        args.put(OVERVIEW, movie.getDescMovieDetail());
        args.put(RATE, movie.getStar());
        args.put(DATE, movie.getTglMovieDetail());
        args.put(IMAGE, movie.getImageOrigin());
        args.put(BACKDROP_IMAGE, movie.getBackdropPict());
        return sqLiteDatabase.insert(DB_TABLE, null, args);
    }


    public int deleteMovie(String id) {
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        return sqLiteDatabase.delete(DB_TABLE, _ID + " = '" + id + "'", null);
    }

}
