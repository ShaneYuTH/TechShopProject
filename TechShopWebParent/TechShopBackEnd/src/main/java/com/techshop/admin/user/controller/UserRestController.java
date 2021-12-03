package com.techshop.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techshop.admin.user.UserService;

/**
 * The Class UserRestController is used to check 
 * a status of the user without refreshing the page.
 */
@RestController
public class UserRestController {
	
	/** The UserService object service. */
	@Autowired
	private UserService service;
	
	/**
	 * Check duplicate email from service and 
	 * return a string indicating whether it's OK or Duplicate.
	 *
	 * @param id The user id
	 * @param email The user email
	 * @return The string indicating whether duplicate or not
	 */
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
		return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
	}
}
