package lev.filippov.service;

import lev.filippov.models.Category;
import lev.filippov.models.Product;
import lev.filippov.models.dto.CategoryDto;
import lev.filippov.persistance.interfaces.JPARepository;
import lev.filippov.persistance.interfaces.ProductJPARepository;
import lev.filippov.service.rest.CategoryRestService;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Named
public class CategoryServiceImpl implements CategoryRestService {

    @Inject
    @Named("categoryRepository")
    JPARepository<Category> categoryRepository;

    @EJB
    ProductJPARepository productRepository;

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.getAll().stream().map(this::convertCatToDto).collect(Collectors.toList());
    }

    @Transactional
    public void save(CategoryDto category) {
        categoryRepository.save(new Category(category.getId(), category.getName(), null));
    }

    @Transactional
    public Category getReference(Long id) {
        return categoryRepository.getReference(id);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return convertCatToDto(categoryRepository.getProductById(id).orElseThrow(RuntimeException::new));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

//    public List<ProductDto> getProductsByCategory(Long id) {
//        List<Product> allbyCategory = productRepository.findAllbyCategory(id);
//        return allbyCategory.stream().map(ProductServiceImpl::productToDto).collect(Collectors.toList());
//    }


    private CategoryDto convertCatToDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(),
                category.getProducts().stream().map(ProductServiceImpl::productToDto).collect(Collectors.toList()));
    }

    ///REST METHODS
    @Transactional
    @Override
    public void update(CategoryDto category) {
        if(category.getId() == null) {
            throw new RuntimeException("Id shouldn't be null!");
        }
//        List<Product> productList = category.getProductDtoList().stream()
//                .map(productDto -> productRepository.getReference(productDto.getId())).collect(Collectors.toList());
        categoryRepository.save(new Category(null, category.getName(), null));
    }

    @Override
    @Transactional
    public void insert(CategoryDto category) {
        if(category.getId() != null) {
            throw new RuntimeException("Id should be null!");
        }
        //ассоцияция с продуктами не работает
//        List<Product> productList = category.getProductDtoList().stream()
//                .map(productDto -> {
//                    return productRepository.getReference(productDto.getId());
//                }).collect(Collectors.toList());
        categoryRepository.save(new Category(null, category.getName(), null));
    }


}
