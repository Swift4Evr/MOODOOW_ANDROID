package health.moodow.moodoow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 14;
    public static final String TABLE_DROP_SAVE = "DROP TABLE IF EXISTS save;DROP TABLE IF EXISTS comms;";


    // Commande sql pour la création de la base de données
    private static final String SAVE_CREATE = "create table save (id integer primary key autoincrement, " +
            "date varchar(200), hour integer(2), smile integer(1), mouep integer(1), " +
            "bad integer(1));";

    private static final String SAVE_COMMS = "create table comms (id integer primary key autoincrement, " +
            "date varchar(200), hour integer(2), coms varchar(200));";

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SAVE_CREATE);
        db.execSQL(SAVE_COMMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP_SAVE);
        onCreate(db);
    }
}