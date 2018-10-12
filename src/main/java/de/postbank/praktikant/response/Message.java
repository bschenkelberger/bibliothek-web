package de.postbank.praktikant.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Bjoern Schenkelberger, Postbank Systems AG
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable{
    private Severity severity;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public Message() {
    }

    public Message(Severity severity, String message) {
        this.severity = severity;
        this.message = message;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
