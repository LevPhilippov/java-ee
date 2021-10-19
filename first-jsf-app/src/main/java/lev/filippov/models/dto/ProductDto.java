package lev.filippov.models.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ProductDto implements Serializable {

    public ProductDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    private Long id;

    private String name;

    private BigDecimal price;

    private Long categoryId;

    private String categoryName;

    private Long brandId;

    private String brandName;

}
