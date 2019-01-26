package com.jitin.crudoperations;

import org.hibernate.Session;

import com.jitin.crudoperations.dto.User;
import com.jitin.crudoperations.util.HibernateUtil;

public class TestDetachedToPersistentObject {

	public static void main(String[] args) {
		/*
		 * Here user object it transient because hibernate hasn't been touched it yet.
		 */
		User user = new User("Test User", "Test Address");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		/*
		 * Here our user object becomes persistent because we hand over this user object
		 * to hibernate to perform a save.
		 */
		session.save(user);

		session.getTransaction().commit();
		session.close();
		/*
		 * Here our user object it detached because session has already been closed.
		 */
		user.setName("User after closed session");
		/*
		 * Here we are going to make our user object again persistent.
		 */
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		/*
		 * Again our user object becomes persistent.
		 */
		session.update(user);

		session.getTransaction().commit();
		session.close();
	}

}
