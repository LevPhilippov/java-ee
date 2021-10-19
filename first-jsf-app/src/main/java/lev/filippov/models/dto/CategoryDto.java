package lev.filippov.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto implements Serializable {
    Long id;
    String name;
    List<ProductDto> productDtoList;
}
