package health.moodow.moodoow;

/**
 * Created by matthieubravo on 07/03/2017.
 */

public class Coms {

    /** id */
    private int id;

    /** date */
    private String date;

    /** heure */
    private int hour;

    /** texte du commentaire */
    private String text;

    public Coms(int id, String date, int hour, String text) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.text = text;
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

    public void setText(String text) {
        this.text = text;
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

    public String getText() {
        return text;
    }
}