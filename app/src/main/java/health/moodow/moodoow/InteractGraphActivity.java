package health.moodow.moodoow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.TypedArrayUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import health.moodow.moodoow.db.DataDAO;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class InteractGraphActivity extends Activity implements AdapterView.OnItemSelectedListener {

    /** jour des mois de l'année */
    private static final int[] DAYS_BY_MONTHS = {31,28,31,30,31,30,31,31,30,31,30,31};

    /** gestion des dates */
    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH");

    /** graphique */
    private LineChart lineChart;

    /** la bd */
    private DataDAO dataDAO;

    /** seletion de l'intervalle */
    private Spinner spinnerDay;

    /** donée de jour */
    private Calendar calendar;
    private int dayNumInWeek;
    private int dayNum;

    /** elements du XML */
    private TextView smileText;
    private TextView mouepText;
    private TextView badText;


    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_graph);

        smileText = (TextView) findViewById(R.id.countSmile);
        mouepText = (TextView) findViewById(R.id.countMouep);
        badText = (TextView) findViewById(R.id.countBad);

        dataDAO = new DataDAO(getApplicationContext());

        calendar = new GregorianCalendar();
        dayNumInWeek = calendar.get(Calendar.DAY_OF_WEEK) -1; //-1 car en amérique la semaine commence au dimanche
        if(dayNumInWeek == 0){
            dayNumInWeek = 7;
        }
        System.out.println("day "+ dayNumInWeek);
        dayNum = calendar.get(Calendar.DAY_OF_MONTH);

        lineChart = (LineChart) findViewById(R.id.chart);
        spinnerDay = (Spinner) findViewById(R.id.spinner);
        spinnerDay.setOnItemSelectedListener(this);

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        setMoodCount();
        setChart(0);

    }

    private void setMoodCount() {

        dataDAO.open();

        String dateRecup = dateFormat.format(date);
        int hour = Integer.parseInt(dateRecup.substring(dateRecup.length()-2,dateRecup.length()));
        String date = dateRecup.substring(0,dateRecup.length()-3);

        ArrayList<ClickSave> clickSaveTest = dataDAO.findDay(date);


        int[] joursArray = new int[0];
        formatData(clickSaveTest, 23, 0, joursArray);

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
        String dateRecup = dateFormat.format(date);
        String date = dateRecup.substring(0,dateRecup.length()-3);
        ArrayList<ClickSave> clickSaveTest = new ArrayList<>();
        switch (position){
            case 0: //charger par jour
                System.out.println("date" + date);
                clickSaveTest = dataDAO.findDay(date);
                int[] joursArray = new int[0];
                formatData(clickSaveTest, 24, 0, joursArray);
                break;
            case 1: //charger par semaine , compliqué car peut enjamber deux mois

                int[] joursArrayS = new int[7];

                int index = 0;

                for(int i = dayNumInWeek-1; i>=0; i--){

                    int dayNumI = dayNum-i;

                    String dateToSearch = "";
                    if(dayNumI < 1) {

                        int month = calendar.get(Calendar.MONTH);
                        int day = DAYS_BY_MONTHS[month-1] + dayNumI;

                        if(day<10){
                            if(month<10) {
                                dateToSearch = "0" + day + "/0" + month + dateRecup.substring(5, 10);
                            } else {
                                dateToSearch = "0" + day + "/" + month + dateRecup.substring(5, 10);
                            }
                        } else {
                            if(month<10) {
                                dateToSearch = day + "/0" + month + dateRecup.substring(5, 10);
                            } else {
                                dateToSearch = day + "/" + month + dateRecup.substring(5, 10);
                            }
                        }


                    } else {
                        dateToSearch = "0" + dayNumI + dateRecup.substring(2, 10);
                    }

                    System.out.println("dateToSearch "+dateToSearch);

                    ArrayList<ClickSave> newClickSave = dataDAO.findDay(dateToSearch);

                    joursArrayS[index] = newClickSave.size();
                    index++;

                    for(int j = 0; j<newClickSave.size(); j++){
                        clickSaveTest.add(newClickSave.get(j));
                    }
                }
                System.out.println("taille2 "+clickSaveTest.size());
                formatData(clickSaveTest, 7, 1, joursArrayS);
                break;
            case 2: //charger par mois

                int[] joursArrayM = new int[dayNum];

                int u = 0;

                for(int i = dayNum-1; i>=0; i--){

                    int dayNumI = dayNum-i;

                    String dateToSearch = "0" + dayNumI + dateRecup.substring(2, 10);

                    System.out.println("dateToSearch "+dateToSearch);

                    ArrayList<ClickSave> newClickSave = dataDAO.findDay(dateToSearch);

                    joursArrayM[u] = newClickSave.size();
                    u++;

                    if(newClickSave != null) {
                        for (int j = 0; j < newClickSave.size(); j++) {
                            clickSaveTest.add(newClickSave.get(j));
                        }
                    }
                }
                formatData(clickSaveTest, DAYS_BY_MONTHS[calendar.get(Calendar.MONTH)], 2, joursArrayM);
                break;
            case 3: //charger par trimestre

                break;
            case 4: //charger par année

                break;
        }

    }

    private void formatData(ArrayList<ClickSave> clickSaves, int numOfValues, int type, final int indexSeq[]){

        List<Entry> moyHours = new ArrayList<>();

        if(clickSaves != null) {
            if (clickSaves.size() > 0) {

                switch (type){
                    case 0:
                        int totalSmile = 0;
                        int totalMouep = 0;
                        int totalBad = 0;
                        for (int i = 0; i < numOfValues; i++) {
                            int smile = 0;
                            int mouep = 0;
                            int bad = 0;
                            int count = 0;
                            for (int j = 0; j < clickSaves.size(); j++) {
                                if (clickSaves.get(j).getHour() == i) {
                                    smile += clickSaves.get(j).getSmile();
                                    mouep += clickSaves.get(j).getMouep();
                                    bad += clickSaves.get(j).getBad();
                                    if (clickSaves.get(j).getSmile() != 0) {
                                        count++;
                                    }
                                    if (clickSaves.get(j).getMouep() != 0) {
                                        count++;
                                    }
                                    if (clickSaves.get(j).getBad() != 0) {
                                        count++;
                                    }
                                }
                            }
                            totalSmile += smile;
                            totalMouep += mouep;
                            totalBad  += bad;
                            double moy = (1.0 * smile + 0.5 * mouep + 0.0 * bad) / (double) count;
                            moyHours.add(new Entry((float) i, (float) moy));
                        }
                        smileText.setText(totalSmile+"");
                        mouepText.setText(totalMouep+"");
                        badText.setText(totalBad+"");

                        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {

                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return ""+(int)value;
                            }
                        });
                        lineChart.getXAxis().setLabelCount(5, true);
                        break;
                    case 1:
                        int totalSmileJ = 0;
                        int totalMouepJ = 0;
                        int totalBadJ = 0;
                        for(int a = 0; a <7; a++) {//7 jours
                            int smile = 0;
                            int mouep = 0;
                            int bad = 0;
                            int count = 0;
                            for (int i = 0; i < indexSeq[a]; i++) { //24 heures
                                for (int j = 0; j < clickSaves.size(); j++) {
                                    if (clickSaves.get(j).getHour() == i) {
                                        smile += clickSaves.get(j).getSmile();
                                        mouep += clickSaves.get(j).getMouep();
                                        bad += clickSaves.get(j).getBad();
                                        if (clickSaves.get(j).getSmile() != 0) {
                                            count++;
                                        }
                                        if (clickSaves.get(j).getMouep() != 0) {
                                            count++;
                                        }
                                        if (clickSaves.get(j).getBad() != 0) {
                                            count++;
                                        }
                                    }
                                }
                            }
                            totalSmileJ += smile;
                            totalMouepJ += mouep;
                            totalBadJ  += bad;
                            double moy = (1.0 * smile + 0.5 * mouep + 0.0 * bad) / (double) count;
                            moyHours.add(new Entry((float) a+1, (float) moy));
                        }
                        smileText.setText(totalSmileJ+"");
                        mouepText.setText(totalMouepJ+"");
                        badText.setText(totalBadJ+"");

                        final String[] valueAxisX = {"Lun","Mar","Mer","Jeu","Ven","Sam","Dim"};

                        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {

                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                System.out.println(value);
                                if((int) value-1 < valueAxisX.length){
                                    return valueAxisX[(int)value-1];
                                }
                                return "";
                            }
                        });
                        lineChart.getXAxis().setLabelCount(7, true);
                        break;
                    case 2:
                        int month = calendar.get(Calendar.MONTH);
                        int day = DAYS_BY_MONTHS[month-1];

                        int totalSmileM = 0;
                        int totalMouepM = 0;
                        int totalBadM = 0;

                        for(int a = 0; a < day; a++) {
                            int smile = 0;
                            int mouep = 0;
                            int bad = 0;
                            int count = 0;
                            if(a < indexSeq.length) {
                                for (int i = 0; i < indexSeq[a]; i++) { //24 heures
                                    for (int j = 0; j < clickSaves.size(); j++) {
                                        if (clickSaves.get(j).getHour() == i) {
                                            smile += clickSaves.get(j).getSmile();
                                            mouep += clickSaves.get(j).getMouep();
                                            bad += clickSaves.get(j).getBad();
                                            if (clickSaves.get(j).getSmile() != 0) {
                                                count++;
                                            }
                                            if (clickSaves.get(j).getMouep() != 0) {
                                                count++;
                                            }
                                            if (clickSaves.get(j).getBad() != 0) {
                                                count++;
                                            }
                                        }
                                    }
                                }
                                double moy = (1.0 * smile + 0.5 * mouep + 0.0 * bad) / (double) count;
                                moyHours.add(new Entry((float) a + 1, (float) moy));
                                totalSmileM += smile;
                                totalMouepM += mouep;
                                totalBadM  += bad;
                            }
                        }
                        smileText.setText(totalSmileM+"");
                        mouepText.setText(totalMouepM+"");
                        badText.setText(totalBadM+"");

                        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {

                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return ""+(int)value;
                            }
                        });
                        lineChart.getXAxis().setLabelCount(5, true);
                        break;
                }

                LineDataSet dataSet = new LineDataSet(moyHours, "");
                dataSet.setDrawCircles(true);
                dataSet.setCircleColor(getResources().getColor(R.color.colorPrimary));
                dataSet.setCircleHoleRadius(1f);
                dataSet.setCircleRadius(4.0f);


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
                lineChart.getAxisLeft().setLabelCount(5, true);
                lineChart.getAxisLeft().setDrawAxisLine(false);
                lineChart.getAxisLeft().setDrawLabels(false);
                lineChart.getAxisRight().setDrawLabels(false);
                lineChart.getAxisRight().setLabelCount(5, true);
                lineChart.getAxisRight().setDrawAxisLine(false);
                lineChart.setTouchEnabled(false);
                lineChart.setDragEnabled(false);
                lineChart.setScaleEnabled(false);
                lineChart.setScaleXEnabled(false);
                lineChart.setScaleYEnabled(false);
                lineChart.setPinchZoom(false);
                lineChart.setDoubleTapToZoomEnabled(false);
                lineChart.invalidate(); // refresh
            }
        }
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
        setChart(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
