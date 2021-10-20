package lev.filippov.models.dto;

import lev.filippov.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    Long id;
    String name;

    public RoleDto(Role r) {
        this.id = r.getId();
        this.name = r.getName();
    }
}
