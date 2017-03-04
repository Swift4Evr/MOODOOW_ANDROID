package health.moodow.moodoow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 13;
    public static final String TABLE_DROP_FILM = "DROP TABLE IF EXISTS film;";


    // Commande sql pour la création de la base de données
    private static final String FILM_CREATE = "create table days (id integer primary key autoincrement, " +
            "artwork varchar(200), title varchar(200), year varchar(50), rating integer(1), " +
            "user_rating integer(1), is_see integer(1), is_to_see integer(1)," +
            " synopsis text, title_review varchar(100), review text);";

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FILM_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP_FILM);
        onCreate(db);
    }
}