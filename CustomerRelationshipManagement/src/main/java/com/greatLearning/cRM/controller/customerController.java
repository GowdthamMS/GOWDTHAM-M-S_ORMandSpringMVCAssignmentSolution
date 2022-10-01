package com.greatLearning.cRM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatLearning.cRM.entity.customer;
import com.greatLearning.cRM.service.customerService;

@Controller
@RequestMapping("/customer")
public class customerController {

	@Autowired
	private customerService customerService;

	// add mapping for "/list"

	@RequestMapping("/list")
	public String listcustomer(Model theModel) {

		System.out.println("request recieved");
		// get CustomerManagement from db
		List<customer> theCustomer = customerService.findAll();

		// add to the spring model
		theModel.addAttribute("Customer", theCustomer);

		return "list-CRM";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		customer thecustomer = new customer();

		theModel.addAttribute("Customer", thecustomer);

		return "customer-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("firstName") int theId, Model theModel) {

		// get the Customer from the service
		customer theCustomer = customerService.findById(theId);

		// set Customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);

		// send over to our form
		return "customer-form";
	}

	@PostMapping("/save")
	public String saveCustomer(int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email) {
		System.out.println(id);
		customer theCustomer;
		if (id != 0) {
			theCustomer = customerService.findById(id);
			theCustomer.setFirstName(firstName);
			theCustomer.setLastName(lastName);
			theCustomer.setEmail(email);
		} else {
			theCustomer = new customer(firstName, lastName, email);
			// save the customer
			customerService.save(theCustomer);

			// use a redirect to prevent duplicate submissions
			return "redirect:/customer/list";
		}
		return "redirect:/customer/list";
	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("firstName") int theId) {

		// delete the customer
		customerService.deleteById(theId);

		// redirect to /customer/list
		return "redirect:/customer/list";
	}
}