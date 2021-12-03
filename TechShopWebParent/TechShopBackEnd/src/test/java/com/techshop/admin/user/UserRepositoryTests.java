package com.techshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import com.techshop.common.entity.Role;
import com.techshop.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userTianhaoY = new User("yu.tia@northeastern.edu", "password", "Tianhao", "Yu");
		userTianhaoY.addRole(roleAdmin);
		
		User savedUser = repo.save(userTianhaoY);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTWORole() {
		User userPeter = new User("peter@gmail.com", "peter2021", "Peter", "Z");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userPeter.addRole(roleAssistant);
		userPeter.addRole(roleEditor);
		
		User savedUser = repo.save(userPeter);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userTianhaoY = repo.findById(1).get();
		System.out.println(userTianhaoY);
		assertThat(userTianhaoY).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userTianhaoY = repo.findById(1).get();
		userTianhaoY.setEnabled(true);
		userTianhaoY.setEmail("tianhao@gmail.com");
		
		repo.save(userTianhaoY);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userPeter = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		userPeter.getRoles().remove(roleEditor);
		userPeter.addRole(roleSalesperson);
		
		repo.save(userPeter);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String emailNull = "abc@def.com";
		String emailNotNull = "peterparker@gmail.com";
		User userNull = repo.getUserByEmail(emailNull);
		User userNotNull = repo.getUserByEmail(emailNotNull);
		
		assertNull(userNull);
		assertNotNull(userNotNull);
	}
	
	@Test
	public void testCountById() {
		assertNotNull(repo.countById(100));
		assertNotNull(repo.countById(1));
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertEquals(4, listUsers.size());
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "tony";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertEquals(1, listUsers.size());
	}
}
