package lev.filippov.persistance;

import lev.filippov.models.Product;
import lev.filippov.persistance.interfaces.ProductJPARepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.*;

@Stateless
public class ProductRepositoryImpl implements ProductJPARepository, Serializable {

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    public ProductRepositoryImpl() {
    }

    public List<Product> getAll(){
        EntityGraph<?> eg = em.getEntityGraph("products-with-categories-and-brands");
        return em.createQuery("SELECT p FROM Product p", Product.class)
                .setHint("javax.persistence.loadgraph",eg).getResultList();

        //return em.createQuery("from Product p left join fetch p.category", Product.class)
    }
    @TransactionAttribute
    public void save(Product product) {
        if(product.getId()==null){
//            product.setId(getId());
            em.persist(product);
        }
//        em.persist(em.merge(product));
        em.merge(product);
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(em.find(Product.class,id));
    }

    @TransactionAttribute
    public void delete(Long id) {
        em.createQuery("DELETE from Product p where p.id = :id").setParameter("id", id).executeUpdate();
//        em.remove(product);
    }

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

    @TransactionAttribute
    @Override
    public Product getReference(Long id) {
        return em.getReference(Product.class,id);
    }
}
