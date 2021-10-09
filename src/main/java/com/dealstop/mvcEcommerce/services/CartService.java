package com.dealstop.mvcEcommerce.services;

import com.dealstop.mvcEcommerce.domainobjects.CartDO;
import com.dealstop.mvcEcommerce.domainobjects.CartItemDO;
import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import com.dealstop.mvcEcommerce.exceptions.EntityNotFoundException;
import com.dealstop.mvcEcommerce.repositories.CartItemRepository;
import com.dealstop.mvcEcommerce.repositories.CartRepository;
import com.dealstop.mvcEcommerce.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService {

    private final String TAG = " (ProductService) ";
    private final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private CartRepository cartRepository;
    private ProductService productService;
    private CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository,
                       ProductService productService,
                       CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * find all carts
     * @return
     */
    public List<CartDO> findAll() {
        String FUNCTION_TAG = TAG + " (findAll) ";
        LOG.debug(FUNCTION_TAG);
        return this.cartRepository.findAll();
    }

    /**
     * find a cart by ID
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    public CartDO findByID(String id) throws EntityNotFoundException {
        String FUNCTION_TAG = TAG + " (findByID) ";
        LOG.debug(FUNCTION_TAG);
        Optional<CartDO> optional = this.cartRepository.findById(id);
        if (optional.isEmpty())
            throw new EntityNotFoundException("Cart not found");

        return optional.get();
    }

    /**
     * Save a cart
     * @param cartDO
     * @return
     */
    public CartDO save(CartDO cartDO) {
        String FUNCTION_TAG = TAG + " (save) ";
        LOG.debug(FUNCTION_TAG);
        return cartRepository.save(cartDO);
    }

    /**
     * Add an item to a cart
     * @param cartDO
     * @param product_id
     * @return
     */

    public void addItemToCart(CartDO cartDO, String product_id) throws EntityNotFoundException {

        if (cartDO == null)
            throw new EntityNotFoundException("Cart not found");

        ProductDO productDO = productService.findByID(product_id);

        List<CartItemDO> cartItemsList = cartDO.getCartItemDO();

        // if this is the very first item in the cart ever
        if (cartItemsList == null) {
            cartItemsList = new ArrayList<>();
        }

        // update quantity of product DO if present
        Optional<CartItemDO> optional = cartItemsList
                .stream()
                .filter(cartItemDO ->
                    cartItemDO.getProductDO().getUuid() == productDO.getUuid()
                )
                .peek(cartItemDO -> cartItemDO.setQuantity(
                        cartItemDO.getQuantity() + 1
                ))
                .findFirst();

        // if the product is not already present in the cart
        if (optional.isEmpty()) {

            CartItemDO cartItem = new CartItemDO();
            cartItem.setCart_id(cartDO);
            cartItem.setProductDO(productDO);
            cartItem.setQuantity(1l);
            cartItemRepository.save(cartItem);

            cartItemsList.add(cartItem);
            cartDO.setCartItemDO(cartItemsList);
            cartRepository.save(cartDO);
        }

        return;
    }


    /**
     * Remove an item from the cart / decrements it counter
     * @param cartDO
     * @param product_id
     * @throws EntityNotFoundException
     */

    public void removeItemFromCart(CartDO cartDO, String product_id) throws EntityNotFoundException {

        if (cartDO == null)
            throw new EntityNotFoundException("Cart not found");

        ProductDO productDO = productService.findByID(product_id);

        List<CartItemDO> cartItemsList = cartDO.getCartItemDO();

        // if this is the very first item in the cart ever
        if (cartItemsList == null || cartItemsList.isEmpty()) {
            throw new EntityNotFoundException("User cart is empty");
        }

        // find item if present and decrement

        Optional<CartItemDO> optional = cartItemsList
                .stream()
                .filter(cartItemDO ->
                        cartItemDO.getProductDO().getUuid() == productDO.getUuid()
                )
                .peek(cartItem -> {
                        cartItem.setQuantity(
                                cartItem.getQuantity() - 1
                        );
                })
                .findFirst();

        if (optional.isEmpty())
            throw new EntityNotFoundException("Item not found in cart");

        CartItemDO cartItemDO = optional.get();

        if (cartItemDO.getQuantity() == 0) {
            cartItemsList.remove(cartItemDO);
            cartItemRepository.delete(cartItemDO);
        }

        return;
    }

}
