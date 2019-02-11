package com.jitin.crudoperations;

import org.hibernate.Session;

import com.jitin.crudoperations.dto.User;
import com.jitin.crudoperations.util.HibernateUtil;

public class TestCreate {

	public static void main(String[] args) {
		/*
		 * User user = new User("User", "Address"); is a transient object. When we use this
		 * object with session then it becomes persistent object.
		 */
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		User user;
		for (int i = 1; i <= 5; i++) {
			/*
			 * Here it's a persistent object.
			 */
			user = new User("User " + i, "Address " + i);
			session.save(user);
		}

		session.getTransaction().commit();
		session.close();
	}

}
