package lev.filippov.persistance;

import lev.filippov.models.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Named
@ApplicationScoped
public class PersistanceBean implements Serializable {

    private Map<Long, Product> storage;
    private Long counter;

    public PersistanceBean() {
    }

    @PostConstruct
    public void init(){
        this.storage = new HashMap();
        this.counter = 0L;
        storage.put(getId(), new Product(counter, "Колбаса", new BigDecimal(300)));
        storage.put(getId(), new Product(counter, "Хлеб", new BigDecimal(200)));
        storage.put(getId(), new Product(counter, "Чай", new BigDecimal(500)));
        storage.put(getId(), new Product(counter, "Сахар", new BigDecimal(700)));
        storage.put(getId(), new Product(counter, "Бутер", new BigDecimal(1000)));
        storage.put(getId(), new Product(counter, "Конфетки", new BigDecimal(9999)));
    }

    private Long getId() {
        return ++counter;
    }

    public List<Product> getAll(){
        return new ArrayList<>(storage.values());
    }

    public void save(Product product) {
        if(product.getId()==null){
            product.setId(getId());
        }
        storage.put(product.getId(), product);
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }


    public void delete(Product product) {
        storage.remove(product.getId());
    }
}
