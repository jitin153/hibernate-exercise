package com.jitin.hibernateinheritanceexample.joined;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.jitin.hibernateinheritanceexample.joined.dto.Accessory;
import com.jitin.hibernateinheritanceexample.joined.dto.Laptop;
import com.jitin.hibernateinheritanceexample.joined.dto.Phone;

public class StartMyApp {

	public static void main(String[] args) {
		Accessory accessory = new Accessory();
		accessory.setName("Phone");

		Laptop laptop = new Laptop();
		laptop.setName("Laptop");
		laptop.setLaptopManufacturer("HP");

		Phone phone = new Phone();
		phone.setName("Phone");
		phone.setPhoneManufacturer("Samsung");
		/*
		 * This is the way to create SessionFactory in hibernate from 4.x onwards.
		 */
		Configuration configuration = new Configuration();
		configuration.configure("joined.cfg.xml");
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(accessory);
		session.save(laptop);
		session.save(phone);
		session.getTransaction().commit();
		session.close();
	}
}
