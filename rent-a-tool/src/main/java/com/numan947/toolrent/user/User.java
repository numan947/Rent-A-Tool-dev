package com.numan947.toolrent.user;

import com.numan947.toolrent.history.ToolTransactionHistory;
import com.numan947.toolrent.role.Role;
import com.numan947.toolrent.tool.Tool;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name="_users")
//@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private LocalDateTime dob;

    @Column(unique = true)
    private String email;

    private String password;
    private boolean locked;
    private boolean enabled;

    @CreatedDate
    @Column(nullable = false, updatable = false) // when creating a new user, we always want to have createdDate, and we don't want to update it later
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false) // when creating a new user, we don't want to have updatedLastModifiedDate (it will be null)
    private LocalDateTime updatedLastModifiedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "owner")
    private List<Tool>tools;

    @OneToMany(mappedBy = "user")
    private List<ToolTransactionHistory>histories;

    @Override
    public String getName() {
        return email; //Comment: should return unique identifier for the user, in this case email
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(
                role-> new SimpleGrantedAuthority(role.getName())
        ).toList(); // Question: is collect(Collectors.toList()) better than toList()?
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; //Comment: should return unique identifier for the user, in this case email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //Comment: we are not using account expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //Comment: we are not using credentials expiration
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }
}
