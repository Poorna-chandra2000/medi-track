package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.dto.AppointmentDTO;
import com.airtribe.meditrack.dto.DocObservationDto;
import com.airtribe.meditrack.entities.Appointment;
import com.airtribe.meditrack.services.AppointmentService;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestParam Long docid,@RequestParam Long patid, @RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.bookAppointment(docid,patid,appointment));
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/appointments/{id}/confirm")
    public ResponseEntity<String> confirmAppointment(@PathVariable Long id, @RequestParam Double amount) {
        return ResponseEntity.ok(appointmentService.confirmAppointment(id, amount));
    }

    @GetMapping("/appointments/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id, @RequestParam String reason) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id,reason));
    }

    @PostMapping("/appointments/doc/{id}")
    ResponseEntity<String> doctorConsultationCompletion(@PathVariable Long id, @RequestBody DocObservationDto docObservationDto) {
        return ResponseEntity.ok(appointmentService.doctorConsultationCompletion(id,docObservationDto));
    }

}
