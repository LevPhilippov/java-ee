package lev.filippov.service;

import lev.filippov.models.Category;
import lev.filippov.models.Product;
import lev.filippov.models.dto.CategoryDto;
import lev.filippov.models.dto.ProductDto;
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

    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public Category getReference(Long id) {
        return categoryRepository.getReference(id);
    }

    @Override
    public List<CategoryDto> getCategoryById() {
        return categoryRepository.getAll()
                .stream().map(cat->new CategoryDto(cat.getId(),cat.getName(), getProductsByCategory(cat.getId()))).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.getProductById(id).orElseThrow(RuntimeException::new);
        return new CategoryDto(category.getId(), category.getName(), getProductsByCategory(id));
    }

    @Transactional
    @Override
    public void update(CategoryDto category) {
        if(category.getId() == null) {
            throw new RuntimeException("Id shouldn't be null!");
        }
        List<Product> productList = category.getProductDtoList().stream()
                .map(productDto -> {
                    return productRepository.getReference(productDto.getId());
                }).collect(Collectors.toList());
        categoryRepository.save(new Category(null, category.getName(), productList));
    }

    @Override
    @Transactional
    public void insert(CategoryDto category) {
        if(category.getId() != null) {
            throw new RuntimeException("Id should be null!");
        }
        //ассоцияция с продуктами не работает
        List<Product> productList = category.getProductDtoList().stream()
                .map(productDto -> {
                    return productRepository.getReference(productDto.getId());
                }).collect(Collectors.toList());
        categoryRepository.save(new Category(null, category.getName(), productList));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

    public List<ProductDto> getProductsByCategory(Long id) {
        List<Product> allbyCategory = productRepository.findAllbyCategory(id);
        return allbyCategory.stream().map(ProductServiceImpl::productToDto).collect(Collectors.toList());
    }



}
