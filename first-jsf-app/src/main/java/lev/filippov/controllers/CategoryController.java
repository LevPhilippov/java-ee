package lev.filippov.controllers;

import lev.filippov.models.Category;
import lev.filippov.persistance.CategoryRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CategoryController implements Serializable {

    @Inject
    CategoryRepository cr;

    Category cat;

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }


    public List<Category> findAll() {
        return cr.getAll();
    }

    public String editCat(Category cat) {
        this.cat=cat;
        return "category_form.xhtml?faces-redirect=true";
    }

    public String deleteCat(Category cat) {
        cr.delete(cat);
        return "category.xhtml?faces-redirect=true";
    }

    public Object newCategory() {
        this.cat = new Category();
        return "category_form.xhtml?faces-redirect=true";
    }

    public String saveCat() {
        cr.save(cat);
        return "category.xhtml?faces-redirect=true";
    }
}
