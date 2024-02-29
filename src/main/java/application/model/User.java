package application.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Accessors(chain = true)
@Document(collection = "users")
public class User implements UserDetails {
    @Indexed(unique = true)
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private boolean isAvailable = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAvailable;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAvailable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAvailable;
    }

    @Override
    public boolean isEnabled() {
        return isAvailable;
    }
}
