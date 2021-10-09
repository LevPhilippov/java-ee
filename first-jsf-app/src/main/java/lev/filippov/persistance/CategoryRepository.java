package lev.filippov.persistance;

import lev.filippov.models.Category;
import lev.filippov.models.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.*;

@Named
@ApplicationScoped
public class CategoryRepository {

    private Map<Long, Category> storage;
    private Long counter;

    public CategoryRepository() {
    }

    @PostConstruct
    public void init(){
        this.storage = new HashMap();
        this.counter = 0L;
        storage.put(getId(), new Category(counter,"Категория 1"));
        storage.put(getId(), new Category(counter,"Категория 2"));
        storage.put(getId(), new Category(counter,"Категория 3"));
        storage.put(getId(), new Category(counter,"Категория 4"));
    }

    private Long getId() {
        return ++counter;
    }

    public List<Category> getAll(){
        return new ArrayList<Category>(storage.values());
    }

    public void save(Category cat) {
        if(cat.getId()==null){
            cat.setId(getId());
        }
        storage.put(cat.getId(), cat);
    }

    public Optional<Category> getProductById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }


    public void delete(Category cat) {
        storage.remove(cat.getId());
    }
}
