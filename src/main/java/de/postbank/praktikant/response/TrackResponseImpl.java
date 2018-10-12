package de.postbank.praktikant.response;

import java.util.ArrayList;
import java.util.List;

import de.postbank.praktikant.restpojo.Track;

public class TrackResponseImpl implements TrackResponse {

    private List<Track> tracks = new ArrayList<>();

    @Override
    public List<Track> getTracks() {
        return tracks;
    }

    @Override
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
