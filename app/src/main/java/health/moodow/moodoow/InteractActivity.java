package health.moodow.moodoow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import health.moodow.moodoow.db.DataDAO;

import static android.R.attr.value;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class InteractActivity extends Activity {

    /** gestion des dates */
    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH");

    /** dernière heure a laquelle il a appuyé sur un bouton*/
    private String lastDate = "";
    private int lastHour = -1;

    /** moyenne de l'heure en cours */
    private int moyHour = Integer.MIN_VALUE;

    /** smiley représentant l'état actuel */
    private ImageView actuSmiley;

    private SharedPreferences preferences;

    /** nombre de chaque humeur dans l'heure */
    private int smile = 0;
    private int mouep = 0;
    private int bad = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact);

        actuSmiley = (ImageView) findViewById(R.id.smileyActu);

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
    }

    /**
     * Clic sur le btn stats
     *
     * @param sender btn stats
     */
    public void clickStats(View sender) {
        Intent intent = new Intent(this, InteractGraphActivity.class);
        startActivity(intent);
    }

    /**
     * Clic sur un bouton du menu
     *
     * @param v btn
     */
    public void touchAction(View v) {
        // Do nothing
    }

    /**
     * Clic sur un btn "Mood"
     * Ajoute +1 au compteur du mood
     *
     * @param view mood btn
     */
    public void clickMood(View view) {
        String mood = view.getTag().toString();
        // Effectue une animation
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        view.startAnimation(anim);

        int tag = Integer.parseInt(view.getTag().toString());

        System.out.println(tag);

        String dateRecup = dateFormat.format(date);

        int hour = Integer.parseInt(dateRecup.substring(dateRecup.length()-2,dateRecup.length()));
        String date = dateRecup.substring(0,dateRecup.length()-2);

        System.out.println("date" + date);

        if(hour != lastHour){

            //enregistrer les données actuelles
            //----
            Hour hourSave = new Hour(-1, date, lastHour, smile, mouep, bad, moyHour);

            DataDAO dataDAO = new DataDAO(getApplicationContext());
            dataDAO.open();
            dataDAO.create(hourSave);

            Hour hourTest = dataDAO.findDay(date);

            System.out.println(hourTest.getBad());

            //----

            smile = 0;
            mouep = 0;
            bad = 0;
        }

        if(moyHour == Integer.MIN_VALUE){
            switch (tag) {
                case 2:
                    moyHour = 20;
                    break;
                case 1:
                   moyHour = 10;
                    break;
                case 0:
                    moyHour = 0;
                    break;
            }
        }

        int newMoy = moyHour;
        switch (tag) {
            case 2:
                newMoy++;
                smile++;
                break;
            case 1:
                if(newMoy>10){
                    newMoy--;
                } else if(newMoy<10){
                    newMoy++;
                }
                mouep++;
                break;
            case 0:
                newMoy--;
                bad++;
                break;
        }

        if (newMoy >= 0 && newMoy <= 20) {
            moyHour = newMoy;
        }

        changeSmiley();

        lastHour = hour;

        System.out.println(moyHour);

        //ajouterPreferences(mood, 1);
    }

    /**
     * Changer le smiley d'humeur actuelle
     * suivant la moyenne
     */
    private void changeSmiley(){
        if(moyHour < 7){
            actuSmiley.setImageResource(R.drawable.bad);
        } else if(moyHour < 13){
            actuSmiley.setImageResource(R.drawable.mouep);
        } else {
            actuSmiley.setImageResource(R.drawable.smile);
        }
    }

    /**
     * Ajoute des valeurs dans les preferences
     *
     * @param etat  key
     * @param value value
     */
    private void ajouterPreferences(String etat, int value) {

        int val = preferences.getInt(etat, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(etat, value + val);
        editor.apply(); // Apply plutot que commit => asynchrone
    }
}
