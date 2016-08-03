package com.jevalab.azure.persistence;

//third commit
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.jevalab.helper.classes.EntityConverter;

public class GeneralController {

	public static final DatastoreService ds = DatastoreServiceFactory
			.getDatastoreService();
	private static Transaction txn = null;

	public static List<Article> getLatestArticles(String categoryName, int i) {

		List<Article> articles = new ArrayList<>();
		Query q = new Query(Article.class.getSimpleName());
		q.setFilter(new FilterPredicate("category", FilterOperator.EQUAL,
				categoryName));
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = ds.prepare(q);
		List<Entity> ents = pq.asList(FetchOptions.Builder.withLimit(i));
		for (Entity e : ents) {
			articles.add(EntityConverter.entityToArticle(e));
		}
		return articles;
	}

	/*
	 * public static List<com.bestqualified.entities.Article>
	 * getNDiscussions(int i) { List<Article> articles = new ArrayList<>();
	 * Query q = new Query(Article.class.getSimpleName()); q.setFilter(new
	 * FilterPredicate("category", FilterOperator.EQUAL,
	 * ArticleCategory.DISCUSSION.name())); q.addSort("nComments",
	 * SortDirection.DESCENDING); PreparedQuery pq = ds.prepare(q); List<Entity>
	 * ents = pq.asList(FetchOptions.Builder.withLimit(i)); for (Entity e :
	 * ents) { articles.add(EntityConverter.entityToArticle(e)); } return
	 * articles; }
	 */

	public static List<Article> getNArticlesByDate(int no) {
		List<Article> articles = new ArrayList<>();
		Query q = new Query(Article.class.getSimpleName());
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = ds.prepare(q);
		List<Entity> ents = pq.asList(FetchOptions.Builder.withLimit(no));
		for (Entity e : ents) {
			articles.add(EntityConverter.entityToArticle(e));
		}
		return articles;
	}

	public static Iterator<Entity> findAll(String className) {
		Query q = new Query(className);
		PreparedQuery pq = ds.prepare(q);
		return pq.asIterator();
	}

	public static Entity findByKey(Key key) {
		try {
			return ds.get(key);
		} catch (EntityNotFoundException e) {

			return null;
		}
	}

	public static void delete(Key key) {
		txn = ds.beginTransaction();
		ds.delete(key);
		txn.commit();
	}

	public static void edit(Entity e) {
		txn = ds.beginTransaction();
		ds.put(e);
		txn.commit();
	}

	public static void create(Entity... e) {
		txn = ds.beginTransaction();
		List<Entity> entities = new ArrayList<>();
		for (Entity e1 : e) {
			entities.add(e1);
		}
		ds.put(entities);
		txn.commit();
	}

	public static void createWithCrossGroup(Entity... entities) {
		List<Entity> l = Arrays.asList(entities);
		txn = ds.beginTransaction(TransactionOptions.Builder.withXG(true));
		ds.put(l);
		txn.commitAsync();

	}

	public static Map<Key, Entity> findByKeys(List<Key> cKeys) {
		// TODO Auto-generated method stub
		return ds.get(cKeys);
	}

	public static Iterator<Entity> findAll(String simpleName, int i) {
		Query q = new Query(simpleName);
		PreparedQuery pq = ds.prepare(q);
		return pq.asIterator(FetchOptions.Builder.withLimit(i));
	}

	public static List<Community> findCACommunities() {
		Iterator<Entity> ents = findAll(Community.class.getSimpleName());
		List<Community> comms = new ArrayList<>();
		while (ents.hasNext()) {
			comms.add(EntityConverter.entityToCommunity(ents.next()));
		}
		return comms;
	}

	public static AzureUser findUserByLogin(String email, String password) {
		Query q = new Query(AzureUser.class.getSimpleName());
		Filter f = new FilterPredicate("EMail",
						FilterOperator.EQUAL, email.trim().toLowerCase());
		q.setFilter(f);
		PreparedQuery pq = ds.prepare(q);
		if (pq.countEntities(FetchOptions.Builder.withDefaults()) == 1) {
			AzureUser u = EntityConverter.entityToUser(pq.asSingleEntity());
			if(u.getPassword()!=null && u.getPassword().equals(password)) {
				return u;
			}else {
				return null;
			}
			
		} else {
			return null;
		}
	}

}
