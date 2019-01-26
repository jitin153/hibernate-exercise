package com.jitin.hibernatecascadeexample.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer userId;
	private String userName;
	/*
	 * We don't want to save all the phones separately thats why we using cascade.
	 * In this case hibernate will automatically save all the phones associated to
	 * user when it saves user object itself.
	 */
	/*
	@OneToMany(cascade = CascadeType.PERSIST)
	@OneToMany(cascade = CascadeType.DETACH)
	@OneToMany(cascade = CascadeType.MERGE)
	@OneToMany(cascade = CascadeType.REFRESH)
	@OneToMany(cascade = CascadeType.REMOVE)
	*/	
	
	/*
	 * For all kind of operations.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	/*
	 * If we don't use @JoinColumn(name = "USER_ID") here, hibernate will create a
	 * separate table with 2 columns USER_ID & PHONE_ID. Since we are using this
	 * hibernate add a new column USER_ID into PHONE table itself.
	 */
	@JoinColumn(name = "USER_ID")
	private List<Phone> phones;

	public User(String userName, List<Phone> phones) {
		this.userName = userName;
		this.phones = phones;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

}
