package com.jitin.hqlexample.criteriaapi;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.jitin.hqlexample.query.User;
import com.jitin.hqlexample.util.HibernateUtil;

public class TestCriteriaApi {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		/*
		 * Test equals to restriction.
		 */
		Criteria criteriaEq = session.createCriteria(User.class);
		criteriaEq.add(Restrictions.eq("id", 3)).add(Restrictions.eq("name", "User 3"));
		User user = (User) criteriaEq.uniqueResult();
		System.out.println(user);
		/*
		 * Test greater than equals to & less than equals to restriction.
		 */
		Criteria criteriaGeLe = session.createCriteria(User.class);
		criteriaGeLe.add(Restrictions.ge("id", 5)).add(Restrictions.le("id", 9));
		List<User> users = (List<User>) criteriaGeLe.list();
		System.out.println(users);
		/*
		 * Test less than & greater than restriction.
		 */
		Criteria criteriaLessThanGreaterThan = session.createCriteria(User.class);
		criteriaLessThanGreaterThan.add(Restrictions.lt("id", 8)).add(Restrictions.gt("id", 4));
		users = (List<User>) criteriaLessThanGreaterThan.list();
		System.out.println(users);
		/*
		 * Test like restriction.
		 */
		Criteria criteriaLike1 = session.createCriteria(User.class);
		criteriaLike1.add(Restrictions.like("name", "User 1%"));
		users = (List<User>) criteriaLike1.list();
		System.out.println(users);

		Criteria criteriaLike2 = session.createCriteria(User.class);
		criteriaLike2.add(Restrictions.like("name", "%r 1%"));
		users = (List<User>) criteriaLike2.list();
		System.out.println(users);
		/*
		 * Test in restriction.
		 */
		Criteria criteriaIn = session.createCriteria(User.class);
		criteriaIn.add(Restrictions.in("id", Arrays.asList(2, 5, 8)));
		users = (List<User>) criteriaIn.list();
		System.out.println(users);
		/*
		 * Test not equal to restriction.
		 */
		Criteria criteriaNe = session.createCriteria(User.class);
		criteriaNe.add(Restrictions.ne("id", 2));
		users = (List<User>) criteriaNe.list();
		System.out.println(users);
		/*
		 * Test between restriction.
		 */
		Criteria criteriaBetween = session.createCriteria(User.class);
		criteriaBetween.add(Restrictions.between("id", 2, 7));
		users = (List<User>) criteriaBetween.list();
		System.out.println(users);
		/*
		 * Test OR restriction.
		 */
		Criteria criteriaOr1 = session.createCriteria(User.class);
		criteriaOr1.add(Restrictions.or(Restrictions.eq("id", 11), Restrictions.like("name", "%1%")));
		users = (List<User>) criteriaOr1.list();
		System.out.println(users);

		Criteria criteriaOr2 = session.createCriteria(User.class);
		criteriaOr2.add(Restrictions.or(Restrictions.between("id", 2, 4), Restrictions.between("id", 7, 9)));
		users = (List<User>) criteriaOr2.list();
		System.out.println(users);
		/*
		 * Test projection.
		 */
		Criteria criteriaProjection1 = session.createCriteria(User.class).setProjection(Projections.count("id"));
		Long count = (Long) criteriaProjection1.uniqueResult();
		System.out.println("Count : " + count);

		Criteria criteriaProjection2 = session.createCriteria(User.class).setProjection(Projections.property("name"));
		users = (List<User>) criteriaProjection2.list();
		System.out.println(users);
		/*
		 * Test Example
		 */

		/*
		 * Example is useful when you have lot of properties that you need to set in
		 * your query, you don't have to do that you can just create an object which has
		 * all those properties & then you can pass that object into example. Hibernate
		 * ignore 2 things when it comes to taking example. 1. Any of the property has
		 * value as null then it's not gonna consider that. 2. If the property is a
		 * primary key. Apart from null property & primary key property if you have set
		 * any value in any of the property then that is what hibernate considers when
		 * its pulling up data.
		 */
		User user2 = new User();
		// user2.setId(5);
		user2.setName("User 5");
		// user2.setAdress("Bareilly");

		Example example = Example.create(user2);
		/*
		 * Suppose you have to 10 properties into your entity & you know that some
		 * properties is gonna have a value or it might have a value & I don't want
		 * hibernate to consider that property in that case you can exclude those
		 * properties.
		 */
		//Example example = Example.create(user2).excludeProperty("address").excludeProperty("phone");
		Criteria criteriaExample = session.createCriteria(User.class).add(example);
		users = (List<User>) criteriaExample.list();
		System.out.println("Example : " + users);
		
		/*
		 * Test enable like with example.
		 */
		User user3 = new User();
		user3.setName("User 1%");

		Example example2 = Example.create(user3).enableLike();
		Criteria criteriaExampleEnableLike = session.createCriteria(User.class).add(example2);
		users = (List<User>) criteriaExampleEnableLike.list();
		System.out.println("Example enable like : " + users);
		/*
		 * Test order.
		 */
		Criteria criteriaOrder = session.createCriteria(User.class).addOrder(Order.desc("id"));
		users = (List<User>) criteriaOrder.list();
		System.out.println(users);
		session.getTransaction().commit();
		session.close();
	}

}
