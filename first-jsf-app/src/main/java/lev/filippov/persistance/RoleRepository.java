package lev.filippov.persistance;

import lev.filippov.models.Role;
import lev.filippov.persistance.interfaces.JPARepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Named("roleRepository")
@ApplicationScoped
public class RoleRepository implements JPARepository<Role> {

    @PersistenceContext(unitName = "ds")
    EntityManager em;



    @Override
    public List<Role> getAll() {
        return em.createQuery("from Role", Role.class).getResultList();
    }

    @Transactional
    @Override
    public void save(Role role) {
        if(role.getId() == null) {
            em.persist(role);
        } else {
            em.merge(role);
        }
    }

    @Override
    public Optional<Role> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Long count() {
        return null;
    }

    @Transactional
    @Override
    public Role getReference(Long id) {
        return null;
    }

    @Transactional
    public Set<Role> getReferences(Set<Long> ids){
        Set<Role> roles = new HashSet<>();
        for (Long id : ids) {
            roles.add(em.getReference(Role.class, id));
        }
        return roles;
    }
}
