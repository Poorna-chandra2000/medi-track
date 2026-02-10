package com.airtribe.meditrack.controller;
import com.airtribe.meditrack.dto.DoctorDto;
import com.airtribe.meditrack.entities.Doctor;
import com.airtribe.meditrack.repositories.DoctorRepo;
import com.airtribe.meditrack.services.DoctorService;
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
public class DoctorController {

    private final DoctorRepo doctorRepo;
    private final ModelMapper modelMapper;

    private final DoctorService doctorService;

    @GetMapping("/doctor/{id}")
    public ResponseEntity<DoctorDto> getDocByID(@PathVariable Integer id) {

        return ResponseEntity.ok(doctorService.getDocById(id));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDto>> getAllDoc() {

        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

}
