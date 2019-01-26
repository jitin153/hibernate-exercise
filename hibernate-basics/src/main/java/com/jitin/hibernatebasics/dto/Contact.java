package com.jitin.hibernatebasics.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/*
 * If you want to embed the following Address class inside another class, you have to mark that class as @Embeddable
 * Whenever you'll embed this class into any other class, you'll get these 2 column into the same database table.
 */
@Embeddable
public class Contact {
	@Column(name="PHONE_NUMBER")
	private Long phone;
	
	@Column(name="EMAIL_ID")
	private String email;

	public Contact() {
	}

	public Contact(Long phone, String email) {
		this.phone = phone;
		this.email = email;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Contact [phone=" + phone + ", email=" + email + "]";
	}

}
