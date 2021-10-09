package com.dealstop.mvcEcommerce.services;

import com.dealstop.mvcEcommerce.domainobjects.CartDO;
import com.dealstop.mvcEcommerce.domainobjects.CartItemDO;
import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.dealstop.mvcEcommerce.repositories.CartItemRepository;
import com.dealstop.mvcEcommerce.repositories.CartRepository;
import com.dealstop.mvcEcommerce.repositories.ProductRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes =
        {
                CartService.class,
                CartRepository.class,
                ProductService.class,
                ProductRepository.class,
                CartItemRepository.class,
        })
@EnableJpaRepositories(
        basePackages = {
                "com.dealstop.mvcEcommerce.repositories"
        }
)
@EntityScan(
        basePackages = {
                "com.dealstop.mvcEcommerce.domainobjects"
        }
)
@Sql({"/data-populate-cart-actions.sql"})
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Test
    void findAll() {

        assertTrue(
                cartService.findAll().size() != 0
        );

    }

    @Test
    void findByID() throws EntityNotFoundException {

        assertTrue(
                cartService.findByID("dd403b90-7305-4947-bdb7-5435598058e1") != null
        );
    }

    @Test
    void findByIDFail() {

        assertThrows(EntityNotFoundException.class,
                () -> cartService.findByID("a")
        );
    }

    @Test
    void save() {

        int len = cartService.findAll().size();

        CartDO cartDO = new CartDO();
        cartService.save(cartDO);

        assertTrue(len + 1 == cartService.findAll().size() );

    }

    @Test
    void addItemToCart() throws EntityNotFoundException {

        CartDO cartDO = cartService.findByID("dd403b90-7305-4947-bdb7-5435598058e1");

        // lemon
        cartService.addItemToCart(cartDO, "4b6e2cd1-00b8-4ef4-8ccb-3fce12a6ba1a");
        cartService.addItemToCart(cartDO, "4b6e2cd1-00b8-4ef4-8ccb-3fce12a6ba1a");
        cartService.addItemToCart(cartDO, "4b6e2cd1-00b8-4ef4-8ccb-3fce12a6ba1a");
        cartService.addItemToCart(cartDO, "4b6e2cd1-00b8-4ef4-8ccb-3fce12a6ba1a");

        // orange
        cartService.addItemToCart(cartDO, "f8ad154b-8058-4dbd-93ef-773ef4762ded");
        cartService.addItemToCart(cartDO, "f8ad154b-8058-4dbd-93ef-773ef4762ded");
        cartService.addItemToCart(cartDO, "f8ad154b-8058-4dbd-93ef-773ef4762ded");

        // apple
        cartService.addItemToCart(cartDO, "3ffdb4d4-0fb4-4754-87b2-3466aa89ba49");

        CartDO cartDO1 = cartService.findByID("dd403b90-7305-4947-bdb7-5435598058e1");

        List<CartItemDO> items = cartDO1.getCartItemDO();
        Map<String, Long> itemCounts = new HashMap<>();
        items.forEach(
                item -> {
                    itemCounts.put(item.getProductDO().getName(), item.getQuantity());
                }
        );
        assertAll(
                () -> assertEquals(items.size(), 3),
                () -> assertEquals(itemCounts.get("APPLE"), 3),
                () -> assertEquals(itemCounts.get("LEMON"), 4),
                () -> assertEquals(itemCounts.get("ORANGE"), 3)
        );


    }

    @Test
    void removeItemFromCart() throws EntityNotFoundException {

        CartDO cartDO = cartService.findByID("dd403b90-7305-4947-bdb7-5435598058e1");

        // lemon
        cartService.addItemToCart(cartDO, "4b6e2cd1-00b8-4ef4-8ccb-3fce12a6ba1a");
        cartService.removeItemFromCart(cartDO, "4b6e2cd1-00b8-4ef4-8ccb-3fce12a6ba1a");

        // apple
        cartService.removeItemFromCart(cartDO, "3ffdb4d4-0fb4-4754-87b2-3466aa89ba49");

        CartDO cartDO1 = cartService.findByID("dd403b90-7305-4947-bdb7-5435598058e1");

        List<CartItemDO> items = cartDO1.getCartItemDO();
        Map<String, Long> itemCounts = new HashMap<>();
        items.forEach(
                item -> {
                    itemCounts.put(item.getProductDO().getName(), item.getQuantity());
                }
        );

        assertAll(
                () -> assertEquals(items.size(), 1),
                () -> assertEquals(itemCounts.get("APPLE"), 1),
                () -> assertThrows(EntityNotFoundException.class, () ->
                        cartService.removeItemFromCart(cartDO, "4b6e2cd1-00b8-4ef4-8ccb-3fce12a6ba1a")
                )
        );

    }
}