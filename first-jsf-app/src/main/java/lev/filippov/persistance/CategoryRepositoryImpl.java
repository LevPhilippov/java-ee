package lev.filippov.persistance;

import lev.filippov.models.Category;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.*;

@Named("categoryRepository")
@ApplicationScoped
public class CategoryRepositoryImpl implements JPARepository<Category> {

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Inject
    UserTransaction ut;

//    private Map<Long, Category> storage;
//    private Long counter;

    public CategoryRepositoryImpl() {
    }

    @PostConstruct
    public void init(){
        if( count() == 0) {
            try {
                ut.begin();
                save(new Category(null,"Категория 1", null));
                save(new Category(null, "Категория 2", null));
                ut.commit();
            } catch (Exception e) {
                try {
                    ut.rollback();
                } catch (SystemException ex) {
                    ex.printStackTrace();
                }
            }
        }
//        this.storage = new HashMap();
//        this.counter = 0L;
    }

//    private Long getId() {
//        return ++counter;
//    }

    public List<Category> getAll(){
        return em.createQuery("SELECT c FROM Category c",Category.class).getResultList();
    }

    @Transactional
    public void save(Category cat) {
        em.persist(em.merge(cat));
    }

    public Optional<Category> getProductById(Long id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    @Transactional
    public void delete(Long id) {
        em.createQuery("DELETE from Category c where c.id=:id").setParameter("id", id).executeUpdate();
//        em.remove(em.merge(cat));
    }

    @Override
    public Long count() {
        return em.createQuery("SELECT count(*) from Category", Long.class).getSingleResult();
    }

    @Transactional
    public Category getReference(Long id){
        return em.getReference(Category.class, id);
    }
}
