package com.jitin.crudoperations;

import org.hibernate.Session;

import com.jitin.crudoperations.dto.User;
import com.jitin.crudoperations.util.HibernateUtil;

public class TestPersistentObject {

	public static void main(String[] args) {
		/*
		 * Here the user object is a transient object. A transient state of an object
		 * means hibernate hasn't been touched this object yet.
		 */
		User user = new User("Test User", "Test Address");
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		/*
		 * Here our user object becomes persistent becuase here we handover our user
		 * object to hibernate to perform a save.
		 */
		session.save(user);
		/*
		 * Hibernate will automatically trigger an update after insert. How does
		 * hibernate know what changes to get reflected into the database? The answer is
		 * once you pass an user entity object to session.save() after that any change
		 * you make to that object will go as an update statement. Note : The last state
		 * of the user object will be the state of database. Eg. you can set the
		 * username as many times as you want before commit the transaction but in
		 * database you'll get the last username that you set.
		 */

		/*
		 * Here we are setting the username thrice but hibernate will trigger update for
		 * the last one only that is "Updated User 3". In database table "Update User 3"
		 * will be store as an username. Hibernate is intelligent enough to detect what
		 * update needs to go into the database.
		 */
		user.setName("Updated User 1");
		user.setName("Updated User 2");
		user.setName("Updated User 3");
		session.getTransaction().commit();
		session.close();
		/*
		 * Once you do session.close() it becomes a detached object. Detached object is
		 * similar to the transient object in the sense of hibernate isn't going to
		 * track the changes anymore. Detached object means it was tracked or persisted
		 * by hibernate before but now the session has been closed. It is no longer
		 * tracked by hibernate, now it is in a detached state.
		 */
	}

}
