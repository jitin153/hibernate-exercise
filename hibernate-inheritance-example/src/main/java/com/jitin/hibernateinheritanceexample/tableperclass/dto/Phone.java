package com.jitin.hibernateinheritanceexample.tableperclass.dto;

import javax.persistence.Entity;

@Entity
public class Phone extends Accessory{
	private String phoneManufacturer;

	public String getPhoneManufacturer() {
		return phoneManufacturer;
	}

	public void setPhoneManufacturer(String phoneManufacturer) {
		this.phoneManufacturer = phoneManufacturer;
	}
}
