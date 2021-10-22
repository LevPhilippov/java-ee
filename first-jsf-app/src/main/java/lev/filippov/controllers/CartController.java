package lev.filippov.controllers;

import lev.filippov.models.CartItem;
import lev.filippov.models.dto.ProductDto;
import lev.filippov.service.CartService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CartController implements Serializable {

    @EJB
    CartService cartService;
    //for Rest Service
    public void addToCart(Long productId) {
        cartService.addToCart(productId);
    }

    public void addToCart(ProductDto product){
        cartService.addToCart(product);
    }

    public List<CartItem> findAll(){
        return cartService.getAll();
    }

    public void removeFromCart(CartItem cartItem) {
        cartService.removeFromCart(cartItem);
    }
}
