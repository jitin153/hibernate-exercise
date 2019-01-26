package com.jitin.hibernateinheritanceexample.singletable.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
/*
 * By default hibernate store value to DTYPE is the class name itself but if you
 * want to change the value to DTYPE from class name to something else then you
 * have to use @DiscriminatorValue.
 */
@DiscriminatorValue("Smart Phone")
public class Phone extends Accessory{
	private String phoneManufacturer;

	public String getPhoneManufacturer() {
		return phoneManufacturer;
	}

	public void setPhoneManufacturer(String phoneManufacturer) {
		this.phoneManufacturer = phoneManufacturer;
	}
}
