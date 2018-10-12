package de.postbank.praktikant.restpojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Bjoern Schenkelberger, Postbank Systems AG
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookSourceEntity extends Source {
	private List<BookEntity> bookEntities;

	public List<BookEntity> getBookEntities() {
		return bookEntities;
	}

	public void setBookEntities(List<BookEntity> bookEntities) {
		this.bookEntities = bookEntities;
	}
}
