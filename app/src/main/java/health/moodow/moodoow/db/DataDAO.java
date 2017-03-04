package health.moodow.moodoow.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import dev.matthieubravo.fadb.MainActivity;
import dev.matthieubravo.fadb.R;
import dev.matthieubravo.fadb.filmsmanager.Day;
import health.moodow.moodoow.Day;

/**
 * Accès BDD Day
 * Gère les actions à réaliser avec la base de données
 * - select
 * - insert
 * - update
 * - delete
 */
public class DataDAO extends DAO {

    private Context context;

    public DataDAO(Context pContext) {
        super(pContext);

        this.context = pContext;
    }

    /**
     * Créé un film dans la bdd
     *
     * @param d Le film à ajouter
     * @return l'id du film créé
     */
    public int create(Day d) {
        ContentValues value = new ContentValues();
        
        value.put("date", d.getDate());
        int id = (int) mDb.insert("days", null, value);

        return id;
    }

    /**
     * Récupère tous les films enregistrés dans la base de données
     *
     * @return Les films présents dans la base de données
     */
    public Day[] selectAll() {
        ArrayList<Day> films = new ArrayList<Day>();

        Cursor curseur = mDb.rawQuery("SELECT * FROM days ORDER BY id DESC", null);
        while (curseur.moveToNext()) {

           /* int id = curseur.getInt(0);
            String artwork = curseur.getString(1);
            String title = curseur.getString(2);
            String date = curseur.getString(3);
            int rating = curseur.getInt(4);
            int userRating = curseur.getInt(5);
            boolean isSee = curseur.getInt(6)==1;
            boolean isToSee = curseur.getInt(7)==1;
            String synopsis = curseur.getString(8);
            String titleReview = curseur.getString(9);
            String review = curseur.getString(10);

            Day film = new Day(id, artwork, title, date, rating, userRating, isSee,
                                 isToSee, synopsis, titleReview, review);
            films.add(film);*/
        }

        // Si il n'y a aucun film alors on en créé des nouveaux
        if (films.isEmpty()) {
            //creerDays();
            // On recharge la fonction
        }

        return films.toArray(new Day[films.size()]);
    }


    /**
     * Récupère les films les mieux notés
     *
     * @param value nombre de films à récupérer
     * @return les films favoris
     */
    public Day[] selectTop(int value) {
        ArrayList<Day> films = new ArrayList<Day>();

        Cursor curseur = mDb.rawQuery("SELECT * FROM days " + "ORDER BY note DESC LIMIT 0, ?",
                new String[]{String.valueOf(value)});
       /*while (curseur.moveToNext()) {

            int id = curseur.getInt(0);
            String artwork = curseur.getString(1);
            String title = curseur.getString(2);
            String date = curseur.getString(3);
            int rating = curseur.getInt(4);
            int userRating = curseur.getInt(5);
            boolean isSee = curseur.getInt(6)==1;
            boolean isToSee = curseur.getInt(7)==1;
            String synopsis = curseur.getString(8);
            String titleReview = curseur.getString(9);
            String review = curseur.getString(10);

            //Day film = new Day(id, artwork, title, date, rating, userRating, isSee,
              //      isToSee, synopsis, titleReview, review);
            films.add(film);
        }
        return films.toArray(new Day[films.size()]);*/
    }

    /**
     * Selectionne un film en particulier
     *
     * @param id id du film à select
     * @return le film selectionné
     */
    public Day find(int id) {
        Cursor curseur = mDb.rawQuery("SELECT * FROM days WHERE id = ?", new String[]{String.valueOf(id)});
        curseur.moveToFirst();

        /*String artwork = curseur.getString(1);
        String title = curseur.getString(2);
        String date = curseur.getString(3);
        int rating = curseur.getInt(4);
        int userRating = curseur.getInt(5);
        boolean isSee = curseur.getInt(6)==1;
        boolean isToSee = curseur.getInt(7)==1;
        String synopsis = curseur.getString(8);
        String titleReview = curseur.getString(9);
        String review = curseur.getString(10);

        Day film = new Day(id, artwork, title, date, rating, userRating, isSee,
                isToSee, synopsis, titleReview, review);

        return film;*/
    }

    /**
     * Modifie un film dans la bdd
     * Met à jour les informations d'un film
     * @param f film à modifier
     */
    public void update(Day f) {

        ContentValues value = new ContentValues();
        // On modifie les infos du film
        //value.put("review", f.getReview());

        //mDb.update("film", value, "id = ?", new String[] {String.valueOf(f.getId())});
    }


    /**
     * Supprime un film de la BDD
     *
     * @param id ID du film à supprimer
     */
    public void delete(int id) {
        mDb.delete("film", "id = ?", new String[]{String.valueOf(id)});
    }
}
