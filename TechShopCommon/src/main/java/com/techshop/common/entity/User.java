package com.techshop.common.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The User Entity Class. 
 * The class is an entity and is mapped to a database table.
 */
@Entity
@Table(name = "users")
public class User {
	
	/** The generated User id in database, no reusable. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/** The User email. */
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	
	/** The User password. */
	@Column(length = 64, nullable = false)
	private String password;
	
	/** The User first name. */
	@Column(name = "first_name", length = 45, nullable = false)
	private String firstName;
	
	/** The User last name. */
	@Column(name = "last_name", length = 45, nullable = false)
	private String lastName;
	
	/** The User photos. */
	@Column(length = 64)
	private String photos;
	
	/** The User enabled status. */
	private boolean enabled;
	
	/** The roles. */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	/**
	 * Default User constructor. Instantiates a new user.
	 */
	public User() {
	}
	
	/**
	 * Instantiates a new user with email, password, firstName and lastName.
	 *
	 * @param email The user's email
	 * @param password The user's password
	 * @param firstName The user's first name
	 * @param lastName the user's last name
	 */
	public User(String email, String password, String firstName, String lastName) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}



	/**
	 * Gets the id.
	 *
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id The new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the email.
	 *
	 * @return The email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email The new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password.
	 *
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password The new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the first name.
	 *
	 * @return The first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName The new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return The last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName The new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the photos.
	 *
	 * @return The photos
	 */
	public String getPhotos() {
		return photos;
	}

	/**
	 * Sets the photos.
	 *
	 * @param photos The new photos
	 */
	public void setPhotos(String photos) {
		this.photos = photos;
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled The new enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the roles.
	 *
	 * @return The roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles The new roles
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * Adds the role.
	 *
	 * @param role The role
	 */
	public void addRole(Role role) {
		this.roles.add(role);
	}

	/**
	 * To string.
	 *
	 * @return The user string
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", roles=" + roles + "]";
	}
	
	/**
	 * Gets the photos image path.
	 *
	 * @return The photos image path
	 */
	@Transient
	public String getPhotosImagePath() {
		if (id == null || photos == null) {
			return "/images/default-user.png";
		}
		return "/user-photos/" + this.id + "/" + this.photos;
	}
	
	/**
	 * Gets the full name. 
	 * @Transient for methods that do not relate to field mapping.
	 *
	 * @return The user's full name
	 */
	@Transient
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public boolean hasRole(String roleName) {
		Iterator<Role> iterator = roles.iterator();
		
		while (iterator.hasNext()) {
			Role role = iterator.next();
			if (role.getName().equals(roleName)) {
				return true;
			}
		}
		
		return false;
	}
}
