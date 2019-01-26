package com.jitin.hqlexample.namednativequery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(name="Teacher.byName",query="select * from teacher_info where name = ?", resultClass=Teacher.class),
	@NamedNativeQuery(name="Teacher.byId",query="select * from teacher_info where id = ?", resultClass=Teacher.class)
})
/*
 * If you have a single named query then you don't need to enclose within @NamedQueries.
 */
//	@NamedNativeQuery(name="Teacher.byId",query="select * from teacher_info where id = ?", resultClass=Teacher.class)
@Table(name="TEACHER_INFO")
public class Teacher {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String address;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + ", address=" + address + "]";
	}

}
