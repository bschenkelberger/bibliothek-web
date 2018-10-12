package de.postbank.praktikant.restpojo;

import java.io.Serializable;
import java.util.Date;

public class Track implements Serializable {

    String title;
    String singer;
    Date datum = new Date();

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date value) {
        this.datum = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    @Override
    public String toString() {
        return "Track [title=" + title + ", singer=" + singer + "]";
    }

}
