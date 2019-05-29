package de.postbank.praktikant;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.postbank.praktikant.restpojo.BookEntity;
import de.postbank.praktikant.restpojo.Genre;
import de.postbank.praktikant.restpojo.Source;

public class NeuPage extends WebPage {

	public NeuPage() {
		super(new Model<BookEntity>(new BookEntity()));

		Form<BookEntity> form = new Form<BookEntity>("form");
		add(form);
		form.add(new TextField<String>("titel", new PropertyModel<String>(getDefaultModel(), "name")));
		/*
		 * Die Wert für Genre und Source müssen in einem zweiten Schritt noch via
		 * RestCall ermittelt werden.
		 * 
		 * http://localhost:8081/bibliothek/api/sources?type=BOOK
		 * http://localhost:8081/bibliothek/api/genres?type=BOOK
		 */
		form.add(new Label("genre", new Model<>("Fantasy")));
		form.add(new Label("source", new Model<>("Amazon Kindle")));

		form.add(new Button("speichern", new ResourceModel("speichernText")) {
			@Override
			public void onSubmit() {
				super.onSubmit();
				addBook();
				setResponsePage(UebersichtPage.class);
			}

		});

		form.add(new Button("zurueck", new ResourceModel("zurueckText")) {
			@Override
			public void onSubmit() {
				super.onSubmit();
				setResponsePage(UebersichtPage.class);
			}
		}.setDefaultFormProcessing(false));
	}

	private void addBook() {
		BookEntity entity = (BookEntity) this.getDefaultModelObject();
		
		/*
		 * Dieser Part ist nur notwendig solange noch kein Daten sprich Genre oder Source aus 
		 * der DB ermittelt werden
		 */
		Genre genre = new Genre();
		genre.setId(9L);
		entity.setGenre(genre);
		Source source = new Source();
		source.setId(1L);
		entity.setSource(source);

		try {
			URL url = new URL("http://localhost:8081/bibliothek/api/books");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			ObjectMapper mapper = new ObjectMapper();
			String jasonTrackString = mapper.writeValueAsString(entity);

			OutputStream os = conn.getOutputStream();
			os.write(jasonTrackString.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new WicketRuntimeException("Failed : HTTP error code: " + conn.getResponseCode());
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
