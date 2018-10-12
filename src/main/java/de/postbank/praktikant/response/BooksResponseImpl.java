package de.postbank.praktikant.response;

import java.util.ArrayList;
import java.util.List;

import de.postbank.praktikant.restpojo.BookEntity;

public class BooksResponseImpl extends BaseResponse implements BooksResponse {

	private List<BookEntity> books = new ArrayList<>();

	@Override
	public List<BookEntity> getBooks() {
		return books;
	}

}
