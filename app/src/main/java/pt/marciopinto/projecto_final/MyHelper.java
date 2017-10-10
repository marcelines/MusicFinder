package pt.marciopinto.projecto_final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marciopinto on 16/06/15.
 */
public class MyHelper extends SQLiteOpenHelper {

    public MyHelper(Context context) {
        super(context, "mydb.sqlite3", null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableSong = "CREATE TABLE song (" +
                "_id INTEGER PRIMARY KEY, " +
                "titulo TEXT, " +
                "artista TEXT, " +
                "duracao TEXT, " +
                "thumb_url TEXT, " +
                "letra TEXT)";

        db.execSQL(createTableSong);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

