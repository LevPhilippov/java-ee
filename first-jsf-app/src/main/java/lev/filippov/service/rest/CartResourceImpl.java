package lev.filippov.service.rest;

import lev.filippov.controllers.CartController;
import lev.filippov.models.CartItem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

//@Path("/v1/cart")
@Named
@ApplicationScoped
public class CartResourceImpl implements CartResource{

    @Inject
    CartController cartController;


    public List<CartItem> getAll() {
        return cartController.findAll();
    }

    @Override
    public List<CartItem> addToCart(Long id) {
        cartController.addToCart(id);
        return getAll();
    }
}
