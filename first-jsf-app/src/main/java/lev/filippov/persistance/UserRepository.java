package lev.filippov.persistance;

import lev.filippov.models.User;
import lev.filippov.persistance.interfaces.JPARepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;
import java.util.Optional;

@Named("userRepository")
@ApplicationScoped
public class UserRepository implements JPARepository<User> {

    @PersistenceContext(unitName = "ds")
    EntityManager em;

    @Inject
    UserTransaction ut;

    @Override
    public List<User> getAll() {
        EntityGraph<?> eg = em.getEntityGraph("users-with-roles");
        return em.createQuery("from User", User.class).setHint("javax.persistence.loadgraph", eg).getResultList();
    }

    @Transactional
    @Override
    public void save(User user) {
        if(user.getId()==null) {
            em.persist(user);
        } else
            em.merge(user);
    }

    @Override
    public Optional<User> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public User getReference(Long id) {
        return null;
    }
}
