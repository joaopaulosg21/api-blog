package projeto.api.blog.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto.api.blog.model.Role;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserDTO {
    private long id;

    private String name;

    private String email;

    private String birthDate;

    private Role role;

    public String getRole() {
        return role.getAuthority();
    }
}
 