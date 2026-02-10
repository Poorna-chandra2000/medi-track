package com.airtribe.meditrack.services;

import com.airtribe.meditrack.dto.AppointmentDTO;
import com.airtribe.meditrack.entities.Appointment;
import com.airtribe.meditrack.entities.Doctor;
import com.airtribe.meditrack.entities.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.repositories.AppointmentRepo;
import com.airtribe.meditrack.repositories.DoctorRepo;
import com.airtribe.meditrack.repositories.PatientRepo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final ModelMapper modelMapper;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final AppointmentRepo appointmentRepo;


    public AppointmentDTO bookAppointment(Long docid, Long patid, Appointment appointment) {
        Optional<Doctor> doctor = doctorRepo.findById(docid);
        Optional<Patient> patient = patientRepo.findById(patid);
        Appointment appointment1;
        if (doctor.isPresent() && isAvailableSlot(appointment.getStartDate(), appointment.getStartTime(), appointment.getEndTime())) {
            appointment1 = Appointment.builder()
                    .doctor(doctor.get())//when you use optional use get() to retrieve the value
                    .patient(patient.get())
                    .startDate(appointment.getStartDate())
                    .startTime(appointment.getStartTime())
                    .endTime(appointment.getEndTime())
                    .status(appointment.getStatus())
                    .build();

            appointmentRepo.save(appointment1);
        } else {
            throw new RuntimeException("Doctor not found or time slot is not available");
        }
        return null;
    }

    private boolean isAvailableSlot(@NotNull LocalDate startDate, @NotNull LocalTime startTime, @NotNull LocalTime endTime) {

        return appointmentRepo.findConflictsInAppointment(startDate,startTime,endTime).isEmpty();
    }

    //send same doc id and pat id and appointment details to confirm the appointment
    String confirmAppointment(Long AppointmentId,Double amount) {
        Optional<Appointment> appointment = appointmentRepo.findById(AppointmentId);
        if (amount <= 0) {
            return "Invalid payment amount. Please enter a positive value.";
        }

        Appointment appointment1 = appointment.get();
        appointment1=Appointment.builder()
                .PaymentAmount(appointment1.getDoctor().getConsultationFee())
                .status(AppointmentStatus.SCHEDULED).build();

        appointmentRepo.save(appointment1);
        return "Appointment confirmed and payment processed successfully.";
    }

    //Cancel the appointment by sending the appointment id and reason for cancellation
    String cancelAppointment(Long AppointmentId,String reason) {
        Optional<Appointment> appointment = appointmentRepo.findById(AppointmentId);
        if (appointment.isPresent()) {
            Appointment appointment1 = appointment.get();
            appointment1.setStatus(AppointmentStatus.CANCELLED);
            appointment1.setCancellationReason(reason);
            appointmentRepo.save(appointment1);
        }
        return "Appointment cancelled successfully.";
    }

}
