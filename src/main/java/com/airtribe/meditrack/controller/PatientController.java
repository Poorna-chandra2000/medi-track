package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.dto.PatientDto;
import com.airtribe.meditrack.entities.Patient;
import com.airtribe.meditrack.repositories.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepo patientRepo;
    private final ModelMapper modelMapper;

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDto> getPatientsByID(@PathVariable Integer id) {


        Optional<Patient> patient = patientRepo.findById(id.longValue());

        if (patient.isPresent()) {
            return ResponseEntity.ok(modelMapper.map(patient.get(), PatientDto.class));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDto>> getAllPatient() {


        List<Patient> patient = patientRepo.findAll();

        if (!patient.isEmpty()) {
            return ResponseEntity.ok(patient.stream()
                    .map(p->modelMapper.map(p,PatientDto.class))
                    .collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();
    }

}
