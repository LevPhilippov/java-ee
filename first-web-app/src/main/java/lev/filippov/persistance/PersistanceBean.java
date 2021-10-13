package lev.filippov.persistance;

import lev.filippov.models.Product;

import java.math.BigDecimal;
import java.util.*;

public class PersistanceBean {

    private Map<Long, Product> storage;
    private Long counter;
    private static PersistanceBean persistanceBean;

    private PersistanceBean() {
        persistanceBean= this;
        this.storage = new HashMap();
        this.counter = 0L;
        storage.put(getId(), new Product(1L, "Колбаса", new BigDecimal(300)));
        storage.put(getId(), new Product(2L, "Хлеб", new BigDecimal(200)));
        storage.put(getId(), new Product(3L, "Чай", new BigDecimal(500)));
        storage.put(getId(), new Product(4L, "Сахар", new BigDecimal(700)));
        storage.put(getId(), new Product(5L, "Бутер", new BigDecimal(1000)));
        storage.put(getId(), new Product(6L, "Конфетки", new BigDecimal(9999)));
    }

    public static PersistanceBean getInstance(){
        if(persistanceBean==null)
            return new PersistanceBean();
        else
            return persistanceBean;
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


}
