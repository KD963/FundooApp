package com.bridgelabz.fundoo.Dto;

public class ForgotDTO {

	private String password;

	public ForgotDTO() {

	}

	public ForgotDTO(String password, String confirmpassword) {
		super();
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "ForgotDTO [password=" + password + "]";
	}

}
