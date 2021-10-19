package lev.filippov.persistance.interfaces;

import java.util.List;
import java.util.Optional;

public interface JPARepository<T> {

    List<T> getAll();

    void save(T t);

    Optional<T> getProductById(Long id);

    void delete(Long id);

    Long count();

    T getReference(Long id);
}