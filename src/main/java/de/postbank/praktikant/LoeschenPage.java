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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.postbank.praktikant.restpojo.BookEntity;

public class LoeschenPage extends WebPage {

	public LoeschenPage(IModel<BookEntity> model) {
		super(model);

		Form<BookEntity> form = new Form<BookEntity>("form", model);
		add(form);

		form.add(new Label("titel", new PropertyModel<String>(getDefaultModel(), "name")));
		form.add(new Label("genre", new PropertyModel<String>(getDefaultModel(), "genre.name")));
		form.add(new Label("source", new PropertyModel<String>(getDefaultModel(), "source.name")));

		form.add(new Button("loeschen", new ResourceModel("loeschenText")) {
			@Override
			public void onSubmit() {
				super.onSubmit();
				deleteBock();
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

	private void deleteBock() {
		BookEntity entity = (BookEntity) this.getDefaultModelObject();
		/*
		 * Der Aufruf loescht den Wert nicht wirklich, er wird nur als geloescht
		 * markiert. Ich habe das in der BooksResponseImpl in der Methode getBooks
		 * geloest. Die Information ist in entity.getDeleted().
		 */
		try {
			URL url = new URL("http://localhost:8081/bibliothek/api/books" + "/" + entity.getId());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Content-Type", "application/json");

			ObjectMapper mapper = new ObjectMapper();
			String jasonTrackString = mapper.writeValueAsString(entity);

			OutputStream os = conn.getOutputStream();
			os.write(jasonTrackString.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
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
