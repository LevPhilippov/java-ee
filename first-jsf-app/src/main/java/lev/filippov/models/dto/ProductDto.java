package lev.filippov.models.dto;

import lev.filippov.models.Category;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ProductDto {

    private Long id;

    private String name;

    private BigDecimal price;

    private Long categoryId;

    private String categoryName;

    private Long brandId;

    private String brandName;

}
