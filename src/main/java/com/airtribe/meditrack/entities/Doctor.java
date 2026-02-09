package com.airtribe.meditrack.entities;

import com.airtribe.meditrack.enums.Specialist;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)//wheenver you create a new doctor object,it will also take details from person class and compare them as well when you compare two doctor objects, it will also compare the details from person class like name,email,phone,address,createdAt,updatedAt
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Doctor extends Person{

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @NotNull
    private String name;

    @Column(nullable = false)
    @Builder.Default //when you create a new doctor object, if you don't provide a value for isAvailable, it will default to true
    private Boolean isAvailable = true;

    @Enumerated(EnumType.STRING)
    private Specialist specialist;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Appointment> appointments;

}
