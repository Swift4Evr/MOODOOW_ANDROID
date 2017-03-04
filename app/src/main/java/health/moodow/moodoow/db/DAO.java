package health.moodow.moodoow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Accès à la base de données
 */
public abstract class DAO {
    protected final static String BD_NAME = "database.db";

    protected SQLiteDatabase mDb = null;

    protected DataBaseHandler mHandler = null;

    protected Context context;

    private static final int VERSION = 1;

    public DAO(Context pContext) {
        this.mHandler = new DataBaseHandler(pContext, BD_NAME, null, VERSION);
        context = pContext;
    }

    public void open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        mDb = mHandler.getWritableDatabase();
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {

        return mDb;

    }
}
