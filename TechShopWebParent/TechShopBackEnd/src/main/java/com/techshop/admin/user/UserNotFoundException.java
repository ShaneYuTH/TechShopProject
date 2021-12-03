package com.techshop.admin.user;

/**
 * The Class UserNotFoundException. Signaling no specific user found
 */
public class UserNotFoundException extends Exception {

	/**
	 * Instantiates a new user not found exception.
	 *
	 * @param message The message signaling no specific user found
	 */
	public UserNotFoundException(String message) {
		super(message);
	}

}
