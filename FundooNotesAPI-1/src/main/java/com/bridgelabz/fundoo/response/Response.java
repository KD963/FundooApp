package com.bridgelabz.fundoo.response;

public class Response {

	public int statusCode;
	public String statusMessage;
	public Object data;

	public Response() {
	}

	public Response(int statusCode, String statusMessage, Object data) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
