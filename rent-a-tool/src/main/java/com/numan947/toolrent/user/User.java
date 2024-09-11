package com.numan947.toolrent.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="_users")
@EntityListeners(AuditingEntityListener.class)
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




    @Override
    public String getName() {
        return email; //Comment: should return unique identifier for the user, in this case email
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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

    private String getFullName() {
        return firstname + " " + lastname;
    }
}
