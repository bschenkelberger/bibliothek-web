package de.postbank.praktikant.restpojo;

import java.io.Serializable;

/**
 * @author Bjoern Schenkelberger, Postbank Systems AG
 */
public class BookEntity implements Serializable{
	private Long id;
	private String name;
	private Boolean lend;
	private Boolean deleted;
	private Genre genre;
	private Source source;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getLend() {
		return lend;
	}

	public void setLend(Boolean lend) {
		this.lend = lend;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}
}
