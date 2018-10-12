package de.postbank.praktikant.restpojo;

import java.io.Serializable;

/**
 * @author Bjoern Schenkelberger, Postbank Systems AG
 */
public class Genre implements Serializable{
	protected Long id;
	protected String name;

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
