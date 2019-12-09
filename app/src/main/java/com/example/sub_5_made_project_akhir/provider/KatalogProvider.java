package com.example.sub_5_made_project_akhir.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.sub_5_made_project_akhir.DB.DbHelper;
import java.util.Objects;
import static com.example.sub_5_made_project_akhir.DB.DbContract.AUTHORITY;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.CONTENT_URI_ENTERTAINMENT;
import static com.example.sub_5_made_project_akhir.DB.DbContract.KatalogColumn.TABLE_NAME;

public class KatalogProvider extends ContentProvider {
    private static final int ENTERTAINMENT = 100;
    private static final int ENTERTAINMENT_ID = 101;
    private static final String DB_NAME_ = TABLE_NAME;
    private static DbHelper dbHelper_;


    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DB_NAME_, ENTERTAINMENT);
        URI_MATCHER.addURI(AUTHORITY, DB_NAME_ + "/#", ENTERTAINMENT_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper_ = DbHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        dbHelper_.open();
        Cursor cursorQuery;
        switch (URI_MATCHER.match(uri)) {
            case ENTERTAINMENT:
                cursorQuery = dbHelper_.cursorOnly();
                break;
            case ENTERTAINMENT_ID:
                cursorQuery = dbHelper_.cursorById(uri.getLastPathSegment());
                break;
            default:
                cursorQuery = null;
                break;
        }
        return cursorQuery;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        dbHelper_.open();
        long added;
        if (URI_MATCHER.match(uri) == ENTERTAINMENT) {
            added = dbHelper_.insertProvider(values);
        } else {
            added = 0;
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_ENTERTAINMENT, null);
        return Uri.parse(CONTENT_URI_ENTERTAINMENT + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        dbHelper_.open();
        int deleted;
        if (URI_MATCHER.match(uri) == ENTERTAINMENT_ID) {
            deleted = dbHelper_.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_ENTERTAINMENT, null);
        return deleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        dbHelper_.open();
        int updated;
        if (URI_MATCHER.match(uri) == ENTERTAINMENT_ID) {
            updated = dbHelper_.updateProvider(uri.getLastPathSegment(), values);
        } else {
            updated = 0;
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_ENTERTAINMENT, null);
        return updated;
    }


}
