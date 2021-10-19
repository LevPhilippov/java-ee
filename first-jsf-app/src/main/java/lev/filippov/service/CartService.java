package lev.filippov.service;

import lev.filippov.models.CartItem;
import lev.filippov.models.Product;
import lev.filippov.models.dto.ProductDto;
import lev.filippov.service.intefaces.ProductService;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateful
public class CartService implements Serializable{

    @EJB
    ProductService productService;

    public Map<CartItem, Integer> cart = new HashMap<>();

    public void addToCart(ProductDto product) {
        CartItem item = new CartItem(product);
        cart.put(item, cart.getOrDefault(item,0) + item.getQty());
//        if(cart.containsKey(item))
//            cart.put(item, cart.get(item)+1);
//        else
//            cart.put(item, 1);
    }

    public void removeFromCart(ProductDto product){
        CartItem item = new CartItem(product);
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


    public void addToCart(Long id) {
        addToCart(productService.getProductById(id));
    }
}
