package com.jitin.hqlexample.namedquery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_INFO")
@NamedQueries({
	@NamedQuery(name = "Student.byRollNumber", query = "from Student where rollNumber= ?"),
	@NamedQuery(name = "Student.byName", query = "from Student where name= ?")
})
/*
 * If you have a single named query then you don't need to enclose within @NamedQueries.
 */
//@NamedQuery(name = "Student.byName", query = "from Student where name= ?")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer rollNumber;
	private String name;
	private String adress;

	public Integer getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	@Override
	public String toString() {
		return "Student [rollNumber=" + rollNumber + ", name=" + name + ", adress=" + adress + "]";
	}

}
