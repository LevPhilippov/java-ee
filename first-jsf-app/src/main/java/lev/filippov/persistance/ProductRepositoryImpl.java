package lev.filippov.persistance;

import lev.filippov.models.Product;
import lev.filippov.models.dto.ProductDto;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.BeforeDestroyed;
import javax.inject.Named;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

//@Named("productRepository")
//@ApplicationScoped
@Stateless
public class ProductRepositoryImpl implements ProductJPARepository, Serializable {

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

//    private Map<Long, Product> storage;
//    private Long counter;

    public ProductRepositoryImpl() {
    }

//    @PostConstruct
//    public void init(){
////        this.storage = new HashMap();
////        this.counter = 0L;
//        save(new Product(counter, "Колбаса", new BigDecimal(300)));
//        save(new Product(counter, "Хлеб", new BigDecimal(200)));
//        save(new Product(counter, "Чай", new BigDecimal(500)));
//        save(new Product(counter, "Сахар", new BigDecimal(700)));
//        save(new Product(counter, "Бутер", new BigDecimal(1000)));
//        save(new Product(counter, "Конфетки", new BigDecimal(9999)));
//    }

//    private Long getId() {
//        return ++counter;
//    }
//    @Transactional

    public List<Product> getAll(){
        EntityGraph<?> eg = em.getEntityGraph("products-with-categories-and-brands");
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .setHint("javax.persistence.loadgraph",eg).getResultList();

        //return em.createQuery("from Product p left join fetch p.category", Product.class)
    }
    @TransactionAttribute
    public void save(Product product) {
//        if(product.getId()==null){
//            product.setId(getId());
//        }

        em.persist(em.merge(product));
//        em.persist(product);
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(em.find(Product.class,id));
    }

//    @Transactional
    @TransactionAttribute
    public void delete(Long id) {
        em.createQuery("DELETE from Product p where p.id = :id").setParameter("id", id).executeUpdate();
//        em.remove(product);
    }

//    @Transactional
//    @BeforeDestroyed(ApplicationScoped.class)
//    public void destroy() {
//        em.createQuery("DELETE from Product p").executeUpdate();
//    }

    @Override
    public Long count() {
        return em.createQuery("SELECT count(*) from Product", Long.class).getSingleResult();
    }

//    @Transactional
    @TransactionAttribute
    public List<Product> findAllbyCategory(Long categoryId) {
        EntityGraph<?> eg = em.getEntityGraph("products-with-categories-and-brands");
        return em.createQuery("SELECT p FROM Product p WHERE p.category.id = :id", Product.class)
                .setHint("javax.persistence.loadgraph",eg)
                .setParameter("id",categoryId).getResultList();
    };

}
