package com.jitin.hibernatebasics.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Vehicle {
	@Id
	@GeneratedValue
	private Integer vehicleId;
	private String vehicle;
	/*
	 * Trying to establish many to one relationship in a slight different way.
	 */
	@ManyToOne
	@JoinColumn(name = "TEACHER_ID")
	/*
	 * Suppose if you are trying to fetch vehicle which does'nt have any owner. In
	 * this case hibernate will throw an exception. To overcome this issue we use
	 * this @NotFound annotation and simply ignore the exception.
	 */
	@NotFound(action = NotFoundAction.IGNORE)
	private Teacher teacher;

	public Vehicle() {
	}

	public Vehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", vehicle=" + vehicle + ", teacher=" + teacher + "]";
	}
}
