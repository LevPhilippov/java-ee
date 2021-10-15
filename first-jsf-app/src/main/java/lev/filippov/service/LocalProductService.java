package lev.filippov.service;

import lev.filippov.models.Product;
import lev.filippov.models.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface LocalProductService {

    List<ProductDto> getAll();

    List<ProductDto> getAll(Long catId);

    void save(ProductDto dto);

    Optional<Product> getProductById(Long id);

     void delete(Product product);

}
