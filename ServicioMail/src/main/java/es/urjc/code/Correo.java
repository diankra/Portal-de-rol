package es.urjc.code;

import java.io.Serializable;

public class Correo implements Serializable{

	private static final long serialVersionUID = -6586361718037738125L;
	
	private String from, to, subject, body, username, password;
	
	public Correo () {
		
	}
	
	public Correo (String from, String to, String subject, String body, String username, String password) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.username = username;
		this.password = password;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
