package lev.filippov.persistance;

import lev.filippov.models.Product;

import java.util.List;

public interface ProductJPARepository extends JPARepository<Product> {

    List<Product> findAllbyCategory(Long categoryId);

}
