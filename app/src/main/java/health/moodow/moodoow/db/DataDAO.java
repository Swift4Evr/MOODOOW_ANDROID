package health.moodow.moodoow.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import health.moodow.moodoow.ClickSave;

/**
 * Accès BDD ClickSave
 * Gère les actions à réaliser avec la base de données
 * - select
 * - insert
 */
public class DataDAO extends DAO {

    private Context context;

    public DataDAO(Context pContext) {
        super(pContext);

        this.context = pContext;
    }

    /**
     * Créé une heure dans la bdd
     *
     * @param h l'heure a ajouter
     * @return l'id de l'heure
     */
    public int create(ClickSave h) {
        ContentValues value = new ContentValues();
        
        value.put("date", h.getDate());
        value.put("hour", h.getHour());
        value.put("smile", h.getSmile());
        value.put("mouep", h.getMouep());
        value.put("bad", h.getBad());
        int id = (int) mDb.insert("save", null, value);

        return id;
    }

    /**
     * Récupère tous les heures enregistrés dans la base de données
     *
     * @return Les heures présents dans la base de données
     */
    public ClickSave[] selectAll() {
        ArrayList<ClickSave> clickSaves = new ArrayList<ClickSave>();

        Cursor curseur = mDb.rawQuery("SELECT * FROM save ORDER BY id DESC", null);
        while (curseur.moveToNext()) {

            int id = curseur.getInt(0);
            String date = curseur.getString(1);
            int hourBD = curseur.getInt(2);
            int smile = curseur.getInt(3);
            int mouep = curseur.getInt(4);
            int bad = curseur.getInt(5);

            ClickSave clickSave = new ClickSave(id, date, hourBD, smile, mouep, bad);
            clickSaves.add(clickSave);
        }
        return clickSaves.toArray(new ClickSave[clickSaves.size()]);
    }


    /**
     * Selectionne un heureen particulier
     *
     * @param date id du heureà select
     * @return le heureselectionné
     */
    public ArrayList<ClickSave> findDay(String date) {

        ArrayList<ClickSave> clickSaves = new ArrayList<ClickSave>();

        Cursor curseur = mDb.rawQuery("SELECT * FROM save WHERE date = ?", new String[]{date});
        while (curseur.moveToNext()) {

            int id = curseur.getInt(0);
            String dateDB = curseur.getString(1);
            int hourBD = curseur.getInt(2);
            int smile = curseur.getInt(3);
            int mouep = curseur.getInt(4);
            int bad = curseur.getInt(5);

            ClickSave clickSave = new ClickSave(id, date, hourBD, smile, mouep, bad);
            clickSaves.add(clickSave);
        }

        return clickSaves;
    }

    /**
     * Modifie un heuredans la bdd
     * Met à jour les informations d'un film
     * @param f heureà modifier

    public void update(ClickSave f) {

        ContentValues value = new ContentValues();
        // On modifie les infos du film
        //value.put("review", f.getReview());

        //mDb.update("film", value, "id = ?", new String[] {String.valueOf(f.getId())});
    }
     */

    /**
     * Supprime un heurede la BDD
     *
     * @param id ID du heureà supprimer

    public void delete(int id) {
        mDb.delete("film", "id = ?", new String[]{String.valueOf(id)});
    }
     */
}
