package de.postbank.praktikant.restpojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Bjoern Schenkelberger, Postbank Systems AG
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookGenreEntity extends Genre {
	private List<BookEntity> bookEntityList = new ArrayList<>();

	public List<BookEntity> getBookEntityList() {
		return bookEntityList;
	}

	public void setBookEntityList(List<BookEntity> bookEntityList) {
		this.bookEntityList = bookEntityList;
	}
}
