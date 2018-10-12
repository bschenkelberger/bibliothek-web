package de.postbank.praktikant.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bjoern Schenkelberger, Postbank Systems AG
 */
public class BaseResponse {
	private Boolean successful;
	private List<Message> messages = new ArrayList<>();

	public Boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
