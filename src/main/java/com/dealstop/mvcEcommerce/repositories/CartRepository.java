package com.dealstop.mvcEcommerce.repositories;

import com.dealstop.mvcEcommerce.domainobjects.CartDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartDO, String> {
}
