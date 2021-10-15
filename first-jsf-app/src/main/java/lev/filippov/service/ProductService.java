package lev.filippov.service;

import lev.filippov.ProductRemoteDto;
import lev.filippov.RemoteProductService;
import lev.filippov.models.Brand;
import lev.filippov.models.Category;
import lev.filippov.models.Product;
import lev.filippov.models.dto.ProductDto;
import lev.filippov.persistance.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Named
//@ApplicationScoped
@Stateless
@Local(value = LocalProductService.class)
@Remote(value = RemoteProductService.class)
public class ProductService implements LocalProductService, RemoteProductService {

//    @Inject
//    @Named("productRepository")
    @EJB(name = "productRepository")
    ProductJPARepository productRepository;

    @Inject
    @Named("categoryRepository")
    JPARepository<Category> categoryRepository;

    @Inject
    @Named("brandRepository")
    JPARepository<Brand> brandRepository;


    @PostConstruct
    public void init(){
        if(productRepository.count() == 0) {
            save(new ProductDto(null,"Колбаса", new BigDecimal(300),null, null,null, null));
            save(new ProductDto(null, "Хлеб", new BigDecimal(200),null, null,null, null));
            save(new ProductDto(null, "Чай", new BigDecimal(500),null, null,null, null));
            save(new ProductDto(null, "Сахар", new BigDecimal(700),null, null,null, null));
            save(new ProductDto(null, "Бутер", new BigDecimal(1000), null, null,null, null));
            save(new ProductDto(null, "Конфетки", new BigDecimal(9999),null, null,null, null));
        }
    }


    public List<ProductDto> getAll() {
        return productRepository.getAll().stream().map(ProductService::productToDto).collect(Collectors.toList());
    };

    public List<ProductDto> getAll(Long catId) {
        return productRepository.findAllbyCategory(catId).stream().map(ProductService::productToDto).collect(Collectors.toList());
    };


    public void save(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCategory(((CategoryRepositoryImpl)(categoryRepository)).getReference(dto.getCategoryId()));
        product.setBrand(((BrandRepositoryImpl)brandRepository).getReference(dto.getBrandId()));
        productRepository.save(product);
    };

    public Optional<Product> getProductById(Long id) {
        return null;
    };

    public  void delete(Product product) {
    productRepository.delete(product);
   };

    private static ProductDto productToDto(Product product) {
        return ProductDto.builder().id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryId(product.getCategory()!=null? product.getCategory().getId() : null)
                .categoryName(product.getCategory()!=null? product.getCategory().getName() : null)
                .brandId(product.getBrand() != null ? product.getBrand().getId(): null)
                .brandName(product.getBrand() != null ? product.getBrand().getName(): null)
                .build();
    }


    @Override
    public List<ProductRemoteDto> getAllRemote() {
        return productRepository.getAll().stream()
                .map(product -> new ProductRemoteDto(product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getBrand().getId(),
                product.getBrand().getName()))
                .collect(Collectors.toList());
    }
}
