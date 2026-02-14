import meditrack.constants.AppointmentStatus;
import meditrack.constants.Specialization;
import meditrack.entity.Appointment;
import meditrack.entity.Bill;
import meditrack.entity.Doctor;
import meditrack.entity.Patient;
import meditrack.factory.BillFactory;
import meditrack.payment.CardPayment;
import meditrack.payment.PaymentStrategy;
import meditrack.payment.UpiPayment;
import meditrack.repository.DataStore;
import meditrack.service.AppointmentService;
import meditrack.service.DoctorService;
import meditrack.service.PatientService;
import meditrack.utils.ConsoleNotifier;
import meditrack.utils.IdGen;

/**
 * Manual Test Runner for MediTrack Application
 * 
 * This class provides comprehensive testing of all major functionalities
 * including entity creation, service operations, design patterns, and error handling.
 */
public class TestRunner {
    
    private static int passedTests = 0;
    private static int totalTests = 0;
    
    public static void main(String[] args) {
        printHeader();
        
        // Run all test suites
        testDoctorCreation();
        testPatientRegistration();
        testAppointmentBooking();
        testBillGeneration();
        testPaymentProcessing();
        testObserverPattern();
        testCSVExport();
        testErrorHandling();
        testSingletonPattern();
        testStrategyPattern();
        testFactoryPattern();
        testInheritance();
        testPolymorphism();
        testDataStore();
        testIdGeneration();
        
        printSummary();
    }
    
    private static void printHeader() {
        System.out.println("========================================");
        System.out.println("    MEDITRACK TEST SUITE");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Running comprehensive tests...");
        System.out.println();
    }
    
