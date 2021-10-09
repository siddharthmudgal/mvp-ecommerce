package com.dealstop.mvcEcommerce.controllers;

import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import com.dealstop.mvcEcommerce.domainobjects.UserDO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.dealstop.mvcEcommerce.services.ProductService;
import com.dealstop.mvcEcommerce.services.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final String TAG = " (UserController) ";
    private final Logger LOG = LoggerFactory.getLogger(ProductsController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;

    }

    /**
     * Get all products
     * @return
     */
    @GetMapping
    public List<UserDO> getAllUsers() {
        String FUNCTION_TAG = TAG + " (getAllUsers) ";
        LOG.debug(FUNCTION_TAG);
        return userService.findAll();
    }

    /**
     * Get a single product details
     * @param user_id
     * @return
     */
    @GetMapping("/{user_id}")
    public UserDO getUser(@PathVariable String user_id) throws EntityNotFoundException {
        String FUNCTION_TAG = TAG + " (getUser) ";
        LOG.debug(FUNCTION_TAG);
        return userService.findByID(user_id);
    }

    /**
     * Create user
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDO createUser(@RequestBody String json) throws JsonProcessingException {
        String FUNCTION_TAG = TAG + " (createUser) ";
        LOG.debug(FUNCTION_TAG);
        return userService.save(json);
    }

}
