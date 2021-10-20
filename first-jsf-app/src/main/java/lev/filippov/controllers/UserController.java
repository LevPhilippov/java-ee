package lev.filippov.controllers;

import lev.filippov.models.Role;
import lev.filippov.models.User;
import lev.filippov.models.dto.RoleDto;
import lev.filippov.models.dto.UserDto;
import lev.filippov.persistance.interfaces.JPARepository;
import lev.filippov.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SessionScoped
@Named
public class UserController implements Serializable {
    @Getter
    @Setter
    UserDto user;

    @Getter
    @Setter
    Set<RoleDto>roles;

    @Inject
    @Named("roleRepository")
    JPARepository<Role> roleJPARepository;

    @Inject
    UserService userService;

    @Inject
    HttpSession session;

    public List<RoleDto> getAllRoles() {
        return roleJPARepository.getAll()
                .stream().map(RoleDto::new)
                .collect(Collectors.toList());
    }

    public List<UserDto> getAll() {
        return userService.getAll();
    }

    public String addNew() {
        this.user = new UserDto();
        return "/admin/user-form.xhtml?faces-redirect=true";
    }

    public String save(){
                userService.saveOrUpdate(user);
                return "/admin/users.xhtml?faces-redirect=true";
    }

    public void editUser(User user) {
    }

    public String logout() {
        session.invalidate();
        return "/products.xhtml?faces-redirect=true";
    }
}
