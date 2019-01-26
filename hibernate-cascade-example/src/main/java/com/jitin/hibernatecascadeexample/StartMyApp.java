package com.jitin.hibernatecascadeexample;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.jitin.hibernatecascadeexample.dto.Phone;
import com.jitin.hibernatecascadeexample.dto.User;

public class StartMyApp {

	public static void main(String[] args) {
		List<Phone> phones = Arrays.asList(new Phone("Samsung", "Galaxy J7"), new Phone("Nokia", "Asha 200"));
		User user = new User("Peter", phones);
		/*
		 * Deprecated way to create session.
		 */
		// SessionFactory sessionFactory = new
		// Configuration().configure().buildSessionFactory();

		/*
		 * This is the way to create SessionFactory in hibernate from 4.x onwards.
		 */
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		/*
		 * Here we are saving only the user object not all the phones associated with
		 * this user. Since we are using cascade hibernate will automatically save all
		 * the phones associated with this user when it saves the user object itself.
		 * Note that here calling persist method of session rather than save method.
		 */
		session.persist(user);
		session.getTransaction().commit();
		session.close();
	}

}
