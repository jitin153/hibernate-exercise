package com.jitin.hqlexample.namedquery;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jitin.hqlexample.util.HibernateUtil;

public class TestNamedQuery {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		/*
		 * Test Student.byRollNumber
		 */
		Query query = session.getNamedQuery("Student.byRollNumber");
		query.setInteger(0, 3);
		Student student = (Student) query.uniqueResult();
		System.out.println(student);
		/*
		 * Test Student.byName
		 */
		query = session.getNamedQuery("Student.byName");
		query.setString(0, "Gagan");
		student = (Student) query.uniqueResult();
		System.out.println(student);
		session.getTransaction().commit();
		session.close();
	}

}
