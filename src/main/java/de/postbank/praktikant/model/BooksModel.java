package de.postbank.praktikant.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.wicket.model.LoadableDetachableModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.postbank.praktikant.response.BooksResponse;
import de.postbank.praktikant.response.BooksResponseImpl;

public class BooksModel extends LoadableDetachableModel<BooksResponse> {

	@Override
	protected BooksResponse load() {
		BooksResponse result = new BooksResponseImpl();
		try {
			URL url = new URL("http://localhost:8081/bibliothek/api/books");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(new InputStreamReader(conn.getInputStream()), BooksResponseImpl.class);
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
