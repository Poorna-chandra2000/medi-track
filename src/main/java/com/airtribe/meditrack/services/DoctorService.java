package com.airtribe.meditrack.services;

import com.airtribe.meditrack.dto.DoctorDto;
import com.airtribe.meditrack.entities.Doctor;
import com.airtribe.meditrack.repositories.DoctorRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepo doctorRepo;
    private final ModelMapper modelMapper;

    public DoctorDto getDocById(Integer id) {
        Optional<Doctor> doctor = doctorRepo.findById(id.longValue());

        if (doctor.isPresent()) {
            return modelMapper.map(doctor.get(), DoctorDto.class);
        }
        return null;

    }

    public List<DoctorDto> getAllDoctors() {

        List<Doctor> doctors = doctorRepo.findAll();

        if (doctors.isEmpty()) {
           return new ArrayList<>();
        }
        return doctors.stream()
                .map(d -> modelMapper.map(d, DoctorDto.class))
                .collect(Collectors.toList());
    }
}
