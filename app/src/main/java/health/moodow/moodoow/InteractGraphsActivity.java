package health.moodow.moodoow;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import health.moodow.moodoow.db.DataDAO;

/**
 * Created by matthieubravo on 28/02/2017.
 */

public class InteractGraphsActivity extends Activity implements DatePickerDialog.OnDateSetListener {

    private Calendar calendar = new GregorianCalendar();

    private Button buttonPicker;

    /** la bd */
    private DataDAO dataDAO;

    /** graphiques */
    private BarChart barChartSmile;
    private BarChart barChartMouep;
    private BarChart barChartBad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_graphs);

        dataDAO = new DataDAO(getApplicationContext());
        dataDAO.open();

        barChartSmile = (BarChart) findViewById(R.id.chartSmile);
        barChartMouep = (BarChart) findViewById(R.id.chartMouep);
        barChartBad = (BarChart) findViewById(R.id.chartBad);

        buttonPicker = (Button) findViewById(R.id.buttonPicker);
        buttonPicker.setText(changeDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                             calendar.get(Calendar.DAY_OF_MONTH)));

        loadFromDay(buttonPicker.getText().toString());

    }
    /**
     * Clic sur un bouton du menu
     *
     * @param v btn
     */
    public void touchAction(View v) {
        // Do nothing
        DatePickerDialog datePicker = new DatePickerDialog(this, this,
                                      calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                      calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        loadFromDay(changeDate(year, month, dayOfMonth));
    }

    private void loadFromDay(String day){
        buttonPicker.setText(day);

        ArrayList<ClickSave> clickSaves = dataDAO.findDay(day);

        List<BarEntry> moySmile = new ArrayList<>();
        List<BarEntry> moyMouep = new ArrayList<>();
        List<BarEntry> moyBad = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            int smile = 0;
            int mouep = 0;
            int bad = 0;
            for (int j = 0; j < clickSaves.size(); j++) {
                if (clickSaves.get(j).getHour() == i) {
                    smile += clickSaves.get(j).getSmile();
                    mouep += clickSaves.get(j).getMouep();
                    bad += clickSaves.get(j).getBad();
                }
            }

            moySmile.add(new BarEntry((float) i, (float) smile));
            moyMouep.add(new BarEntry((float) i, (float) mouep));
            moyBad.add(new BarEntry((float) i, (float) bad));
        }

        Description description = new Description();
        description.setText("");

        BarDataSet setSmile = new BarDataSet(moySmile, "");
        setSmile.setColor(getResources().getColor(R.color.green));
        BarData dataSmile = new BarData(setSmile);
        dataSmile.setDrawValues(false);
        barChartSmile.setData(dataSmile);
        barChartSmile.setDescription(description);
        defineGraph(barChartSmile);

        BarDataSet setMouep = new BarDataSet(moyMouep, "");
        setMouep.setColor(getResources().getColor(R.color.orange));
        BarData dataMouep = new BarData(setMouep);
        dataMouep.setDrawValues(false);
        barChartMouep.setData(dataMouep);
        barChartMouep.setDescription(description);
        defineGraph(barChartMouep);

        BarDataSet setBad = new BarDataSet(moyBad, "");
        setBad.setColor(getResources().getColor(R.color.red));
        BarData dataBad = new BarData(setBad);
        dataBad.setDrawValues(false);
        barChartBad.setData(dataBad);
        barChartBad.setDescription(description);
        defineGraph(barChartBad);

    }

    private void defineGraph(BarChart chart){
        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.setNoDataText("Aucune informations pour la période sélectionnée");
        chart.setGridBackgroundColor(getResources().getColor(R.color.colorPrimary));
        chart.setDrawBorders(false);
        chart.getXAxis().setLabelCount(5, true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(false); // no grid lines
        chart.getAxisLeft().setDrawGridLines(false); // no grid lines
        chart.getAxisRight().setDrawGridLines(false); // no grid lines
        chart.getAxisLeft().setLabelCount(5, true);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisRight().setLabelCount(5, true);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setScaleXEnabled(false);
        chart.setScaleYEnabled(false);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.invalidate(); // refresh
    }

    private String changeDate(int year, int month, int dayOfMonth){
        String toLoad = "";
        if(dayOfMonth<10){
            toLoad = "0" + dayOfMonth;
        } else {
            toLoad = "" + dayOfMonth;
        }
        if(month<10){
            toLoad += "/0" + (month+1);
        } else {
            toLoad += "/" + (month+1);
        }
        toLoad += "/" + year;

        return toLoad;
    }
}
