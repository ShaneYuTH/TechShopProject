package com.techshop.common.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Role Entity Class.
 * The class is an entity and is mapped to a database table.
 */
@Entity
@Table(name = "roles")
public class Role {
	
	/** The Role id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/** The Role name. */
	@Column(length = 40, nullable = false, unique = true)
	private String name;
	
	/** The Role description. */
	@Column(length = 150, nullable = false)
	private String description;
	
	/**
	 * Default Role Constructor. Instantiates a new role.
	 */
	public Role() {
	}
	
	/**
	 * Instantiates a new role with id.
	 *
	 * @param id The Role id
	 */
	public Role(Integer id) {
		this.id = id;
	}
	
	/**
	 * Instantiates a new role with name.
	 *
	 * @param name The Role name
	 */
	public Role(String name) {
		this.name = name;
	}
	
	/**
	 * Instantiates a new role with name and description.
	 *
	 * @param name The Role name
	 * @param description The Role description
	 */
	public Role(String name, String description) {
		this.name = name;
		this.description = description;
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
	 * Gets the name.
	 *
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name The new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description The new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Hash code.
	 *
	 * @return The hash code of id;
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/**
	 * See if two role equals.
	 *
	 * @param obj The potential role object
	 * @return true, if two roles are the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * Role to string.
	 *
	 * @return The role name string
	 */
	@Override
	public String toString() {
		return this.name;
	}

	
	
}
