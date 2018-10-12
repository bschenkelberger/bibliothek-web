package de.postbank.praktikant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.postbank.praktikant.restpojo.Track;

public class AendernPage extends WebPage {

    public AendernPage(IModel<Track> model) {
        super(model);

        Form<Track> form = new Form<Track>("form", model);
        add(form);
        form.add(new TextField<String>("titel", new PropertyModel<String>(getDefaultModel(), "title")));
        form.add(new TextField<String>("singer", new PropertyModel<String>(getDefaultModel(), "singer")));
        form.add(new DateTextField("datum", new PropertyModel<Date>(getDefaultModel(), "datum"), "dd.MM.yyyy"));
        form.add(new Button("speichern", new ResourceModel("speichernText")) {
            @Override
            public void onSubmit() {
                super.onSubmit();
                addVideo((Track) AendernPage.this.getDefaultModelObject());
                setResponsePage(new UebersichtPage(new PageParameters()));
            }
        });

        form.add(new Button("zurueck", new ResourceModel("zurueckText")) {
            @Override
            public void onSubmit() {
                super.onSubmit();
                setResponsePage(new UebersichtPage(new PageParameters()));
            }
        }.setDefaultFormProcessing(false));
    }

    private void addVideo(Track track) {

        try {
            //Als Url muss der Endpunk von deinem Service eingetragen werden
            URL url = new URL("http://localhost:8090/xxxx/rest/json/video/post");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            ObjectMapper mapper = new ObjectMapper();
            String jasonTrackString = mapper.writeValueAsString(track);

            OutputStream os = conn.getOutputStream();
            os.write(jasonTrackString.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new WicketRuntimeException("Failed : HTTP error code: " + conn.getResponseCode());
            }

//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String output;
//            System.out.println("Output from Server....\n");
//            while ((output = br.readLine()) != null) {
//                System.out.println(output);
//            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}