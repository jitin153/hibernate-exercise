package com.jitin.hqlexample.query;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jitin.hqlexample.util.HibernateUtil;

public class TestQuery {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		/*
		 * Fetch all records. "User" isn't a table name its your object.
		 */
		Query query = session.createQuery("FROM User");
		List<User> users = query.list();
		users.forEach(System.out::println);
		/*
		 * Fetch conditionally. id isn't a table column name its the member variable of
		 * your User class.
		 */
		query = session.createQuery("FROM User where id>=5");
		users = query.list();
		users.forEach(System.out::println);

		query = session.createQuery("FROM User where id>5 and id<9");
		users = query.list();
		users.forEach(System.out::println);
		/*
		 * Pagination.
		 */
		query = session.createQuery("FROM User");
		/*
		 * From where you want to start.
		 */
		query.setFirstResult(3);
		/*
		 * How much records you want to fetch from starting point.
		 */
		query.setMaxResults(5);
		users = query.list();
		users.forEach(System.out::println);
		/*
		 * Fetch user name only.
		 */
		query = session.createQuery(" SELECT name FROM User");
		List<String> usernames = query.list();
		usernames.forEach(System.out::println);
		/*
		 * Fetch data in the form of map.
		 */
		query = session.createQuery(" SELECT new map(id,name) FROM User");
		List<Map<Integer, String>> userMap = query.list();
		userMap.forEach(System.out::println);
		/*
		 * Aggregate functions.
		 */
		query = session.createQuery(" SELECT max(id) FROM User");
		Integer maxId = (Integer) query.uniqueResult();
		System.out.println("Max id : " + maxId);

		query = session.createQuery(" SELECT count(id) FROM User");
		Long count = (Long) query.uniqueResult();
		System.out.println("Count of ids : " + count);

		query = session.createQuery(" SELECT avg(id) FROM User");
		Double average = (Double) query.uniqueResult();
		System.out.println("Avegare of ids : " + average);

		query = session.createQuery(" SELECT sum(id) FROM User");
		Long sum = (Long) query.uniqueResult();
		System.out.println("Sum of ids : " + sum);
		/*
		 * Parameterized query : Two different approaches to set parameters. Note : It's
		 * highly recommended to use parameterized query to avoid SQL injection.
		 */
		query = session.createQuery("FROM User where id > ? and id< ?");
		query.setInteger(0, 3);
		query.setInteger(1, 7);
		users = query.list();
		users.forEach(System.out::println);

		query = session.createQuery("FROM User where id = :userid");
		query.setInteger("userid", 8);
		User user1 = (User) query.uniqueResult();
		System.out.println(user1);

		query = session.createQuery("FROM User where name = :username");
		query.setString("username", "User 7");
		User user2 = (User) query.uniqueResult();
		System.out.println(user2);

		query = session.createQuery("FROM User where id > :userid and name = :username");
		query.setInteger("userid", 5);
		query.setString("username", "User 9");
		User user3 = (User) query.uniqueResult();
		System.out.println(user3);

		session.getTransaction().commit();
		session.close();
	}

}
