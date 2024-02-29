package application.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@Accessors(chain = true)
@Document(collection = "roles")
public class Role implements GrantedAuthority {
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return "ROLE_" + roleName.name();
    }

    public enum RoleName {
        USER
    }
}
