package com.airtribe.meditrack;

import com.airtribe.meditrack.dto.AppointmentDTO;
import com.airtribe.meditrack.entities.*;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.notificationService.Observer;
import com.airtribe.meditrack.repositories.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeditrackApplicationTests{



	@Mock
	private DoctorRepo doctorRepo;

	@Mock
	private PatientRepo patientRepo;

	@Mock
	private AppointmentRepo appointmentRepo;

	@Mock
	private PaymentService paymentService;

	@Mock
	private PaymentRepo paymentRepo;

	@Mock
	private BillRep billRepo;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private Observer observer;

	@InjectMocks
	private AppointmentService appointmentService;

	@Test
	void testBookAppointment_success() {

		// ---------- Arrange ----------
		Doctor doctor = Doctor.builder()
				.id(1L)
				.name("Dr Strange")
				.consultationFee(500.0)
				.build();

		Patient patient = Patient.builder()
				.id(2L)
				.name("Tony")
				.build();

		Appointment inputAppointment = Appointment.builder()
				.startDate(LocalDate.now())
				.startTime(LocalTime.of(10, 0))
				.endTime(LocalTime.of(10, 30))
				.patientSymptoms("Headache")
				.build();

		Appointment savedAppointment = Appointment.builder()
				.id(100L)
				.doctor(doctor)
				.patient(patient)
				.status(AppointmentStatus.PAYMENT_PENDING)
				.build();

		AppointmentDTO dto = new AppointmentDTO();

		// observers list injection
		appointmentService = new AppointmentService(
				modelMapper,
				doctorRepo,
				patientRepo,
				appointmentRepo,
				paymentService,
				paymentRepo,
				List.of(observer),
				billRepo
		);

		// ---------- Mock behavior ----------
		when(doctorRepo.findById(1L)).thenReturn(Optional.of(doctor));
		when(patientRepo.findById(2L)).thenReturn(Optional.of(patient));
		when(appointmentRepo.findConflictsInAppointment(
				anyLong(), any(), any(), any()))
				.thenReturn(List.of());

		when(appointmentRepo.save(any(Appointment.class)))
				.thenReturn(savedAppointment);

		when(modelMapper.map(any(Appointment.class), eq(AppointmentDTO.class)))
				.thenReturn(dto);

		// ---------- Act ----------
		AppointmentDTO result =
				appointmentService.bookAppointment(1L, 2L, inputAppointment);

		// ---------- Assert ----------
		assertNotNull(result);

		verify(doctorRepo).findById(1L);
		verify(patientRepo).findById(2L);
		verify(appointmentRepo).save(any(Appointment.class));
		verify(observer).updateAppointment(any(Appointment.class));
	}
}
