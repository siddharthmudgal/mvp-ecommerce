package com.dealstop.mvcEcommerce.repositories;

import com.dealstop.mvcEcommerce.domainobjects.CartItemDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemDO, String> {
}
