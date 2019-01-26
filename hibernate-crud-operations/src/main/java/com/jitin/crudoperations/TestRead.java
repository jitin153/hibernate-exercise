package com.jitin.crudoperations;

import org.hibernate.Session;

import com.jitin.crudoperations.dto.User;
import com.jitin.crudoperations.util.HibernateUtil;

public class TestRead {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, 3);
		// System.out.println(user);
		session.getTransaction().commit();
		session.close();
		/*
		 * We can now print the user object here after closing the session we have
		 * already pulled the first level data before closing the session.
		 */
		System.out.println(user);

		/*
		 * Fetch result by using load method rather than get.
		 */
		Session session2 = HibernateUtil.getSessionFactory().openSession();
		session2.beginTransaction();
		/*
		 * load is better than get in term of performance. If use are using load to
		 * fetch the data hibernate won't fire a select query until & unless you are not
		 * using that object which holds the result but in case of get hibernate will
		 * fire a query even if you are not using that object which holds the result.
		 */
		User user2 = (User) session2.load(User.class, 3);
		session2.getTransaction().commit();
		/*
		 * We didn't use the object user2 anywhere in our program. Hibernate won't fire
		 * any select query for this object. Look into your IDE console to test.
		 */
		session2.close();

		Session session3 = HibernateUtil.getSessionFactory().openSession();
		session3.beginTransaction();
		User user3 = (User) session3.load(User.class, 3);
		session3.getTransaction().commit();
		/*
		 * Here we are using user3 object therefore hibernate will fire a select query
		 * to hit the database.
		 */
		System.out.println(user3);
		session3.close();
		/*
		 * You can't use this user3 object here after closing the session. But in case
		 * of get you can do the same because when you use get immediately hibernate
		 * fires select query to get the result & you can use that object after closing
		 * the session which holds the result because first level data has already been
		 * fetched. But in case of load hibernate won't fires select query until
		 * & unless you don't use that object which holds the result & after closing session
		 * you can't fire a query thats why you can't use that object after closing the
		 * session.
		 */
		// System.out.println(user3);
	}
}
