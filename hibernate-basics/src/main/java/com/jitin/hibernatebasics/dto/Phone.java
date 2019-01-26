package com.jitin.hibernatebasics.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Phone {
	@Id
	@GeneratedValue
	private Integer phoneId;
	private String manufacturer;
	private String model;
	
	/*
	 * Here we're trying to establish the many to one relationship between the Teacher & the Phone.
	 */
	@ManyToOne
	private Teacher teacher;

	public Phone(String manufacturer, String model) {
		this.manufacturer = manufacturer;
		this.model = model;
	}

	public Integer getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(Integer phoneId) {
		this.phoneId = phoneId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "Phone [phoneId=" + phoneId + ", manufacturer=" + manufacturer + ", model=" + model + ", teacher=" + teacher
				+ "]";
	}

}
