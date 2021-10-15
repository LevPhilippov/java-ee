package lev.filippov.service;

import lev.filippov.models.Brand;
import lev.filippov.persistance.JPARepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class BrandService {

    @Inject
    JPARepository<Brand> brandRepository;

    public List<Brand> getAll(){
        return brandRepository.getAll();
    };

}
