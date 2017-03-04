package health.moodow.moodoow;

import java.util.ArrayList;

/**
 * Created by matthieubravo on 03/03/2017.
 */

public class Day {

    /** date du jour */
    private String date;

    /** tableau des heures de la journée */
    private ArrayList<Hour> arrayListHour;

    public Day(){

    }

    public Day(String date, ArrayList<Hour> arrayListHour){
        this.date = date;
        this.arrayListHour = arrayListHour;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addHour(Hour hour) {
        this.arrayListHour.add(hour);
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Hour> getArrayListHour() {
        return arrayListHour;
    }
}
