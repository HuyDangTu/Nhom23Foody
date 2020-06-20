package hcmute.edu.vn.foody_23;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "Foody.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}