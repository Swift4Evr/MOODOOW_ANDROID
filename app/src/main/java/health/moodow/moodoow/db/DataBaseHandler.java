package health.moodow.moodoow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 13;
    public static final String TABLE_DROP_FILM = "DROP TABLE IF EXISTS save;";


    // Commande sql pour la création de la base de données
    private static final String SAVE_CREATE = "create table save (id integer primary key autoincrement, " +
            "date varchar(200), hour integer(2), smile integer(10), mouep integer(10), " +
            "bad integer(10), moy_hour integer(2));";

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SAVE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP_FILM);
        onCreate(db);
    }
}