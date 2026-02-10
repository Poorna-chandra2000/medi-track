package com.airtribe.meditrack.services;

import com.airtribe.meditrack.repositories.DoctorRepo;
import com.airtribe.meditrack.repositories.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepo patientRepo;
    private final ModelMapper modelMapper;
}
