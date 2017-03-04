package health.moodow.moodoow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import health.moodow.moodoow.db.DataDAO;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class InteractGraphActivity extends Activity implements AdapterView.OnItemSelectedListener {

    /** gestion des dates */
    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH");

    /** graphique */
    private LineChart lineChart;

    /** la bd */
    private DataDAO dataDAO = new DataDAO(getApplicationContext());

    /** seletion de l'intervalle */
    private Spinner spinnerDay;


    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_graph);

        lineChart = (LineChart) findViewById(R.id.chart);

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        setMoodCount();
        setChart(0);

    }

    private void setMoodCount() {

        dataDAO = new DataDAO(getApplicationContext());
        dataDAO.open();

        String dateRecup = dateFormat.format(date);
        int hour = Integer.parseInt(dateRecup.substring(dateRecup.length()-2,dateRecup.length()));
        String date = dateRecup.substring(0,dateRecup.length()-2);

        ArrayList<ClickSave> clickSaveTest = dataDAO.findDay(date);
        formatData(clickSaveTest);

        int smile = 0;
        int mouep = 0;
        int bad = 0;

        System.out.println(date);
        for(int i = 0 ; i<clickSaveTest.size() ; i++){
            smile += clickSaveTest.get(i).getSmile();
            mouep += clickSaveTest.get(i).getMouep();
            bad += clickSaveTest.get(i).getBad();
        }

        //calcul de la moyenne de la journée
        double moyDuMom = (1.0*smile+0.5*mouep+0.0*bad)/clickSaveTest.size();
        changeSmiley(moyDuMom);

        TextView smileText = (TextView) findViewById(R.id.countSmile);
        TextView mouepText = (TextView) findViewById(R.id.countMouep);
        TextView badText = (TextView) findViewById(R.id.countBad);

        smileText.setText(smile+"");
        mouepText.setText(mouep+"");
        badText.setText(bad+"");
    }


    /**
     * changer le smiley selon la moyenne de la journée
     * @param moy moyenne de la journée
     */
    public void changeSmiley(double moy){
        if(moy< 0.4){
            ((ImageView) findViewById(R.id.currentSmile)).setImageResource(R.drawable.bad);
        } else if(moy < 0.6){
            ((ImageView) findViewById(R.id.currentSmile)).setImageResource(R.drawable.mouep);
        } else {
            ((ImageView) findViewById(R.id.currentSmile)).setImageResource(R.drawable.smile);
        }
    }

    private void setChart(int position){

        switch (position){
            case 0: //charger par jour
                String dateRecup = dateFormat.format(date);
                int hour = Integer.parseInt(dateRecup.substring(dateRecup.length()-2,dateRecup.length()));
                String date = dateRecup.substring(0,dateRecup.length()-2);
                ArrayList<ClickSave> clickSaveTest = dataDAO.findDay(date);
                formatData(clickSaveTest);
                break;
            case 1: //carger par semaine

                break;
            case 2: //carger par mois

                break;
            case 3: //carger par trimestre

                break;
            case 4: //carger par année

                break;
        }

    }

    private void formatData(ArrayList<ClickSave> clickSaves){

        List<Entry> moyHours = new ArrayList<>();

        for(int i = 0; i<=24; i++){
            int smile = 0;
            int mouep = 0;
            int bad = 0;
            int count = 0;
            for(int j = 0; j<clickSaves.size(); j++){
                if(clickSaves.get(j).getHour() == i) {
                    smile += clickSaves.get(j).getSmile();
                    mouep += clickSaves.get(j).getMouep();
                    bad += clickSaves.get(j).getBad();
                    if(clickSaves.get(j).getSmile() != 0){
                        count++;
                    }
                    if(clickSaves.get(j).getMouep() != 0){
                        count++;
                    }
                    if(clickSaves.get(j).getBad() != 0){
                        count++;
                    }
                }
            }
            double moy = (1.0*smile+0.5*mouep+0.0*bad)/(double)count;
            moyHours.add(new Entry((float) i, (float) moy));
        }

        LineDataSet dataSet = new LineDataSet(moyHours, "");
        System.out.println("taile  "+moyHours.size());
        if(moyHours.size() == 1) {
            dataSet.setDrawCircles(true);
            dataSet.setCircleColor(getResources().getColor(R.color.colorPrimary));
            dataSet.setCircleHoleRadius(0.0f);
            dataSet.setCircleRadius(4.0f);
        } else {
            dataSet.setDrawCircles(false);
        }
        dataSet.setDrawValues(false);
        dataSet.setColor(getResources().getColor(R.color.colorPrimary));

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        lineChart.getAxisLeft().setAxisMaximum(1f);
        lineChart.getAxisLeft().setAxisMinimum(0f);
        lineChart.getLegend().setEnabled(false);
        lineChart.setNoDataText("Aucune informations pour la période sélectionnée");
        lineChart.setGridBackgroundColor(getResources().getColor(R.color.colorPrimary));
        lineChart.setDrawBorders(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawGridLines(false); // no grid lines
        lineChart.getXAxis().setLabelCount(5, true);
        lineChart.getAxisLeft().setLabelCount(5,true);
        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setLabelCount(5,true);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.invalidate(); // refresh
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
