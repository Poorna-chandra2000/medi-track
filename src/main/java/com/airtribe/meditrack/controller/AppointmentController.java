package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.dto.AppointmentDTO;
import com.airtribe.meditrack.entities.Appointment;
import com.airtribe.meditrack.services.AppointmentService;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestParam Long docid,@RequestParam Long patid, @RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.bookAppointment(docid,patid,appointment));
    }

}
