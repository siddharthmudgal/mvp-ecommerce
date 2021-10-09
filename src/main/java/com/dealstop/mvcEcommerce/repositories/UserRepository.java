package com.dealstop.mvcEcommerce.repositories;

import com.dealstop.mvcEcommerce.domainobjects.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Rest repository for UserDO
 */

public interface UserRepository extends JpaRepository<UserDO, String> {
}
