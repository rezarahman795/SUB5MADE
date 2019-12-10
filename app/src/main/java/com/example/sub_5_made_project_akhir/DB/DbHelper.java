package com.example.sub_5_made_project_akhir.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.MOVIE_ID;
import static com.example.sub_5_made_project_akhir.DB.DbContract.TABLE_NAME;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_entertainment";
    private static final int DB_VERSION = 100;
    private static SQLiteDatabase sqLiteDatabase_;
    private static DbHelper dbHelper;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME,
            DbContract.KatalogColumn._ID,
            MOVIE_ID,
            DbContract.KatalogColumn.TYPE,
            DbContract.KatalogColumn.DATE,
            DbContract.KatalogColumn.RATE,
            DbContract.KatalogColumn.OVERVIEW,
            DbContract.KatalogColumn.TITLE,
            DbContract.KatalogColumn.IMAGE,
            DbContract.KatalogColumn.BACKDROP_IMAGE
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void open()throws SQLException {
        sqLiteDatabase_ = dbHelper.getWritableDatabase();
    }

    public static DbHelper getInstance(Context context){
        if (dbHelper == null){
            synchronized (SQLiteOpenHelper.class){
                if (dbHelper == null){
                    dbHelper = new DbHelper(context);
                }
            }
        }
        return dbHelper;
    }

    public void close() {
        if (sqLiteDatabase_.isOpen())
            sqLiteDatabase_.close();
    }

    public int delete(int id) {
        return sqLiteDatabase_.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public Cursor cursorOnly() {
        return sqLiteDatabase_.query(TABLE_NAME
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public Cursor cursorById(String id) {
        return sqLiteDatabase_.query(TABLE_NAME, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public boolean FlagFavourite(String id) throws SQLException {
        boolean isFavorite = false;
        sqLiteDatabase_ = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase_.rawQuery("SELECT * from " + TABLE_NAME
                + " where " + MOVIE_ID + "=?", new String[]{id});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                isFavorite = true;
            } while (cursor.moveToNext());
        }
        assert cursor != null;
        cursor.close();
        return isFavorite;
    }

    public long insertProvider(ContentValues values) {
        return sqLiteDatabase_.insert(TABLE_NAME, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return sqLiteDatabase_.update(TABLE_NAME, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return sqLiteDatabase_.delete(TABLE_NAME, _ID + " = ?", new String[]{id});
    }

}
