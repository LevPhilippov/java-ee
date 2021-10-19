package lev.filippov.controllers;

import lev.filippov.models.Category;
import lev.filippov.service.CategoryServiceImpl;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class CategoryController implements Serializable {

    @Inject
    CategoryServiceImpl categoryService;

    @Getter
    @Setter
    List<Category> categoryList;

    public void preloadData () {
        this.categoryList = categoryService.getAll();
    }
    @Getter
    @Setter
    Category cat;

    public List<Category> findAll() {
        return categoryService.getAll();
    }

    public String editCat(Category cat) {
        this.cat=cat;
        return "category_form.xhtml?faces-redirect=true";
    }

    public String deleteCat(Category cat) {
        categoryService.delete(cat.getId());
        return "category.xhtml?faces-redirect=true";
    }

    public Object newCategory() {
        this.cat = new Category();
        return "category_form.xhtml?faces-redirect=true";
    }

    public String saveCat() {
        categoryService.save(cat);
        return "category.xhtml?faces-redirect=true";
    }
}
