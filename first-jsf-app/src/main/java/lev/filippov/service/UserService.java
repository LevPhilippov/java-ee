package lev.filippov.service;

import lev.filippov.models.User;
import lev.filippov.models.dto.UserDto;
import lev.filippov.persistance.interfaces.JPARepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class UserService {

    @Inject
    @Named("userRepository")
    JPARepository<User> userJPARepository;

    public List<UserDto> getAll() {
        return userJPARepository.getAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void saveOrUpdate(UserDto user) {
        userJPARepository.save(new User(user));
    }



}
