package de.postbank.praktikant.response;

import java.util.List;

import de.postbank.praktikant.restpojo.BookEntity;

public interface BooksResponse {

	List<BookEntity> getBooks();

}
