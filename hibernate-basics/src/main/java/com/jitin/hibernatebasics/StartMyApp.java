package com.jitin.hibernatebasics;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.jitin.hibernatebasics.dto.Address;
import com.jitin.hibernatebasics.dto.Contact;
import com.jitin.hibernatebasics.dto.Department;
import com.jitin.hibernatebasics.dto.Phone;
import com.jitin.hibernatebasics.dto.Post;
import com.jitin.hibernatebasics.dto.Student;
import com.jitin.hibernatebasics.dto.Teacher;
import com.jitin.hibernatebasics.dto.Vehicle;
import com.jitin.hibernatebasics.util.DateUtility;

public class StartMyApp {

	public static void main(String[] args) {
		// LocalDate dob = new LocalDate (1970, 1, 20);
		// LocalDate dob = LocalDate.of(1960, Month.JANUARY, 1);
		// Date dob=DateUtility.formateDate("1994/02/05", "yyyy/MM/dd");

		Date dob = DateUtility.convertLocalDateToDate(LocalDate.of(1990, Month.JANUARY, 2));

		Address officeAddress = new Address(67, "Test Street", "Test City", "Test State", 232323);
		Address homeAddress = new Address(23, "Test Street", "Test City", "Test State", 232425);

		Set<Address> addresses = new HashSet();
		addresses.add(homeAddress);
		addresses.add(officeAddress);

		List<Post> posts = Arrays.asList(new Post("Post1", "Test Post1", new Date()),
				new Post("Post2", "Test Post2", new Date()));

		Department department = new Department();
		department.setDepartmentName("Science");

		Teacher teacher = new Teacher("Narendra", dob, addresses, new Date(), "Test About");
		teacher.setPosts(posts);

		/*
		 * To establish the one to one mapping with department we have to set this
		 * department object into teacher object before saving it.
		 */
		teacher.setDepartment(department);
		Contact officeContact = new Contact(9494123543L, "narendra@wipro.com");
		teacher.setOfficeContact(officeContact);
		Contact personalContact = new Contact(9812398123L, "narendra@gmail.com");
		teacher.setPersonalContact(personalContact);

		/*
		 * START--Bidirectional or reverse relationship. Teacher to Phones & Phones to
		 * Teacher.
		 */
		Phone phoneSamsung = new Phone("Samsung", "Galaxy J-7");
		Phone phoneNokia = new Phone("Nokia", "Lumia");
		List<Phone> phones = Arrays.asList(phoneSamsung, phoneNokia);
		/*
		 * To establish the one to many mapping with phones we have to set this
		 * phones object into teacher object before saving it.
		 */
		teacher.setPhones(phones);
		/*
		 * Making many to one relationship between the Teacher & Phone. NOTE : Its
		 * better if you enclose this piece of code into a separate method for your
		 * convenience to set the phones into teacher & teacher into phones.
		 */
		phoneSamsung.setTeacher(teacher);
		phoneNokia.setTeacher(teacher);

		/*
		 * Same thing whatever you have done for phone.
		 */
		Vehicle car = new Vehicle("Car");
		Vehicle bike = new Vehicle("Bike");
		List<Vehicle> vehicles = Arrays.asList(car, bike);
		teacher.setVehicle(vehicles);
		car.setTeacher(teacher);
		bike.setTeacher(teacher);

		/* START-Many to many */
		Student student1 = new Student("Raghav");
		Student student2 = new Student("Gagan");
		List<Student> students = Arrays.asList(student1, student2);
		teacher.setStudents(students);
		student1.setTeachers(Arrays.asList(teacher));
		/*
		 * Created another teacher object to test many to many relationship. One teacher
		 * can teach many students and one student can teach by many teachers.
		 */
		Teacher teacher2 = new Teacher("Mahendra", dob, addresses, new Date(), "Test About");
		teacher2.setStudents(students);
		student2.setTeachers(Arrays.asList(teacher, teacher2));
		/* END-Many to many */

		/*
		 * END--Bidirectional or reverse relationship. Teacher to Phones & Phones to
		 * Teacher.
		 */
		
		/*
		 * Deprecated way to create session factory.
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
		try {
			session.save(teacher);
			/*
			 * Saving teacher2 object here to test many to many relationship.
			 */
			session.save(teacher2);
			session.save(department);
			/*
			 * Since Phone is separate entity not a embeddable collection therefore we have
			 * to save both Phone objects separately.
			 */
			session.save(phoneSamsung);
			session.save(phoneNokia);
			/*
			 * Here we are saving each vehicle object separately. What will happen if we
			 * have 100 or 1000 different vehicle objects. It'll be painful to save each of
			 * them separately so what we want to save all vehicle associated to a
			 * particular teacher automatically on the save of the teacher object itself &
			 * we can achieve this by using cascades. We have to mark our vehicle field into
			 * teacher entity as @OneToMany(cascade=CascadeType.PERSIST) and then you have
			 * to call persist method of session to save the teacher object rather than save
			 * method. if you do so hibernate will automatically save all the vehicle
			 * associated to a teacher. There are different Cascade Types like ALL, MERGE,
			 * REMOVE etc, you can choose according to your need. I prefer CascadeTypes.ALL.
			 */

			/*
			 * I am not using cascades here in this project. I'll create a separate
			 * example project for that.
			 */
			session.save(car);
			session.save(bike);
			session.save(student1);
			session.save(student2);
			session.getTransaction().commit();
			System.out.println("Teacher saved!");
		} catch (Exception e) {
			System.out.println("Error occurred while saving the teacher!");
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		// --Fetch Data--
		teacher = null;
		session = sessionFactory.openSession();
		teacher = (Teacher) session.get(Teacher.class, 1);
		/*
		 * Proxy Objects : Since we have collections of objects for posts & address in
		 * Teacher class, we have to use the hibernate proxy objects concept to get the
		 * value or these collections. Hibernate have 2 fetch strategies, 1.Lazy Loading
		 * & 2. Eager Loading. By default hibernate uses lazy loading. When we use the
		 * eager loading hibernate automatically creates the separate queries to get the
		 * data for those collections from the different tables & set the data into the
		 * collections without even calling any getter, where as in case of lazy loading
		 * hibernate doesn't create separate queries to get the data until & unless we
		 * don't call the getter of that particular object. So when we are going for
		 * lazy one we have to call the getter in order to get the data for that
		 * particular collection from a separate table. Eg. Suppose, We only need
		 * teacher's basic information like name or date of birth then there is no need
		 * to choose eager. In this case we can simply go for the lazy which is
		 * hibernate's default fetch strategy. Hibernate won't create unnecessary
		 * queries to get the posts & address which are there in separate tables until &
		 * unless we don't need them. If you need only posts but not addresses then
		 * simply call the getPost() method of teacher object, you'll only get the
		 * posts. If you want both posts & addresses then call both the getters of
		 * teacher object. But if you want all the data every time for all the
		 * collections, no matter how much data are there in the tables then you can go
		 * for eager one. It'll automatically creates all required select queries for
		 * all the collection objects to get the data. It's quite obvious that eager
		 * will impact the performance. To do this hibernate internally creates proxy
		 * object of that class at runtime.
		 */

		/*
		 * Here we are calling getAddresses() method to get the addresses of the
		 * teacher.
		 */
		// teacher.getAddresses();
		// teacher.getPosts();

		/*
		 * For testing purpose if we close this session here before calling the getter
		 * then we will come up with an error. But if you are using "Eager" fetch type
		 * then this will work even you have closed the session before calling the
		 * getter. In order to make your collection eager you have to mark your
		 * collection property in your model class
		 * with @ElementCollection(fetch=FetchType.EAGER). If you want lazy you can
		 * do @ElementCollection(fetch=FetchType.LAZY) or @ElementCollection. If you're
		 * not providing any fetch type to @ElementCollection it means that you want to
		 * use lazy because by default hibernate uses lazy. Eg. for eager
		 * : @ElementCollection(fetch=FetchType.EAGER) private List<Address> addresses;
		 * Eg. for lazy : @ElementCollection(fetch=FetchType.LAZY) private List<Address>
		 * addresses; or : @ElementCollection private List<Address> addresses;
		 */
		session.close();
		System.out.println(teacher.getAddresses());
		// teacher.getPosts().forEach(System.out::println);
		// session.close();
	}

}
