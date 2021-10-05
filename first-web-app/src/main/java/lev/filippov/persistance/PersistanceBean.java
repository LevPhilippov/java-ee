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
        storage.put(getId(), new Product(1L, "Prod1", new BigDecimal(300)));
        storage.put(getId(), new Product(2L, "Prod2", new BigDecimal(200)));
        storage.put(getId(), new Product(3L, "Prod3", new BigDecimal(500)));
        storage.put(getId(), new Product(4L, "Prod4", new BigDecimal(700)));
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

    public void save() {

    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }


}
