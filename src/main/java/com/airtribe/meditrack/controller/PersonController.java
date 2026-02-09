package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.dto.DoctorDetailDTO;
import com.airtribe.meditrack.dto.PatientDetailDTO;
import com.airtribe.meditrack.entities.Doctor;
import com.airtribe.meditrack.entities.Patient;
import com.airtribe.meditrack.entities.Person;
import com.airtribe.meditrack.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PersonController {

        private final PersonRepository personRepository;

        @PostMapping("/register/doc")
        ResponseEntity<Person> createDoc(@RequestBody DoctorDetailDTO dto) {
            Doctor doctor = Doctor.builder()
                    .name(dto.getName())            // goes to doctor table
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .address(dto.getAddress())
                    .role(dto.getRole())
                    .specialist(dto.getSpecialist())
                    .isAvailable(false)
                    .build();
                Person person  = personRepository.save(doctor);
                return ResponseEntity.ok(person);
        }

    @PostMapping("/register/patient")
    ResponseEntity<Person> createPatient(@RequestBody PatientDetailDTO dto) {
        Patient patient = Patient.builder()
                .name(dto.getName())            // goes to doctor table
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .role(dto.getRole())
                .age(dto.getAge())
                .build();
        Person person  = personRepository.save(patient);
        return ResponseEntity.ok(person);
    }




}
