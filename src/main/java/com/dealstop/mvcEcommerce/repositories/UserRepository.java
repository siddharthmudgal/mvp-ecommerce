package com.dealstop.mvcEcommerce.repositories;

import com.dealstop.mvcEcommerce.domainobjects.UserDO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Rest repository for UserDO
 */

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<UserDO, String> {
}