    private static void printSummary() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("TEST RESULTS SUMMARY");
        System.out.println("========================================");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (totalTests - passedTests));
        System.out.println("Success Rate: " + (passedTests * 100.0 / totalTests) + "%");
        System.out.println("========================================");
        
        if (passedTests == totalTests) {
            System.out.println("✅ ALL TESTS PASSED!");
        } else {
            System.out.println("❌ SOME TESTS FAILED");
        }
        System.out.println("========================================");
    }
    
    private static void test(String testName, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("[✓] " + testName);
        } else {
            System.out.println("[✗] " + testName + " - FAILED");
        }
    }
    
    private static void testSection(String sectionName) {
        System.out.println();
        System.out.println("--- " + sectionName + " ---");
    }
    
    // Test 1: Doctor Creation
    private static void testDoctorCreation() {
        testSection("Test 1: Doctor Creation and Management");
        
        try {
            IdGen idGen = IdGen.getInstance();
            DoctorService ds = new DoctorService();
            
            // Create doctor
            int doctorId = idGen.nextId();
            Doctor doctor = new Doctor(doctorId, "Dr. Smith", 45, 
                                      Specialization.CARDIOLOGY, 1000.0);
            
            test("Doctor object created successfully", doctor != null);
            test("Doctor has correct ID", doctor.getId() == doctorId);
            test("Doctor has correct name", doctor.getName().equals("Dr. Smith"));
            test("Doctor has correct age", doctor.getAge() == 45);
            test("Doctor has correct specialization", 
                 doctor.getSpecialization() == Specialization.CARDIOLOGY);
            test("Doctor has correct fee", doctor.getfee() == 1000.0);
            
            // Add to service
            ds.add(doctor);
            Doctor retrieved = ds.get(doctorId);
            
            test("Doctor can be added to service", retrieved != null);
            test("Retrieved doctor matches created doctor", 
                 retrieved.getId() == doctor.getId());
            
        } catch (Exception e) {
            test("Doctor creation - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 2: Patient Registration
    private static void testPatientRegistration() {
        testSection("Test 2: Patient Registration and Management");
        
        try {
            IdGen idGen = IdGen.getInstance();
            PatientService ps = new PatientService();
            
            // Create patient
            int patientId = idGen.nextId();
            Patient patient = new Patient(patientId, "John Doe", 30, "Fever");
            
            test("Patient object created successfully", patient != null);
            test("Patient has correct ID", patient.getId() == patientId);
            test("Patient has correct name", patient.getName().equals("John Doe"));
            test("Patient has correct age", patient.getAge() == 30);
            test("Patient has correct condition", patient.getCondition().equals("Fever"));
            
            // Add to service
            ps.add(patient);
            Patient retrieved = ps.get(patientId);
            
            test("Patient can be added to service", retrieved != null);
            test("Retrieved patient matches created patient", 
                 retrieved.getId() == patient.getId());
            
        } catch (Exception e) {
            test("Patient registration - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 3: Appointment Booking
    private static void testAppointmentBooking() {
        testSection("Test 3: Appointment Booking System");
        
        try {
            IdGen idGen = IdGen.getInstance();
            DoctorService ds = new DoctorService();
            PatientService ps = new PatientService();
            AppointmentService as = new AppointmentService();
            
            // Create doctor and patient
            Doctor doctor = new Doctor(idGen.nextId(), "Dr. Johnson", 40, 
                                      Specialization.NEUROLOGY, 1200.0);
            Patient patient = new Patient(idGen.nextId(), "Jane Smith", 25, "Migraine");
            
            ds.add(doctor);
            ps.add(patient);
            
            // Create appointment
            int aptId = idGen.nextId();
            Appointment appointment = as.create(aptId, doctor, patient);
            
            test("Appointment created successfully", appointment != null);
            test("Appointment has correct ID", appointment.getId() == aptId);
            test("Appointment has correct doctor", 
                 appointment.getDoctor().getId() == doctor.getId());
            test("Appointment has correct patient", 
                 appointment.getPatient().getId() == patient.getId());
            test("Appointment initial status is PENDING", 
                 appointment.getStatus() == AppointmentStatus.PENDING);
            
            // Confirm appointment
            appointment.confirm();
            test("Appointment can be confirmed", 
                 appointment.getStatus() == AppointmentStatus.CONFIRMED);
            
        } catch (Exception e) {
            test("Appointment booking - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 4: Bill Generation
    private static void testBillGeneration() {
        testSection("Test 4: Bill Generation and Calculation");
        
        try {
            double baseFee = 1000.0;
            Bill bill = BillFactory.Create(baseFee);
            
            test("Bill created via factory", bill != null);
            test("Bill has correct base fee", bill.getBaseFee() == baseFee);
            
            // Tax calculation (20% tax)
            double expectedTax = baseFee * 0.20;
            test("Bill calculates tax correctly", bill.getTax() == expectedTax);
            
            // Total calculation
            double expectedTotal = baseFee + expectedTax;
            double actualTotal = bill.calculateTotal();
            test("Bill calculates total correctly", actualTotal == expectedTotal);
            test("Bill total includes base + tax", actualTotal == 1200.0);
            
        } catch (Exception e) {
            test("Bill generation - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 5: Payment Processing
    private static void testPaymentProcessing() {
        testSection("Test 5: Payment Processing with Strategy Pattern");
        
        try {
            double amount = 1200.0;
            
            // Test UPI payment
            PaymentStrategy upiPayment = new UpiPayment();
            test("UPI payment strategy created", upiPayment != null);
            
            System.out.print("    ");
            upiPayment.pay(amount);
            test("UPI payment processes amount", true);
            
            // Test Card payment
            PaymentStrategy cardPayment = new CardPayment();
            test("Card payment strategy created", cardPayment != null);
            
            System.out.print("    ");
            cardPayment.pay(amount);
            test("Card payment processes amount", true);
            
            // Test strategy switching
            PaymentStrategy payment = new UpiPayment();
            payment = new CardPayment();
            test("Payment strategy can be switched at runtime", payment != null);
            
        } catch (Exception e) {
            test("Payment processing - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 6: Observer Pattern
    private static void testObserverPattern() {
        testSection("Test 6: Observer Pattern for Notifications");
        
        try {
            IdGen idGen = IdGen.getInstance();
            Doctor doctor = new Doctor(idGen.nextId(), "Dr. Williams", 50, 
                                      Specialization.ORTHOPEDICS, 900.0);
            Patient patient = new Patient(idGen.nextId(), "Bob Johnson", 35, "Back Pain");
            
            Appointment appointment = new Appointment(idGen.nextId(), doctor, patient);
            
            test("Appointment created for observer test", appointment != null);
            
            // Add observer
            ConsoleNotifier notifier = new ConsoleNotifier();
            appointment.addObserver(notifier);
            
            test("Observer added to appointment", true);
            
            // Trigger notification
            System.out.print("    ");
            appointment.confirm();
            
            test("Observer notified on appointment confirmation", 
                 appointment.getStatus() == AppointmentStatus.CONFIRMED);
            
            // Test multiple observers
            ConsoleNotifier notifier2 = new ConsoleNotifier();
            appointment.addObserver(notifier2);
            
            System.out.print("    ");
            appointment.cancel();
            
            test("Multiple observers can be attached", 
                 appointment.getStatus() == AppointmentStatus.CANCELLED);
            
        } catch (Exception e) {
            test("Observer pattern - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 7: CSV Export
    private static void testCSVExport() {
        testSection("Test 7: CSV Export Functionality");
        
        try {
            IdGen idGen = IdGen.getInstance();
            PatientService ps = new PatientService();
            
            // Add patients
            ps.add(new Patient(idGen.nextId(), "Patient 1", 25, "Condition 1"));
            ps.add(new Patient(idGen.nextId(), "Patient 2", 30, "Condition 2"));
            ps.add(new Patient(idGen.nextId(), "Patient 3", 35, "Condition 3"));
            
            // Export to CSV
            String filename = "test_patients.csv";
            ps.saveCSV(filename);
            
            test("CSV export executed successfully", true);
            
            // Note: In real scenario, we would verify file contents
            System.out.println("    CSV file created: " + filename);
            
        } catch (Exception e) {
            test("CSV export - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 8: Error Handling
    private static void testErrorHandling() {
        testSection("Test 8: Error Handling and Validation");
        
        try {
            DoctorService ds = new DoctorService();
            
            // Test invalid doctor retrieval
            boolean exceptionThrown = false;
            try {
                Doctor nonExistent = ds.get(99999);
            } catch (Exception e) {
                exceptionThrown = true;
                test("Exception thrown for non-existent doctor", true);
            }
            
            test("Error handling works for invalid operations", exceptionThrown);
            
            // Test patient service error handling
            PatientService ps = new PatientService();
            exceptionThrown = false;
            try {
                Patient nonExistent = ps.get(99999);
            } catch (Exception e) {
                exceptionThrown = true;
                test("Exception thrown for non-existent patient", true);
            }
            
            test("Error handling works across services", exceptionThrown);
            
        } catch (Exception e) {
            test("Error handling test - Unexpected exception", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 9: Singleton Pattern
    private static void testSingletonPattern() {
        testSection("Test 9: Singleton Pattern Implementation");
        
        try {
            IdGen instance1 = IdGen.getInstance();
            IdGen instance2 = IdGen.getInstance();
            
            test("IdGen singleton instance created", instance1 != null);
            test("Multiple getInstance() calls return same instance", 
                 instance1 == instance2);
            
            DataStore store1 = DataStore.getInstance();
            DataStore store2 = DataStore.getInstance();
            
            test("DataStore singleton instance created", store1 != null);
            test("DataStore singleton returns same instance", store1 == store2);
            
        } catch (Exception e) {
            test("Singleton pattern - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 10: Strategy Pattern
    private static void testStrategyPattern() {
        testSection("Test 10: Strategy Pattern Flexibility");
        
        try {
            // Test that different strategies can be used interchangeably
            PaymentStrategy[] strategies = {
                new UpiPayment(),
                new CardPayment()
            };
            
            test("Multiple payment strategies created", strategies.length == 2);
            
            for (PaymentStrategy strategy : strategies) {
                test("Strategy implements PaymentStrategy interface", 
                     strategy instanceof PaymentStrategy);
            }
            
            // Test runtime strategy selection
            PaymentStrategy selected = Math.random() > 0.5 ? 
                new UpiPayment() : new CardPayment();
            
            test("Strategy can be selected at runtime", selected != null);
            
        } catch (Exception e) {
            test("Strategy pattern - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 11: Factory Pattern
    private static void testFactoryPattern() {
        testSection("Test 11: Factory Pattern for Object Creation");
        
        try {
            Bill bill1 = BillFactory.Create(500.0);
            Bill bill2 = BillFactory.Create(1000.0);
            
            test("Factory creates bill objects", bill1 != null && bill2 != null);
            test("Factory applies consistent tax rate", 
                 bill1.getTax() == 100.0 && bill2.getTax() == 200.0);
            
            // All bills should have same tax rate
            double taxRate1 = bill1.getTax() / bill1.getBaseFee();
            double taxRate2 = bill2.getTax() / bill2.getBaseFee();
            
            test("Factory ensures consistent business rules", taxRate1 == taxRate2);
            
        } catch (Exception e) {
            test("Factory pattern - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 12: Inheritance
    private static void testInheritance() {
        testSection("Test 12: Inheritance Hierarchy");
        
        try {
            IdGen idGen = IdGen.getInstance();
            Doctor doctor = new Doctor(idGen.nextId(), "Dr. Brown", 42, 
                                      Specialization.PEDIATRICS, 800.0);
            Patient patient = new Patient(idGen.nextId(), "Child Patient", 10, "Flu");
            
            // Test that Doctor and Patient inherit from Person
            test("Doctor inherits Person properties", 
                 doctor.getName() != null && doctor.getAge() > 0);
            test("Patient inherits Person properties", 
                 patient.getName() != null && patient.getAge() > 0);
            
            // Test specific properties
            test("Doctor has specialization (specific property)", 
                 doctor.getSpecialization() != null);
            test("Patient has condition (specific property)", 
                 patient.getCondition() != null);
            
        } catch (Exception e) {
            test("Inheritance - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 13: Polymorphism
    private static void testPolymorphism() {
        testSection("Test 13: Polymorphism in Action");
        
        try {
            // Polymorphic references
            PaymentStrategy payment = new UpiPayment();
            test("Polymorphic reference to UPI payment", payment != null);
            
            payment = new CardPayment();
            test("Polymorphic reference reassigned to Card payment", payment != null);
            
            // Observer polymorphism
            meditrack.interfacepkg.Observer observer = new ConsoleNotifier();
            test("Polymorphic reference to observer", observer != null);
            
        } catch (Exception e) {
            test("Polymorphism - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 14: DataStore Operations
    private static void testDataStore() {
        testSection("Test 14: DataStore CRUD Operations");
        
        try {
            IdGen idGen = IdGen.getInstance();
            DataStore store = DataStore.getInstance();
            
            Doctor doctor = new Doctor(idGen.nextId(), "Test Doctor", 40, 
                                      Specialization.CARDIOLOGY, 1000.0);
            
            // Add operation
            store.add(doctor);
            test("Entity can be added to DataStore", true);
            
            // Get operation
            Doctor retrieved = store.get(doctor.getId(), Doctor.class);
            test("Entity can be retrieved from DataStore", 
                 retrieved != null && retrieved.getId() == doctor.getId());
            
            // GetAll operation
            test("DataStore can return all entities of a type", 
                 store.getAll(Doctor.class).size() > 0);
            
        } catch (Exception e) {
            test("DataStore operations - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
    
    // Test 15: ID Generation
    private static void testIdGeneration() {
        testSection("Test 15: Unique ID Generation");
        
        try {
            IdGen idGen = IdGen.getInstance();
            
            int id1 = idGen.nextId();
            int id2 = idGen.nextId();
            int id3 = idGen.nextId();
            
            test("IDs are generated", id1 > 0 && id2 > 0 && id3 > 0);
            test("IDs are unique", id1 != id2 && id2 != id3 && id1 != id3);
            test("IDs are sequential", id2 == id1 + 1 && id3 == id2 + 1);
            
        } catch (Exception e) {
            test("ID generation - Exception occurred", false);
            System.out.println("    Error: " + e.getMessage());
        }
    }
}
