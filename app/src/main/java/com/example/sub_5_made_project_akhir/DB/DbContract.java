package com.example.sub_5_made_project_akhir.DB;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DbContract {

    public static final String AUTHORITY = "com.example.sub_5_made_project_akhir";
    private static final String SCHEME = "content";

    private DbContract() {
    }


    public static final class KatalogColumn implements BaseColumns {
        public static String TABLE_NAME = "entertainment";
        public final static String MOVIE_ID = "id";
        public final static String TV_ID = "id";

        public static String TYPE = "type";
        public static String DATE = "date";
        public static String RATE = "rating";
        public static String OVERVIEW = "overview";
        public static String TITLE = "title";
        public static String IMAGE = "image";
        public static String BACKDROP_IMAGE = "backdrop_image";

        public static final Uri CONTENT_URI_ENTERTAINMENT = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
