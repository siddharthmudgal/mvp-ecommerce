package com.dealstop.mvcEcommerce.controllers;

import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.dealstop.mvcEcommerce.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller to expose products related REST
 */

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final String TAG = " (ProductsController) ";
    private final Logger LOG = LoggerFactory.getLogger(ProductsController.class);

    private ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {

        this.productService = productService;

    }

    /**
     * Get all products
     * @return
     */
    @GetMapping
    public List<ProductDO> getAllProducts() {
        String FUNCTION_TAG = TAG + " (getAllProducts) ";
        LOG.debug(FUNCTION_TAG);
        return productService.findAll();
    }

    /**
     * Get a single product details
     * @param product_id
     * @return
     */
    @GetMapping("/{product_id}")
    public ProductDO getProduct(@PathVariable String product_id) throws EntityNotFoundException {
        String FUNCTION_TAG = TAG + " (getAllProducts) ";
        LOG.debug(FUNCTION_TAG);
        return productService.findByID(product_id);
    }

    /**
     * Create product
     * @param productDO
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDO createProduct(@RequestBody String productDO) throws JsonProcessingException {
        String FUNCTION_TAG = TAG + " (getAllProducts) ";
        LOG.debug(FUNCTION_TAG);
        return productService.save(productDO);
    }

    @GetMapping("/search")
    public List<ProductDO> findProducts(@RequestParam String name) throws EntityNotFoundException {
        String FUNCTION_TAG = TAG + " (findProducts) ";
        LOG.debug(FUNCTION_TAG);
        return productService.findProducts(name);
    }
}
