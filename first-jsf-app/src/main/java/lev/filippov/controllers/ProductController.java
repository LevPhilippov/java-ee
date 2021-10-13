package lev.filippov.controllers;

import lev.filippov.models.Brand;
import lev.filippov.models.Product;
import lev.filippov.models.dto.ProductDto;
import lev.filippov.service.BrandService;
import lev.filippov.service.ProductService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class ProductController implements Serializable {

    @Inject
    BrandService brandService;

    @Inject
    ProductService productService;

    @Setter
    @Getter
    List<ProductDto> productList;

    @Setter
    @Getter
    List<Brand> brandList;

    @Getter
    @Setter
    ProductDto product;

    @Inject
    HttpServletRequest request;

    @Getter
    @Setter
    Long categoryId;

    public void preloadData(ComponentSystemEvent componentSystemEvent){
        this.brandList = brandService.getAll();
        if (request.getParameter("categoryId") != null)
            this.categoryId = Long.parseLong(request.getParameter("categoryId"));
        this.productList = findAll();
    }


    public List<ProductDto> findAll() {
        if(request.getParameter("categoryId") != null)
            this.productList = productService.getAll(categoryId);
        else
        {
            this.categoryId = null;
            this.productList = productService.getAll();
        }
        return productList;
    }

    public String editProduct(ProductDto product) {
        this.product = product;
        return "product_form.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) {
        productService.delete(product);
//        return "products.xhtml?faces-redirect=true";
    }

    public String newProduct() {
        this.product = new ProductDto();
        return "product_form.xhtml?faces-redirect=true";
    }

    public String saveProduct() {
        productService.save(product);
        return "products.xhtml?faces-redirect=true";
    }

}
