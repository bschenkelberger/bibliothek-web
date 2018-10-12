package de.postbank.praktikant.response;

import java.io.Serializable;
import java.util.List;

import de.postbank.praktikant.restpojo.Track;

public interface TrackResponse extends Serializable {

    List<Track> getTracks();

    void setTracks(List<Track> tracks);
}
