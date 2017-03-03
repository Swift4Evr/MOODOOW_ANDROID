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

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class InteractActivity extends Activity {

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

        ajouterPreferences(mood, 1);
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
