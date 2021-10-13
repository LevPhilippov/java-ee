package lev.filippov.persistance;

import lev.filippov.models.Brand;
import lev.filippov.models.Category;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Optional;

@Named("brandRepository")
@ApplicationScoped
@Transactional
public class BrandRepositoryImpl implements JPARepository<Brand>{

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Inject
    UserTransaction ut;

    @PostConstruct
    public void init(){
        if( count() == 0) {
            try {
                ut.begin();
                save(new Brand(null,"Lacoste", null));
                save(new Brand(null, "Olololo LTD", null));
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
    public List<Brand> getAll(){
        return em.createQuery("SELECT b FROM Brand b",Brand.class).getResultList();
    }


    public void save(Brand brand) {
        em.persist(em.merge(brand));
    }

    public Optional<Brand> getProductById(Long id) {
        return Optional.ofNullable(em.find(Brand.class, id));
    }


    public void delete(Brand brand) {
        em.createQuery("DELETE from Brand b where b.id = :id").setParameter("id", brand.getId()).executeUpdate();
    }

    @Override
    public Long count() {
        return em.createQuery("SELECT count(*) from Brand", Long.class).getSingleResult();
    }

    public Brand getReference(Long id){
        return em.getReference(Brand.class, id);
    }
}
