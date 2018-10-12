package de.postbank.praktikant.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.wicket.model.LoadableDetachableModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.postbank.praktikant.response.TrackResponse;
import de.postbank.praktikant.response.TrackResponseImpl;

public class TracksModel extends LoadableDetachableModel<TrackResponse> {

    @Override
    protected TrackResponse load() {
        TrackResponse result = new TrackResponseImpl();
        // String test = "";
        try {
            URL url = new URL("http://localhost:8090/RESTfulExample/rest/json/video/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(new InputStreamReader(conn.getInputStream()), TrackResponseImpl.class);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
