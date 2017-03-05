package health.moodow.moodoow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import health.moodow.moodoow.db.DataDAO;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class InteractActivity extends Activity {

    /** gestion des dates */
    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH");


    /** smiley représentant l'état actuel */
    private ImageView actuSmiley;

    private SharedPreferences preferences;


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

        String dateRecup = dateFormat.format(date);
        int hour = Integer.parseInt(dateRecup.substring(dateRecup.length()-2,dateRecup.length()));
        String date = dateRecup.substring(0,dateRecup.length()-3);


        ClickSave clickSaveSave = new ClickSave();
        switch (tag) {
            case 2:
                clickSaveSave = new ClickSave(-1, date, hour, 1, 0, 0);
                actuSmiley.setImageResource(R.drawable.smile);
                break;
            case 1:
                clickSaveSave = new ClickSave(-1, date, hour, 0, 1, 0);
                actuSmiley.setImageResource(R.drawable.mouep);
                break;
            case 0:
                clickSaveSave = new ClickSave(-1, date, hour, 0, 0, 1);
                actuSmiley.setImageResource(R.drawable.bad);
                break;
        }

        //enregistrer les données actuelles
        //----

        DataDAO dataDAO = new DataDAO(getApplicationContext());
        dataDAO.open();
        dataDAO.create(clickSaveSave);

        ArrayList<ClickSave> clickSaveTest = dataDAO.findDay(date);

        int smile = 0;
        int mouep = 0;
        int bad = 0;
        for(int i = 0 ; i<clickSaveTest.size() ; i++){
            smile += clickSaveTest.get(i).getSmile();
            mouep += clickSaveTest.get(i).getMouep();
            bad += clickSaveTest.get(i).getBad();
        }
        System.out.println("smile " + smile);
        System.out.println("mouep " + mouep);
        System.out.println("bad " + bad);
        
        double moyDuMom = (1.0*smile+0.5*mouep+0.0*bad)/clickSaveTest.size();

        System.out.println("moyenne de lheure en cours : " + moyDuMom);

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
