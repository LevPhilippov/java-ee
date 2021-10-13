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
@Transactional
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

    public void save(Category cat) {
        em.persist(em.merge(cat));
    }

    public Optional<Category> getProductById(Long id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }


    public void delete(Category cat) {
        em.createQuery("DELETE from Category c where p.id = :id").setParameter("id", cat.getId()).executeUpdate();
    }

    @Override
    public Long count() {
        return em.createQuery("SELECT count(*) from Category", Long.class).getSingleResult();
    }

    public Category getReference(Long id){
        return em.getReference(Category.class, id);
    }
}