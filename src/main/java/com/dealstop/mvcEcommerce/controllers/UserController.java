package com.dealstop.mvcEcommerce.controllers;

import com.dealstop.mvcEcommerce.domainobjects.CartDO;
import com.dealstop.mvcEcommerce.domainobjects.CartItemDO;
import com.dealstop.mvcEcommerce.domainobjects.UserDO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.dealstop.mvcEcommerce.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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

    /**
     * Load user cart items, creates a new cart if not present
     * @param user_id
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/{user_id}/cart")
    public ResponseEntity<List<CartItemDO>> loadCart(@PathVariable String user_id) throws EntityNotFoundException {
        String FUNCTION_TAG = TAG + " (loadCart) ";
        LOG.debug(FUNCTION_TAG);

        return new ResponseEntity<>(
                userService.loadCart(user_id),
                HttpStatus.OK
        );
    }

    /**
     * Add to cart actions
     * @param user_id
     * @param product_id
     * @return
     * @throws EntityNotFoundException
     */

    @PostMapping("/{user_id}/addtocart/{product_id}")
    public ResponseEntity<CartDO> addToCart(
            @PathVariable String user_id,
            @PathVariable String product_id
    ) throws EntityNotFoundException {

        String FUNCTION_TAG = TAG + " (addToCart) ";
        LOG.debug(FUNCTION_TAG);
        userService.addToCart(user_id, product_id);

        return new ResponseEntity<>(
                HttpStatus.CREATED
        );
    }

    /**
     * Post request to remove an item from the cart
     * @param user_id
     * @param product_id
     * @return
     * @throws EntityNotFoundException
     */

    @PostMapping("/{user_id}/removefromcart/{product_id}")
    public ResponseEntity<CartDO> removeFromCart(
            @PathVariable String user_id,
            @PathVariable String product_id
    ) throws EntityNotFoundException {

        String FUNCTION_TAG = TAG + " (addToCart) ";
        LOG.debug(FUNCTION_TAG);
        userService.removeFromCart(user_id, product_id);

        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

}
