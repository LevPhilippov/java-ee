package lev.filippov.controllers;

import lev.filippov.models.Category;
import lev.filippov.models.dto.CategoryDto;
import lev.filippov.service.CategoryServiceImpl;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CategoryController implements Serializable {

    @Inject
    CategoryServiceImpl categoryService;

    @Getter
    @Setter
    List<CategoryDto> categoryList;

    @Getter
    @Setter
    CategoryDto cat;

    public void preloadData () {
        this.categoryList = categoryService.getAll();
    }

    public List<CategoryDto> findAll() {
        return categoryService.getAll();
    }

    public String editCat(CategoryDto cat) {
        this.cat=cat;
        return "category_form.xhtml?faces-redirect=true";
    }

    public String deleteCat(CategoryDto cat) {
        categoryService.delete(cat.getId());
        return "category.xhtml?faces-redirect=true";
    }

    public Object newCategory() {
        this.cat = new CategoryDto();
        return "category_form.xhtml?faces-redirect=true";
    }

    public String saveCat() {
        categoryService.save(cat);
        return "category.xhtml?faces-redirect=true";
    }
}
