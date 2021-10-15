package lev.filippov.service;

import lev.filippov.models.CartItem;
import lev.filippov.models.dto.ProductDto;

import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateful
public class CartService {

    public Map<CartItem, Integer> cart = new HashMap<>();

    public void addToCart(ProductDto product) {
        CartItem item = new CartItem(product, null, product.getPrice());
        if(cart.containsKey(item))
            cart.put(item, cart.get(item)+1);
        else
            cart.put(item, 1);
    }

    public void removeFromCart(ProductDto product){
        CartItem item = new CartItem(product, null, product.getPrice());
        if(cart.containsKey(item)) {
            if(cart.get(item) == 1)
                cart.remove(item);
            else
                cart.put(item, cart.get(item)-1);
        }
    }

    public void removeFromCart(CartItem item){
        if(cart.containsKey(item)) {
            if(cart.get(item) == 1)
                cart.remove(item);
            else
                cart.put(item, cart.get(item)-1);
        }
    }

    public List<CartItem> getAll(){
        cart.forEach(CartItem::setQty);
        return new ArrayList<CartItem>(cart.keySet());
    }

}
