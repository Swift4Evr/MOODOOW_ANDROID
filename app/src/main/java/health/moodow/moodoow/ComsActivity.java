package health.moodow.moodoow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;

import java.util.Calendar;

import health.moodow.moodoow.db.DataDAO;

/**
 * Created by matthieubravo on 07/03/2017.
 */

public class ComsActivity extends Activity {

    /** date a rechercher */
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coms);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        System.out.println("date" + date);

    }
}
