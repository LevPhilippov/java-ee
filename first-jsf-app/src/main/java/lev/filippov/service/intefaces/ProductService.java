package lev.filippov.service.intefaces;

import lev.filippov.models.Product;
import lev.filippov.models.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> getAll();

    List<ProductDto> getAll(Long catId);

    void save(ProductDto dto);

    ProductDto getProductById(Long id);

    void delete(Long id);

}
