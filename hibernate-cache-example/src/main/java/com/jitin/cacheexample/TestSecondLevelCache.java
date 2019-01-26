package com.jitin.cacheexample;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jitin.cacheexample.dto.User;
import com.jitin.cacheexample.util.HibernateUtil;

public class TestSecondLevelCache {

	public static void main(String[] args) {
		/*
		 * By default hibernate uses first level cache which means that in between a
		 * session hibernate caches the result but when you close the session, cache
		 * gets cleared. Suppose you have already executed a select query bu using
		 * session.get() method. In this case hibernate will execute a select query to
		 * get the result and cache the same result. Now if you try to execute the same
		 * session.get() method in the same session, hibernate won't execute the select
		 * query again. Hibernate will return the result from cache. Even if you execute
		 * some update queries after that & then you are trying to fetch the result
		 * still hibernate won't execute a new select query to get the result because
		 * cache the change by update query as well & return you the update object from
		 * cache. So it this all about first level cache which hibernate uses by
		 * default. But what when you close the session & still want to cache results
		 * then second level cache comes into picture. To enable the second level cache
		 * you have to do small configuration into your hibernate.cfg.xml file. You have
		 * to put below 2 properties into your hibernate.cfg.xml file. <property
		 * name="cache.use_second_level_cache">true</property> <property
		 * name="cache.provider_class">org.hibernate.cache.EhCacheProvider</property>
		 * Here I'm using EhCache.
		 */
		Session session1 = HibernateUtil.getSessionFactory().openSession();
		session1.beginTransaction();
		User user1 = (User) session1.get(User.class, 2);
		System.out.println(user1);
		session1.getTransaction().commit();
		session1.close();
		/*
		 * We are trying to get the same result here in a different session. When you
		 * will look into your console, you would see hibernate fires only only select
		 * query. This is because of second level cache.
		 */
		Session session2 = HibernateUtil.getSessionFactory().openSession();
		session2.beginTransaction();
		User user2 = (User) session2.get(User.class, 2);
		System.out.println(user2);
		session2.getTransaction().commit();
		session2.close();

		/*
		 * Second level cache with query. In order to user query cache we have to add a
		 * property into hibernate.cfg.xml file. <property
		 * name="hibernate.cache.use_query_cache">true</property>
		 */

		Session session3 = HibernateUtil.getSessionFactory().openSession();
		session3.beginTransaction();
		Query query1 = session3.createQuery("from User where id=3");
		/*
		 * In order to make a query cacheable you have to set a setCacheable property as
		 * true in both the places, where you want to cache the result of the query &
		 * where you want to get the same result from the cache which has already
		 * cached. Here setCacheable will cache the result of this query.
		 */
		query1.setCacheable(true);
		User user3 = (User) query1.uniqueResult();
		System.out.println(user3);
		session3.getTransaction().commit();
		session3.close();

		Session session4 = HibernateUtil.getSessionFactory().openSession();
		session4.beginTransaction();
		Query query2 = session4.createQuery("from User where id=3");
		/*
		 * Here setCacheable tells the query to look at second level cache & see if its
		 * already there.
		 */

		/*
		 * setCacheable property is not only caches the result it also tells query to
		 * look at second level cache see if its already there.
		 */
		query2.setCacheable(true);
		User user4 = (User) query2.uniqueResult();
		System.out.println(user4);
		session4.getTransaction().commit();
		session4.close();

		/*
		 * NOTE : We need to use query cache a bit carefully. In query it's very easy to
		 * make whole system very inefficient just by caching something which is not
		 * really required to be cached or resulting in situation where cache is getting
		 * updated very often because you're caching something that updated often so we
		 * need to use this with care.
		 */
	}

}
