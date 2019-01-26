package com.jitin.hibernateinheritanceexample.tableperclass.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
/*
 * @Inheritance isn't mandatory. By default hibernate uses SINGLE_TABLE strategy
 * for inheritance but here we are implementing the TABLE_PER_CLASS strategy. In
 * this case @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) is
 * mandatory thing to do.
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
/*
 * In case of TABLE_PER_CLASS strategy hibernate doesn't create any separate
 * column called DTYPE therefore we can't use @DiscriminatorColumn value here.
 */
public class Accessory {
	@Id
	/*
	 * 
	 * When you use TABLE_PER_CLASS strategy, you have to
	 * use @GeneratedValue(strategy=GenerationType.TABLE) in order to auto generate
	 * keys otherwise you will come up with an error.
	 */
	@GeneratedValue(strategy = GenerationType.TABLE)
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
