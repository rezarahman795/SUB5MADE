package com.example.sub_5_made_project_akhir.DB.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sub_5_made_project_akhir.DB.DbHelper;
import com.example.sub_5_made_project_akhir.model.TV;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.BACKDROP_IMAGE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.DATE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.IMAGE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.OVERVIEW;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.RATE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.TITLE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.TV_ID;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.TYPE;
import static com.example.sub_5_made_project_akhir.DB.DbContract.TABLE_NAME;

public class TVhelper {
    private static final String DB_TABLE = TABLE_NAME;
    private static DbHelper databaseHelper ;
    private static SQLiteDatabase sqLiteDatabase;

    private TVhelper(Context context){

        databaseHelper = new DbHelper(context);
    }

    public ArrayList<TV> getListFavoriteTV(String type) {
        ArrayList<TV> arrayList = new ArrayList<>();
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
        TV tv;
        if (cursor.getCount() > 0) {
            do {
                tv = new TV();
                tv.setID_TV(cursor.getString(cursor.getColumnIndexOrThrow(_ID)));
                tv.setSerialNameTV(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tv.setType(cursor.getString(cursor.getColumnIndexOrThrow(TYPE)));
                tv.setDescSerial(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                tv.setRateTV(cursor.getDouble(cursor.getColumnIndexOrThrow(RATE)));
                tv.setTglSerial(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                tv.setPictureTV(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                tv.setBackdropPictTV(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_IMAGE)));
                arrayList.add(tv);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public int deleteTV(int id) {
        return sqLiteDatabase.delete(DB_TABLE, _ID + " = '" + id + "'", null);
    }

    public long insertTV(TV tv) {
        ContentValues args = new ContentValues();
        args.put(_ID, tv.getID_TV());
        args.put(TV_ID, tv.getID_TV());
        args.put(TITLE, tv.getSerialNameTV());
        args.put(TYPE, tv.getType());
        args.put(OVERVIEW, tv.getDescSerial());
        args.put(RATE, tv.getRateTV());
        args.put(DATE, tv.getTglSerial());
        args.put(IMAGE, tv.getPictureTV());
        args.put(BACKDROP_IMAGE, tv.getBackdropPictTV());
        return sqLiteDatabase.insert(DB_TABLE, null, args);
    }
}
