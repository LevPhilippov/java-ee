package lev.filippov.models;

import lev.filippov.models.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CartItem {

    private ProductDto product;

    private Integer qty;

    private BigDecimal price;

    public CartItem(ProductDto product) {
        this.product = product;
        this.qty = 1;
        this.price = product.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return product.getId().equals(cartItem.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getId());
    }
}
