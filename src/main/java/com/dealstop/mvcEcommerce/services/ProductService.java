package com.dealstop.mvcEcommerce.services;

import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.dealstop.mvcEcommerce.repositories.ProductRepository;
import com.dealstop.mvcEcommerce.utils.ProductObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final String TAG = " (ProductService) ";
    private final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDO> findAll() {
        String FUNCTION_TAG = TAG + " (findAll) ";
        LOG.debug(FUNCTION_TAG);
        return this.productRepository.findAll();
    }

    public ProductDO findByID(String id) throws EntityNotFoundException {
        String FUNCTION_TAG = TAG + " (findByID) ";
        LOG.debug(FUNCTION_TAG);
        Optional<ProductDO> optional = this.productRepository.findById(id);
        if (optional.isEmpty())
            throw new EntityNotFoundException("Product not found");

        return optional.get();
    }

    public ProductDO save(String json) throws JsonProcessingException {
        String FUNCTION_TAG = TAG + " (save) ";
        LOG.debug(FUNCTION_TAG);
        ProductDO productDO = ProductObjectMapper.verifyAndMap(json);
        return productRepository.save(productDO);
    }

    /**
     * Find products with various params
     * @param name
     * @return
     * @throws EntityNotFoundException
     */

    public List<ProductDO> findProducts(String name) throws EntityNotFoundException {
        String FUNCTION_TAG = TAG + " (findProducts) ";
        LOG.debug(FUNCTION_TAG);

        if (name == null || name.trim().length() == 0)
            return findAll();

        List<ProductDO> doList = productRepository.findByNameContainingIgnoreCase(name);

        if (doList.size() == 0)
            throw new EntityNotFoundException("No products found");

        return doList;
    }

}
