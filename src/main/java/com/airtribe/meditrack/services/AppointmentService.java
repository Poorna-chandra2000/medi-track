package com.airtribe.meditrack.services;

import com.airtribe.meditrack.dto.AppointmentDTO;
import com.airtribe.meditrack.dto.DocObservationDto;
import com.airtribe.meditrack.dto.PaymentDto;
import com.airtribe.meditrack.entities.Appointment;
import com.airtribe.meditrack.entities.Doctor;
import com.airtribe.meditrack.entities.Patient;
import com.airtribe.meditrack.entities.Payment;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.repositories.AppointmentRepo;
import com.airtribe.meditrack.repositories.DoctorRepo;
import com.airtribe.meditrack.repositories.PatientRepo;
import com.airtribe.meditrack.repositories.PaymentRepo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final ModelMapper modelMapper;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final AppointmentRepo appointmentRepo;
    private final PaymentService paymentService;
    private final PaymentRepo paymentRepo;


    public AppointmentDTO bookAppointment(Long docid, Long patid, Appointment appointment) {
        Optional<Doctor> doctor = doctorRepo.findById(docid);
        Optional<Patient> patient = patientRepo.findById(patid);
        Appointment appoint;
        if (doctor.isPresent() && isAvailableSlot(docid, appointment.getStartDate(), appointment.getStartTime(), appointment.getEndTime())) {
            appoint = Appointment.builder()
                    .doctor(doctor.get())//when you use optional use get() to retrieve the value
                    .patient(patient.get())
                    .startDate(appointment.getStartDate())
                    .startTime(appointment.getStartTime())
                    .endTime(appointment.getEndTime())
                    .status(AppointmentStatus.PAYMENT_PENDING)
                    .patientSymptoms(appointment.getPatientSymptoms())
                    .paymentAmount(doctor.get().getConsultationFee())
                    .build();

            appointmentRepo.save(appoint);
        } else {
            throw new RuntimeException("Doctor not found or time slot is not available");
        }
        return modelMapper.map(appoint, AppointmentDTO.class);
    }

    private boolean isAvailableSlot(Long doctorId, @NotNull LocalDate startDate, @NotNull LocalTime startTime, @NotNull LocalTime endTime) {

        return appointmentRepo.findConflictsInAppointment(doctorId, startDate, startTime, endTime).isEmpty();
    }

    public String confirmAppointment(Long appointid, PaymentDto paymentDto) {
        Optional<Appointment> appointment = appointmentRepo.findById(appointid);

        if (appointment.isEmpty()) {
            return "Appointment not found. Please check the appointment ID.";
        }

        if (appointment.get().getDoctor().getConsultationFee() > paymentDto.getPaymentAmount()) {
            return "Pls check the amount you have entered ,Doesnt match the Consultation fee. Please check the appointment ID.";
        }

        Boolean paid = paymentService.processPayment(paymentDto.getPaymentType(), paymentDto.getPaymentAmount());
        if (!paid) {
            return "Payment failed. Please check the payment details and try again.";
        }

        Appointment appointment1 = appointment.get();

        appointment1.setPaymentAmount(
                appointment1.getDoctor().getConsultationFee()
        );
        appointment1.setStatus(AppointmentStatus.SCHEDULED);

        Payment payment = Payment.builder()
                .amount(paymentDto.getPaymentAmount())
                .paymentType(paymentDto.getPaymentType())
                .appointment(appointment1)
                .build();

        paymentRepo.save(payment);

        appointmentRepo.save(appointment1);
        return "Appointment confirmed and payment processed successfully.";
    }

    public String doctorConsultationCompletion(Long AppointmentId, DocObservationDto docObservationDto) {
        Optional<Appointment> appointment = appointmentRepo.findById(AppointmentId);
        if (appointment.isPresent()) {
            Appointment appointment1 = appointment.get();
            appointment1.setStatus(AppointmentStatus.COMPLETED);
            appointment1.setDocObservations(docObservationDto.getDocObservations());
            appointmentRepo.save(appointment1);
        }
        return "Doctor consultation completed successfully.";
    }

    //Cancel the appointment by sending the appointment id and reason for cancellation
    public String cancelAppointment(Long AppointmentId, String reason) {
        Optional<Appointment> appointment = appointmentRepo.findById(AppointmentId);
        if (appointment.isPresent()) {
            Appointment appointment1 = appointment.get();
            appointment1.setStatus(AppointmentStatus.CANCELLED);
            appointment1.setCancellationReason(reason);
            appointmentRepo.save(appointment1);
        }
        return "Appointment cancelled successfully.";
    }

    public AppointmentDTO getAppointmentById(Long id) {
        Optional<Appointment> appointment = appointmentRepo.findById(id);
        if (appointment.isPresent()) {
            return modelMapper.map(appointment.get(), AppointmentDTO.class);
        } else {
            throw new RuntimeException("Appointment not found");
        }
    }

}
