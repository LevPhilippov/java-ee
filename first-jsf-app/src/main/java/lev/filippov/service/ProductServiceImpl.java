package lev.filippov.service;

import lev.filippov.ProductRemoteDto;
import lev.filippov.RemoteProductService;
import lev.filippov.models.Brand;
import lev.filippov.models.Category;
import lev.filippov.models.Product;
import lev.filippov.models.dto.ProductDto;
import lev.filippov.persistance.*;
import lev.filippov.persistance.interfaces.JPARepository;
import lev.filippov.persistance.interfaces.ProductJPARepository;
import lev.filippov.service.rest.ProductRestService;
import lev.filippov.service.intefaces.ProductService;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Remote(value = RemoteProductService.class)
@Local(value = {ProductService.class, ProductRestService.class})
public class ProductServiceImpl implements RemoteProductService, ProductService, ProductRestService {

    @EJB
    ProductJPARepository productRepository;

    @Inject
    @Named("categoryRepository")
    JPARepository<Category> categoryRepository;

    @Inject
    @Named("brandRepository")
    JPARepository<Brand> brandRepository;


    @PostConstruct
    public void init() {
        if (productRepository.count() < 1) {
            save(new ProductDto(null, "Колбаса", new BigDecimal(300), null, null, null, null));
            save(new ProductDto(null, "Хлеб", new BigDecimal(200), null, null, null, null));
            save(new ProductDto(null, "Чай", new BigDecimal(500), null, null, null, null));
            save(new ProductDto(null, "Сахар", new BigDecimal(700), null, null, null, null));
            save(new ProductDto(null, "Бутер", new BigDecimal(1000), null, null, null, null));
            save(new ProductDto(null, "Конфетки", new BigDecimal(9999), null, null, null, null));
        }
    }

    public List<ProductDto> getAll() {
        return productRepository.getAll().stream().map(ProductServiceImpl::productToDto).collect(Collectors.toList());
    }


    public List<ProductDto> getAllProductsByCategoryId(Long catId) {
        return productRepository.findAllbyCategory(catId).stream().map(ProductServiceImpl::productToDto).collect(Collectors.toList());
    }


    public void save(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        if (dto.getCategoryId() != null)
            product.setCategory(((CategoryRepositoryImpl) (categoryRepository)).getReference(dto.getCategoryId()));
        if (dto.getBrandId() != null)
            product.setBrand(((BrandRepositoryImpl) brandRepository).getReference(dto.getBrandId()));
        productRepository.save(product);
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.getProductById(id)
                .orElseThrow(RuntimeException::new);
        return productToDto(product);
    }

    ;

    public void delete(Long id) {
        productRepository.delete(id);
    }

    protected static ProductDto productToDto(Product product) {
        return ProductDto.builder().id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                .brandName(product.getBrand() != null ? product.getBrand().getName() : null)
                .build();
    }


    @Override
    public List<ProductRemoteDto> getAllRemote() {
        List<ProductDto> dtos = getAll();
        return dtos.stream().map((p) -> new ProductRemoteDto(p.getId(), p.getName(),
                p.getPrice(), p.getCategoryId(), p.getCategoryName(), p.getBrandId(), p.getBrandName())).collect(Collectors.toList());
    }

    /* REST METHODS */

    @Override
    public void insert(ProductDto dto) {
        if (dto.getId() != null) {
            throw new RuntimeException("Id of new product should be null!");
        }
        save(dto);
    }

    @Override
    public void update(ProductDto dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("Id of new product shouldn't be null!");
        }
        save(dto);
    }



}