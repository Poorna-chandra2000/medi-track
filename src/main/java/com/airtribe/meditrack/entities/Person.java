package com.airtribe.meditrack.entities;


import com.airtribe.meditrack.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
//@MappedSuperclass //use only hwne using abstract class, otherwise it will create a table for this class and the subclasses will have their own tables with a foreign key to this table, which is not what we want in this case since we want to treat this purely for code reusability, not as an entity like USER for login and authentication
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Person {  //treat this purely for code reusability, not as an entity like USER for login and authentication

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @Email(message = "Email should be valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;


    @Column(length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
