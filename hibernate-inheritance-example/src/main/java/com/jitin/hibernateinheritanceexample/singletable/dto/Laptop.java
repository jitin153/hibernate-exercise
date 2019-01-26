package com.jitin.hibernateinheritanceexample.singletable.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
/*
 * By default hibernate store value to DTYPE is the class name itself but if you
 * want to change the value to DTYPE from class name to something else then you
 * have to use @DiscriminatorValue.
 */
@DiscriminatorValue("Laptop")
public class Laptop extends Accessory {
	private String laptopManufacturer;

	public String getLaptopManufacturer() {
		return laptopManufacturer;
	}

	public void setLaptopManufacturer(String laptopManufacturer) {
		this.laptopManufacturer = laptopManufacturer;
	}
}
