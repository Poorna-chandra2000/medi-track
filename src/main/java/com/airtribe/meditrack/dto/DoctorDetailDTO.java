package com.airtribe.meditrack.dto;


import com.airtribe.meditrack.enums.Role;
import com.airtribe.meditrack.enums.Specialist;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDetailDTO {

    @Column(nullable = false, length = 100)
    private String name;


    @Column(nullable = false, unique = true, length = 100)
    @Email(message = "Email should be valid", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;


    @Column(length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(nullable = false)
    private Double consultationFee;

    @Enumerated(EnumType.STRING)
    private Specialist specialist;

}
