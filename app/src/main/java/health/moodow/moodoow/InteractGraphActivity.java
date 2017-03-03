package health.moodow.moodoow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class InteractGraphActivity extends Activity {


    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_graph);

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        setMoodCount();

    }

    private void setMoodCount() {
        int smile = preferences.getInt("smile", 0);
        int mouep = preferences.getInt("mouep", 0);
        int bad = preferences.getInt("bad", 0);

        TextView smileText = (TextView) findViewById(R.id.countSmile);
        TextView mouepText = (TextView) findViewById(R.id.countMouep);
        TextView badText = (TextView) findViewById(R.id.countBad);

        smileText.setText(smile+"");
        mouepText.setText(mouep+"");
        badText.setText(bad+"");
    }

    public void smileysClicked(View sender){
        Intent intent = new Intent(this, InteractGraphsActivity.class);
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
}
