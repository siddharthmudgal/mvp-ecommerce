package com.dealstop.mvcEcommerce.repositories;

import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Rest repository for products DO
 */

public interface ProductRepository extends JpaRepository<ProductDO, String> {
}
