package com.dealstop.mvcEcommerce.services;

import com.dealstop.mvcEcommerce.domainobjects.CartDO;
import com.dealstop.mvcEcommerce.domainobjects.CartItemDO;
import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import com.dealstop.mvcEcommerce.domainobjects.UserDO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.dealstop.mvcEcommerce.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final String TAG = " (UserService) ";
    private final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private UserRepository userRepository;
    private CartService cartService;

    @Autowired
    public UserService(UserRepository userRepository,
                       CartService cartService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
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

    /**
     * create user and cart
     *
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    public UserDO save(String json) throws JsonProcessingException {
        String FUNCTION_TAG = TAG + " (save) ";
        LOG.debug(FUNCTION_TAG);

        ObjectMapper objectMapper = new ObjectMapper();
        UserDO userDO = objectMapper.readValue(json, UserDO.class);
        return userRepository.save(userDO);
    }

    /**
     * Load cart
     *
     * @param user_id
     * @return
     * @throws EntityNotFoundException
     */
    public List<CartItemDO> loadCart(String user_id) throws EntityNotFoundException {

        UserDO userDO = findByID(user_id);

        if (userDO.getCart() == null)
            throw new EntityNotFoundException("Cart is empty");

        List<CartItemDO> cartItems = userDO.getCart().getCartItemDO();

        if (cartItems == null || cartItems.size() == 0)
            throw new EntityNotFoundException("Cart is empty");

        return cartItems;
    }

    /**
     * Adds a new item to the cart of a user
     *
     * @param user_id
     * @param product_id
     * @return
     * @throws EntityNotFoundException
     */
    @Transactional
    public void addToCart(String user_id, String product_id) throws EntityNotFoundException {
        UserDO userDO = findByID(user_id);

        CartDO cartDO = userDO.getCart();

        // check and create if cart is null
        if (cartDO == null) {

            cartDO = new CartDO();
            cartDO.setUser(userDO);

            userDO.setCart(cartDO);
            userRepository.save(userDO);

        }

        cartService.addItemToCart(userDO.getCart(), product_id);
    }


    /**
     * remove a product from the users cart
     * @param user_id
     * @param product_id
     * @throws EntityNotFoundException
     */
    @Transactional
    public void removeFromCart(String user_id, String product_id) throws EntityNotFoundException {
        UserDO userDO = findByID(user_id);

        CartDO cartDO = userDO.getCart();

        // check and create if cart is null
        if (cartDO == null)
            throw new EntityNotFoundException("User cart is empty!");

        cartService.removeItemFromCart(userDO.getCart(), product_id);
    }
}

