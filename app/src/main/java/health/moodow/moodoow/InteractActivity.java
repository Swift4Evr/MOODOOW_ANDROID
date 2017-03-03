package health.moodow.moodoow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class InteractActivity extends Activity {

    /** gestion des dates */
    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH");

    /** dernière heure a laquelle il a appuyé sur un bouton*/
    private String lastDate = "";
    private int lastHour = -1;

    /** moyenne de l'heure en cours */
    private double moyHour = Integer.MIN_VALUE;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact);

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

        double tag = Integer.parseInt(view.getTag().toString());

        System.out.println(tag);

        String dateRecup = dateFormat.format(date);

        int hour = Integer.parseInt(dateRecup.substring(dateRecup.length()-2,dateRecup.length()));

        //reset moyenne de l'heure
        if(hour != lastHour){
            switch((int)tag){
                case 1:
                    moyHour = 20.0;
                    break;
                case 2:
                    moyHour = 10.0;
                    break;
                case 3:
                    moyHour = 0.0;
                    break;
            }
        } else {

            if(moyHour == Integer.MIN_VALUE){
                moyHour = tag;
            }

            double newMoy = (moyHour + tag) / 2.0;

            if (newMoy > 0.0 && newMoy <= 20.0) {
                moyHour = newMoy;
            }
        }

        lastHour = hour;

        System.out.println(moyHour);

        //ajouterPreferences(mood, 1);
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
