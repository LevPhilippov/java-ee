package lev.filippov.controllers;

import lev.filippov.models.Product;
import lev.filippov.persistance.PersistanceBean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductController implements Serializable {

    @Inject
    PersistanceBean ps;

    Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> findAll() {
        return ps.getAll();
    }

    public String editProduct(Product product) {
        this.product = product;
        return "product_form.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) {
        ps.delete(product);
//        return "products.xhtml?faces-redirect=true";
    }

    public String newProduct() {
        this.product = new Product();
        return "product_form.xhtml?faces-redirect=true";
    }

    public String saveProduct() {
        ps.save(product);
        return "products.xhtml?faces-redirect=true";
    }
}
