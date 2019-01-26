package com.jitin.hibernateinheritanceexample.joined.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
/*
 * @Inheritance isn't mandatory. By default hibernate uses SINGLE_TABLE strategy
 * for inheritance but here we are implementing the JOINED strategy. In this
 * case @Inheritance(strategy = InheritanceType.JOINED) is mandatory thing to
 * do.
 */
@Inheritance(strategy = InheritanceType.JOINED)
/*
 * In case of JOINED strategy hibernate doesn't create any separate column
 * called DTYPE therefore we can't use @DiscriminatorColumn value here.
 */
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
