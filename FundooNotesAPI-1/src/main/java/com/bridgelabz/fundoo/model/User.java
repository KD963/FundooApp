package com.bridgelabz.fundoo.model;

import java.io.Serializable;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Details
 * 
 * @author xyz
 *
 */
@Document
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The user id */
	@Id
	private String userId;

	private String name;

	private String emailId;

	private String phone;

	private String password;

	// private String token;

	private boolean isVerify;

	private LocalDateTime createStamp = LocalDateTime.now();

	public User() {
		super();
	}

	public User(String userId, String name, String emailId,
			String phone, String password, boolean isVerify, LocalDateTime createStamp) {
		super();
		this.userId = userId;
		this.name = name;
		this.emailId = emailId;
		this.phone = phone;
		this.password = password;
		this.isVerify = isVerify;
		this.createStamp = createStamp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerify() {
		return isVerify;
	}

	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", emailId=" + emailId + ", phone=" + phone + ", password="
				+ password + ", isVerify=" + isVerify + ", createStamp=" + createStamp + "]";
	}
}
