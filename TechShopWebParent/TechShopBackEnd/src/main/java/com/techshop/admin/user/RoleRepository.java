package com.techshop.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techshop.common.entity.Role;

/**
 * The Interface RoleRepository, extending CrudRepository.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
