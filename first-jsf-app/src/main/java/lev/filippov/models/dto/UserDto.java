package lev.filippov.models.dto;

import lev.filippov.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    Long id;
    String login;
    String password;
    Set<RoleDto> roles;

    public UserDto(User user) {
        this.id= user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.roles = user.getRoles().stream().map(RoleDto::new).collect(Collectors.toSet());
    }
}
