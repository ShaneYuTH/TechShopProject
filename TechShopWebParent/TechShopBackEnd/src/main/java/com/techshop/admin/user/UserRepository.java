package com.techshop.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.techshop.common.entity.User;

/**
 * The Interface UserRepository, extending PagingAndSortingRepository.
 * Using custom queries @Query annotation.
 * No need for concrete methods as Spring Data JPA will generate implementation classes
 * at runtime.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	
	/**
	 * Gets the user by email.
	 *
	 * @param email The specific email
	 * @return The user by email
	 */
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	/**
	 * Count by id.
	 *
	 * @param id The user's id
	 * @return The number of ids that match
	 */
	public Long countById(Integer id);
	
	/**
	 * Find all pages.
	 *
	 * @param keyword The searching keyword
	 * @param pageable The pageable object
	 * @return The page of users object
	 */
	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', "
			+ "u.lastName) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	
	/**
	 * Update enabled status. Using @Modyfying to update queries.
	 *
	 * @param id The user's id
	 * @param enabled The enabled status
	 */
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id= ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
}
