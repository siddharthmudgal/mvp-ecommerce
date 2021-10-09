package com.dealstop.mvcEcommerce.services;

import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import com.dealstop.mvcEcommerce.domainobjects.UserDO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.dealstop.mvcEcommerce.repositories.ProductRepository;
import com.dealstop.mvcEcommerce.repositories.UserRepository;
import com.dealstop.mvcEcommerce.utils.ProductObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final String TAG = " (UserService) ";
    private final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDO> findAll() {
        String FUNCTION_TAG = TAG + " (findAll) ";
        LOG.debug(FUNCTION_TAG);
        return this.userRepository.findAll();
    }

    public UserDO findByID(String id) throws EntityNotFoundException {
        String FUNCTION_TAG = TAG + " (findByID) ";
        LOG.debug(FUNCTION_TAG);
        Optional<UserDO> optional = this.userRepository.findById(id);
        if (optional.isEmpty())
            throw new EntityNotFoundException("User not found");

        return optional.get();
    }

    public UserDO save(String json) throws JsonProcessingException {
        String FUNCTION_TAG = TAG + " (save) ";
        LOG.debug(FUNCTION_TAG);
        ObjectMapper objectMapper = new ObjectMapper();
        UserDO userDO = objectMapper.readValue(json, UserDO.class);
        return userRepository.save(userDO);
    }

}
