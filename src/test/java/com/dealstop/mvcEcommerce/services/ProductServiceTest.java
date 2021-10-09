package com.dealstop.mvcEcommerce.services;

import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes =
        {
                ProductService.class
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
@Sql({"/data-populate.sql"})
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void findAll() {

        assertTrue(
                    productService.findAll().size() != 0
        );
    }

    @Test
    void findByID() throws EntityNotFoundException {
        assertTrue(
                productService.findByID("0d99c629-2f30-40e1-aa96-bc3c716ad78f") !=
                        null
        );
    }

    @Test
    void findByIDFailed() {
        assertThrows(EntityNotFoundException.class,
                () -> productService.findByID("e")
        );
    }

    @Test
    void save() throws JsonProcessingException {

        int size = productService.findAll().size();

        ProductDO productDO = new ProductDO();
        productDO.setName("Hello");
        productDO.setQuantity(50l);
        String json = new ObjectMapper().writeValueAsString(productDO);
        productService.save(json);

        assertTrue(
                productService.findAll().size() > size
        );

    }

    @Test
    void findProductsCaseCheck() throws EntityNotFoundException {

        String nameCaps = "PLE";
        String nameLower = "ple";
        assertAll(
                () ->assertTrue(
                        productService.findProducts(nameCaps).size() ==
                                productService.findProducts(nameLower).size()
                )

        );

    }
}