package com.greatLearning.cRM.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.greatLearning.cRM.entity.customer;

@Repository
public class customerServiceImpl implements customerService {

	private SessionFactory sessionFactory;

	// create session
	private Session session;

	@Autowired
	customerServiceImpl(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

	}

	@Transactional
	public List<customer> findAll() {

		Transaction tx = session.beginTransaction();

		// find all the records from the database table
		@SuppressWarnings("unchecked")
		List<customer> customer = session.createQuery("from Customer").list();

		tx.commit();

		return customer;
	}

	@Transactional
	public customer findById(int id) {

		customer customer = new customer();

		Transaction tx = session.beginTransaction();

		// find record with Id from the database table
		customer = session.get(customer.class, id);

		tx.commit();

		return customer;
	}

	@Transactional
	public void save(customer thecustomer) {

		Transaction tx = session.beginTransaction();

		// save transaction
		session.saveOrUpdate(thecustomer);

		tx.commit();

	}

	@Transactional
	public void deleteById(int id) {

		Transaction tx = session.beginTransaction();

		// get transaction
		customer customer = session.get(customer.class, id);

		// delete record
		session.delete(customer);

		tx.commit();

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}