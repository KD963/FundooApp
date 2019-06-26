package com.bridgelabz.fundoo.model;

import java.io.Serializable;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Details
 * 
 * @author Kalyani Deobhankar
 *
 */
@Document(collection = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The user id */
	@Id
	private String userId;

	private String name;

	private String email;

	private String phone;

	private String password;

	private boolean isVerify;

	private LocalDateTime createStamp = LocalDateTime.now();

	public User() {

	}

	public User(String userId, String name, String email, String phone, String password, boolean isVerify,
			LocalDateTime createStamp) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public LocalDateTime getCreateStamp() {
		return createStamp;
	}

	public void setCreateStamp(LocalDateTime createStamp) {
		this.createStamp = createStamp;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", phone=" + phone + ", password="
				+ password + ", isVerify=" + isVerify + ", createStamp=" + createStamp + "]";
	}

}
