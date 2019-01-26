package com.jitin.hibernateinheritanceexample.tableperclass.dto;

import javax.persistence.Entity;

@Entity
public class Laptop extends Accessory{
	private String laptopManufacturer;

	public String getLaptopManufacturer() {
		return laptopManufacturer;
	}

	public void setLaptopManufacturer(String laptopManufacturer) {
		this.laptopManufacturer = laptopManufacturer;
	}
}
