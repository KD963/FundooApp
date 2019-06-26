package com.bridgelabz.fundoo.model;

public class Email {

	
	private String emailId;
	private String to;
	private String sub;
	private String body;
	
	public Email() {}

	public Email(String emailId, String to, String sub, String body) {
		super();
		this.emailId = emailId;
		this.to = to;
		this.sub = sub;
		this.body = body;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
