package com.jitin.hqlexample.namednativequery;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jitin.hqlexample.util.HibernateUtil;

public class TestNamedNativeQuery {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		/*
		 * Test Teacher.byName
		 */
		Query query = session.getNamedQuery("Teacher.byName");
		query.setString(0, "Aniket");
		Teacher teacher = (Teacher) query.uniqueResult();
		System.out.println(teacher);
		/*
		 * Test Teacher.byId
		 */
		query = session.getNamedQuery("Teacher.byId");
		query.setInteger(0, 2);
		teacher = (Teacher) query.uniqueResult();
		System.out.println(teacher);
		session.getTransaction().commit();
		session.close();

	}

}
