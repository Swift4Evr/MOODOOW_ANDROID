package health.moodow.moodoow;

import java.util.ArrayList;

/**
 * Created by matthieubravo on 03/03/2017.
 */

public class Day {

    /** date du jour */
    private String date;

    /** tableau des heures de la journée */
    private ArrayList<ClickSave> arrayListClickSave;

    public Day(){

    }

    public Day(String date, ArrayList<ClickSave> arrayListClickSave){
        this.date = date;
        this.arrayListClickSave = arrayListClickSave;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addHour(ClickSave clickSave) {
        this.arrayListClickSave.add(clickSave);
    }

    public String getDate() {
        return date;
    }

    public ArrayList<ClickSave> getArrayListClickSave() {
        return arrayListClickSave;
    }
}
