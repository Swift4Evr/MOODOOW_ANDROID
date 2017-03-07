package health.moodow.moodoow;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import health.moodow.moodoow.db.DataDAO;

/**
 * Created by matthieubravo on 07/03/2017.
 */

public class ComsActivity extends ListActivity {

    /** date a rechercher */
    private String date;
    private DataDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coms);

        dao = new DataDAO(this);
        dao.open();

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        System.out.println("date" + date);

        ArrayList<Coms> coms = dao.findDayComs(date);
        populateList(coms);
    }

    /**
     * Complète la liste avec des commentaires
     * @param coms commentaires pour compléter la liste
     */
    private void populateList(List<Coms> coms) {
        ArrayList<HashMap<String, String>> items = new ArrayList<>();

        for (int i = 0; i < coms.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("date", coms.get(i).getDate());
            map.put("heure", coms.get(i).getHour());
            map.put("commentaire", coms.get(i).getText());

            items.add(map);
        }

        // Met en place les éléments dans la liste
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.itemlist_com,
                new String[]{"date", "heure", "commentaire"},
                new int[]{R.id.date, R.id.heure, R.id.commentaire});
        // On ajoute le binder personnalisé
        this.setListAdapter(adapter);

    }


}
