package com.jitin.crudoperations;

import org.hibernate.Session;

import com.jitin.crudoperations.dto.User;
import com.jitin.crudoperations.util.HibernateUtil;

public class TestUpdate {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, 3);
		user.setName("Updated user");
		/*
		 * In order to update the record we don't need to call update method. Hibernate
		 * keep tracking the persistent object. As soon as anything changes in the
		 * persistent object hibernate will automatically trigger update.
		 */
		// session.update(user);
		System.out.println(user);
		session.getTransaction().commit();
		session.close();
	}

}
