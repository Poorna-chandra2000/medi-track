package com.airtribe.meditrack.repositories;

import com.airtribe.meditrack.entities.Appointment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    @Query("""
            select a from Appointment a
            where a.startDate = :startDate
            and ((:startTime<a.endDate and :endTime >a.startTime))
            """)
    List<Appointment> findConflictsInAppointment(@NotNull LocalDate startDate, @NotNull LocalTime startTime, @NotNull LocalTime endTime);
}
