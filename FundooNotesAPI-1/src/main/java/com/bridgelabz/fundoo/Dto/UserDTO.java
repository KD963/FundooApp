package com.bridgelabz.fundoo.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

	
	@NotNull
	@Size(min=2, message="Name should have atleast 2 characters")
	private String name;
	@NotNull
	@NotEmpty(message = "email id should not be empty")
	private String emailId;
	@NotNull
	@NotEmpty(message = "phone number should not be empty")
	private String phoneNumber;
	@NotNull
	@NotEmpty(message = "password should not be empty")
	private String password;

	public UserDTO() {

	}

	public UserDTO(String name, String emailId, String phoneNumber, String password) {
		
		super();
		this.name = name;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", emailId=" + emailId + ", phoneNumber=" + phoneNumber + ", password="
				+ password + "]";
	}

}
