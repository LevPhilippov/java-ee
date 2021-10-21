package lev.filippov.persistance;

import lev.filippov.models.Category;
import lev.filippov.persistance.interfaces.JPARepository;

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
    }

    public List<Category> getAll(){
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }
    @Transactional
    public void save(Category cat) {
        if(cat.getId()!=null)
            em.merge(cat);
        else
            em.persist(cat);
    }

    public Optional<Category> getProductById(Long id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    public void delete(Long id) {
        em.createQuery("DELETE from Category c where c.id=:id").setParameter("id", id).executeUpdate();
//        em.remove(em.merge(cat));
    }
    @Override
    public Long count() {
        return em.createQuery("SELECT count(*) from Category", Long.class).getSingleResult();
    }

    public Category getReference(Long id){
        return em.getReference(Category.class, id);
    }
}
