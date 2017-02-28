package health.moodow.moodoow;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * démarrer l'application principale lors du click sur connection
     * @param sender bouton conntection
     */
    public void clickCo(View sender){
        Intent intent = new Intent(this, InteractActivity.class);
        startActivity(intent);
    }
}
