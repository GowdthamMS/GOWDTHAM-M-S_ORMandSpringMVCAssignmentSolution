package com.greatLearning.cRM.service;

import java.util.List;

import com.greatLearning.cRM.entity.customer;

public interface customerService {
	public List<customer> findAll();

	public customer findById(int theId);

	public void save(customer theCustomer);

	public void deleteById(int theId);
}