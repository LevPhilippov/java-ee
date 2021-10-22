package lev.filippov.models;

import lev.filippov.models.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@NamedEntityGraph(
        name = "users-with-roles",
        attributeNodes = @NamedAttributeNode("roles")
)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "login", nullable = false)
    String login;

    @Column(name = "password", nullable = false)
    String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;

    public User (UserDto dto) {
        this.id = dto.getId();
        this.login = dto.getLogin();
        this.password = dto.getPassword();
        this.roles = dto.getRoles().stream().map(Role::new).collect(Collectors.toSet());
    }

}
