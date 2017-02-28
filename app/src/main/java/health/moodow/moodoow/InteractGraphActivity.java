package health.moodow.moodoow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class InteractGraphActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_graph);
    }

    public void smileysClicked(View sender){
        Intent intent = new Intent(this, InteractGraphsActivity.class);
        startActivity(intent);
    }

}
