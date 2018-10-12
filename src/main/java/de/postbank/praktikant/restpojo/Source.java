package de.postbank.praktikant.restpojo;

import java.io.Serializable;

/**
 * @author Bjoern Schenkelberger, Postbank Systems AG
 */
public class Source implements Serializable{
	protected Long id;
	private String name;

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
}
