package com.numan947.toolrent.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numan947.toolrent.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="_roles")
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    @CreatedDate
    @Column(nullable = false, updatable = false) // when creating a new user, we always want to have createdDate, and we don't want to update it later
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false) // when creating a new user, we don't want to have updatedLastModifiedDate (it will be null)
    private LocalDateTime updatedLastModifiedDate;

}
