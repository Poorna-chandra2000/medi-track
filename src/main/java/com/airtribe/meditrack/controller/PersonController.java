package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.dto.DoctorDetailDTO;
import com.airtribe.meditrack.dto.PatientDetailDTO;
import com.airtribe.meditrack.entities.Doctor;
import com.airtribe.meditrack.entities.Patient;
import com.airtribe.meditrack.entities.Person;
import com.airtribe.meditrack.repositories.PersonRepository;
import com.airtribe.meditrack.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping

public class PersonController {

    private final PersonRepository personRepository;

    private final EmbeddingModel embeddingModel;

    // Manual constructor to resolve the ambiguity
    public PersonController(
            PersonRepository personRepository,
            @Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel) {
        this.personRepository = personRepository;
        this.embeddingModel = embeddingModel;
    }

        @PostMapping("/register/doc")
        ResponseEntity<Person> createDoc(@RequestBody DoctorDetailDTO dto) {
            Doctor doctor = Doctor.builder()
                    .name(dto.getName())            // goes to doctor table
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .address(dto.getAddress())
                    .role(dto.getRole())
                    .consultationFee(dto.getConsultationFee())
                    .specialist(dto.getSpecialist())
                    .description(dto.getDescription())
                    .isAvailable(true)
                    .build();

            // Generate embedding - added String.valueOf to prevent NullPointer if specialist is missing
            String textToEmbed = String.valueOf(doctor.getSpecialist()) + " " + doctor.getDescription();
            float[] vector = embeddingModel.embed(textToEmbed);

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
