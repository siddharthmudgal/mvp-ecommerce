package com.dealstop.mvcEcommerce.services;

import com.dealstop.mvcEcommerce.domainobjects.UserDO;
import com.dealstop.mvcEcommerce.enums.UserTypeEnum;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.dealstop.mvcEcommerce.repositories.CartItemRepository;
import com.dealstop.mvcEcommerce.repositories.CartRepository;
import com.dealstop.mvcEcommerce.repositories.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes =
        {
                CartService.class,
                CartRepository.class,
                ProductService.class,
                ProductRepository.class,
                CartItemRepository.class,
                UserService.class
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
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void findAll() {

        assertTrue(userService.findAll().size() > 0);

    }

    @Test
    void findByID() throws EntityNotFoundException {

        assertTrue(userService.findByID("e63784e2-daff-410c-a7ec-91d598365e9d") != null);

    }

    @Test
    void findByIDFail() {

        assertThrows(EntityNotFoundException.class, () -> userService.findByID("a"));

    }

    @Test
    void save() throws JsonProcessingException {

        int size = userService.findAll().size();

        UserDO userDO = new UserDO();
        userDO.setUsername("Hello");
        userDO.setUserType(UserTypeEnum.MERCHANT);

        userService.save(new ObjectMapper().writeValueAsString(userDO));

        assertTrue(size + 1 == userService.findAll().size());

    }

    @Test
    void loadCart() {

        assertAll(
                () -> assertTrue(userService.loadCart("e63784e2-daff-410c-a7ec-91d598365e9d") != null),
                () -> assertThrows(EntityNotFoundException.class , () ->
                        userService.loadCart("f35d02b6-12f3-4cac-963f-f704948b06e1")
                )
        );

    }

    @Test
    void addToCart() throws EntityNotFoundException {

        userService.addToCart("f35d02b6-12f3-4cac-963f-f704948b06e1", "4b6e2cd1-00b8-4ef4-8ccb-3fce12a6ba1a");

        assertAll(
                () -> assertTrue(
                        userService.findByID("f35d02b6-12f3-4cac-963f-f704948b06e1").getCart() != null
                ),
                () -> assertTrue(
                        userService.findByID("f35d02b6-12f3-4cac-963f-f704948b06e1").getCart().getCartItemDO().size() == 1
                ),
                () -> assertTrue(
                        userService.findByID("f35d02b6-12f3-4cac-963f-f704948b06e1")
                                .getCart().getCartItemDO().get(0).getQuantity() == 1
                )

        );

    }

    @Test
    void addToCart_existing_cart() throws EntityNotFoundException {

        userService.addToCart("e63784e2-daff-410c-a7ec-91d598365e9d", "3ffdb4d4-0fb4-4754-87b2-3466aa89ba49");

        assertAll(
                () -> assertTrue(
                        userService.findByID("e63784e2-daff-410c-a7ec-91d598365e9d").getCart() != null
                ),
                () -> assertTrue(
                        userService.findByID("e63784e2-daff-410c-a7ec-91d598365e9d").getCart().getCartItemDO().size() == 1
                ),
                () -> assertTrue(
                        userService.findByID("e63784e2-daff-410c-a7ec-91d598365e9d")
                                .getCart().getCartItemDO().get(0).getQuantity() == 3
                )

        );

    }

    @Test
    void removeFromCart() throws EntityNotFoundException {

        userService.removeFromCart("e63784e2-daff-410c-a7ec-91d598365e9d", "3ffdb4d4-0fb4-4754-87b2-3466aa89ba49");

        assertAll(
                () -> assertTrue(
                        userService.findByID("e63784e2-daff-410c-a7ec-91d598365e9d").getCart() != null
                ),
                () -> assertTrue(
                        userService.findByID("e63784e2-daff-410c-a7ec-91d598365e9d").getCart().getCartItemDO().size() == 1
                ),
                () -> assertTrue(
                        userService.findByID("e63784e2-daff-410c-a7ec-91d598365e9d")
                                .getCart().getCartItemDO().get(0).getQuantity() == 1
                )

        );

    }
}