package com.dealstop.mvcEcommerce.repositories;

import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Rest repository for products DO
 */

public interface ProductRepository extends JpaRepository<ProductDO, String> {

    public List<ProductDO> findByNameContainingIgnoreCase(String name);

}
