package com.airtribe.meditrack.repositories;

import com.airtribe.meditrack.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor,Long> {


    @Query(value = """
        SELECT d.*, p.address, p.created_at, p.email, p.phone, p.role, p.updated_at 
        FROM doctor d
        JOIN person p ON d.id = p.id
        ORDER BY d.embedding <-> CAST(:vector AS vector)
        LIMIT 1
    """, nativeQuery = true)
    List<Doctor> findTop5Similar(@Param("vector") String vector);


    List<Doctor> findBySpecialistContainingIgnoreCase(String query);

    //apointments per doctor,group by doctor id, count appointments, order by count desc
        @Query(value = """
            SELECT d.id, d.name, COUNT(a.id) AS appointment_count
            FROM doctor d
            LEFT JOIN appointment a ON d.id = a.doctor_id
            GROUP BY d.id
            ORDER BY appointment_count DESC
        """, nativeQuery = true)
        List<Object[]> findDoctorsWithAppointmentCounts();
}
