package com.techshop.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techshop.common.entity.Role;
import com.techshop.common.entity.User;


/**
 * The Service Class UserService. Transactional for query update
 */
@Service
@Transactional
public class UserService {
	
	/** The Constant USERS_PER_PAGE. */
	public static final int USERS_PER_PAGE = 4;

	/** The user repository. */
	@Autowired
	private UserRepository userRepo;
	
	/** The role repository. */
	@Autowired
	private RoleRepository roleRepo;
	
	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Gets user by email.
	 *
	 * @param email The selected email
	 * @return The user get by the email
	 */
	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}
	
	/**
	 * List all users in specific order.
	 *
	 * @return The list of User sort by first name in ascending order.
	 */
	public List<User> listAll() {
		return (List<User>) userRepo.findAll(Sort.by("firstName").ascending());
	}
	
	/**
	 * List by page.
	 *
	 * @param pageNum The page number
	 * @param sortField The sort field
	 * @param sortDir The sort directory
	 * @param keyword The sort keyword
	 * @return The page object with relevant attributes
	 */
	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
		
		if (keyword != null) {
			return userRepo.findAll(keyword, pageable);
		}
		
		return userRepo.findAll(pageable);
	}
	
	/**
	 * List roles.
	 *
	 * @return The list of roles
	 */
	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();
	}

	/**
	 * Save user.
	 *
	 * @param user The selected user
	 * @return The saved user object
	 */
	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		
		if (isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			
			if (user.getPassword().isEmpty()) {
				// If updating an existing user and no new password
				user.setPassword(existingUser.getPassword());
			} else {
				// Else we encode new password
				encodePassword(user);
			}
		} else {
			// If save a new user, then encode
			encodePassword(user);
		}
		

		return userRepo.save(user);
		
	}
	
	/**
	 * Update logged in account.
	 *
	 * @param userInForm The user in form (logged in user)
	 * @return The updated user
	 */
	public User updateAccount(User userInForm) {
		User userInDB = userRepo.findById(userInForm.getId()).get();
		
		// update password if given new password
		if (!userInForm.getPassword().isEmpty()) {
			userInDB.setPassword(userInForm.getPassword());
			encodePassword(userInDB);
		}
		
		// update pic if given new pic
		if (userInForm.getPhotos() != null) {
			userInDB.setPhotos(userInForm.getPhotos());
		}
		
		userInDB.setFirstName(userInForm.getFirstName());
		userInDB.setLastName(userInForm.getLastName());
		
		return userRepo.save(userInDB);
	}
	
	/**
	 * Encode password. Using spring security BCrypt to encode password
	 *
	 * @param user The selected user
	 */
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	/**
	 * Checks if is email unique.
	 *
	 * @param id The selected user's id
	 * @param email The selected user's email
	 * @return true, if email is unique, false otherwise
	 */
	public boolean isEmailUnique(Integer id, String email) {
		// see if any user existed with selected email
		User userByEmail = userRepo.getUserByEmail(email);
		
		if (userByEmail == null) {
			return true;
		}
		
		boolean isCreatingNew = (id == null);
		
		if (isCreatingNew) {
			if (userByEmail != null) {
				return false;
			}
		} else {
			// Not Creating New, but two user's ids no match
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Gets the user by id.
	 *
	 * @param id The selected id
	 * @return The user with selected id
	 * @throws UserNotFoundException the user not found exception
	 */
	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}

	}
	
	/**
	 * Delete user.
	 *
	 * @param id The selected user's id
	 * @throws UserNotFoundException Signals the user not found exception
	 */
	public void delete(Integer id) throws UserNotFoundException {
		// check if any id in the repository
		Long countById = userRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("User ID " + id + " Not Found.");
		}
		
		userRepo.deleteById(id);
	}
	
	/**
	 * Update user enabled status.
	 *
	 * @param id The selected user's id
	 * @param enabled The enabled status
	 */
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}
}
