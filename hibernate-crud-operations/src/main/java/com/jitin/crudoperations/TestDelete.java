package com.jitin.crudoperations;

import org.hibernate.Session;

import com.jitin.crudoperations.dto.User;
import com.jitin.crudoperations.util.HibernateUtil;

public class TestDelete {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, 3);
		session.delete(user);
		System.out.println("User deleted!");
		session.getTransaction().commit();
		session.close();
	}

}
