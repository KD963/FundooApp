package com.bridgelabz.fundoo.exception;

public class NoteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public NoteException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
