package com.jitin.hibernateinheritanceexample.singletable.dto;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
/*
 * @Inheritance isn't mandatory. By default hibernate uses SINGLE_TABLE strategy
 * for inheritance.
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/*
 * In case of SINGLE_TABLE strategy hibernate creates separate column called
 * DTYPE into the table which store the class name. This is also not a mandatory
 * configuration.
 */
@DiscriminatorColumn(name = "ACCESSORY_TYPE", discriminatorType = DiscriminatorType.STRING)
public class Accessory {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
