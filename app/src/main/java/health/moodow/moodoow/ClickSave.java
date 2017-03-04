package health.moodow.moodoow;

/**
 * Created by matthieubravo on 03/03/2017.
 */

public class ClickSave {

    /** id */
    private int id;

    /** date */
    private String date;

    /** heure */
    private int hour;

    /** nombre de chaque humeur dans l'heure */
    private int smile;
    private int mouep;
    private int bad;

    public ClickSave(){

    }

    public ClickSave(int id, String date, int hour, int smile, int mouep, int bad) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.smile = smile;
        this.mouep = mouep;
        this.bad = bad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setSmile(int smile) {
        this.smile = smile;
    }

    public void setMouep(int mouep) {
        this.mouep = mouep;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getHour() {
        return hour;
    }

    public int getSmile() {
        return smile;
    }

    public int getMouep() {
        return mouep;
    }

    public int getBad() {
        return bad;
    }

}
