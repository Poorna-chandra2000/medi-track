# MediTrack - Hospital Management System

A Java-based hospital management system demonstrating object-oriented programming principles, design patterns, and clean architecture.

## 📋 Table of Contents
- [Features](#features)
- [System Requirements](#system-requirements)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [Design Patterns Used](#design-patterns-used)
- [Sample Run Outputs](#sample-run-outputs)
- [Testing](#testing)
- [Documentation](#documentation)

## ✨ Features

- **Doctor Management**: Add and manage doctors with specializations
- **Patient Management**: Register patients with medical conditions
- **Appointment Scheduling**: Book and track appointments
- **Billing System**: Generate bills with automatic calculations
- **Payment Processing**: Multiple payment methods (UPI, Card)
- **Observer Pattern**: Real-time notifications for appointment confirmations
- **CSV Export**: Save patient data to CSV files
- **Data Validation**: Input validation and error handling

## 🔧 System Requirements

- Java Development Kit (JDK) 17 or higher
- Any text editor or IDE (IntelliJ IDEA, Eclipse, VS Code)
- Terminal/Command Prompt

## 📁 Project Structure

```
airtribe/
├── meditrack/
│   ├── Main.java                           # Application entry point
│   ├── constants/
│   │   ├── AppointmentStatus.java          # Appointment status enum
│   │   ├── Constants.java                  # Application constants
│   │   └── Specialization.java             # Doctor specialization enum
│   ├── entity/
│   │   ├── Appointment.java                # Appointment entity
│   │   ├── Bill.java                       # Bill entity
│   │   ├── BillingSummary.java             # Billing summary
│   │   ├── Doctor.java                     # Doctor entity
│   │   ├── MedicalEntity.java              # Base medical entity
│   │   ├── Patient.java                    # Patient entity
│   │   └── Person.java                     # Base person entity
│   ├── exceptions/
│   │   ├── AppointmentNotFoundException.java
│   │   └── InvalidDataException.java
│   ├── factory/
│   │   └── BillFactory.java                # Bill creation factory
│   ├── interfacepkg/
│   │   ├── Observer.java                   # Observer pattern interface
│   │   ├── Payable.java                    # Payable interface
│   │   └── Subject.java                    # Subject pattern interface
│   ├── payment/
│   │   ├── CardPayment.java                # Card payment implementation
│   │   ├── PaymentStrategy.java            # Payment strategy interface
│   │   └── UpiPayment.java                 # UPI payment implementation
│   ├── repository/
│   │   └── DataStore.java                  # In-memory data store
│   ├── service/
│   │   ├── AppointmentService.java         # Appointment business logic
│   │   ├── DoctorService.java              # Doctor business logic
│   │   └── PatientService.java             # Patient business logic
│   └── utils/
│       ├── ConsoleNotifier.java            # Console notification observer
│       ├── CSVUtils.java                   # CSV utility functions
│       ├── IdGen.java                      # ID generator (Singleton)
│       └── Validator.java                  # Input validation utility
├── TestRunner.java                         # Manual test runner
├── patient.csv                             # Sample patient data
├── docs/
│   ├── JVM_Report.md                       # JVM concepts report
│   ├── Setup_Instructions.md               # Detailed setup guide
│   └── Design_Decisions.md                 # Architecture decisions
└── README.md                               # This file
```

## 🚀 Setup Instructions

### Quick Start

1. **Clone or extract the repository**
   ```bash
   cd airtribe
   ```

2. **Compile all Java files**
   ```bash
   javac meditrack/**/*.java meditrack/*.java
   ```

3. **Run the application**
   ```bash
   java meditrack.Main
   ```

For detailed setup instructions, see [docs/Setup_Instructions.md](docs/Setup_Instructions.md)

## 💻 Usage

### Interactive Menu

The application provides an interactive menu with the following options:

```
1. Add Doc          - Add a new doctor to the system
2. Add patient      - Register a new patient
3. Book Appointment - Schedule an appointment
4. List Appointment - View all appointments
5. Generate Bill    - Create and process a bill
6. Save CSV         - Export patient data to CSV
7. Exit             - Exit the application
```

### Step-by-Step Workflow

1. **Add a Doctor**
   - Select option 1
   - Doctor will be created with auto-generated ID and name
   - Doctor details will be displayed

2. **Add a Patient**
   - Select option 2
   - Patient will be registered with auto-generated ID
   - Patient details will be displayed

3. **Book an Appointment**
   - Select option 3
   - Enter Doctor ID (from step 1)
   - Enter Patient ID (from step 2)
   - Appointment will be created with PENDING status

4. **List Appointments**
   - Select option 4
   - View all scheduled appointments

5. **Generate and Pay Bill**
   - Select option 5
   - Enter Appointment ID
   - Bill will be generated and payment processed
   - Appointment status changes to CONFIRMED
   - Notification sent to observers

6. **Export Data**
   - Select option 6
   - Patient data saved to patient.csv

## 🎨 Design Patterns Used

1. **Singleton Pattern**: `IdGen` for unique ID generation
2. **Observer Pattern**: Real-time appointment notifications
3. **Strategy Pattern**: Multiple payment methods (UPI, Card)
4. **Factory Pattern**: `BillFactory` for bill creation
5. **Repository Pattern**: `DataStore` for data management

For detailed design decisions, see [docs/Design_Decisions.md](docs/Design_Decisions.md)

## 📊 Sample Run Outputs

### Example 1: Complete Workflow

```
1.Add Doc
2.Add patient
3.Book Appointment
4.List Appointment
5.Generate Bill
6.Save CSV
7.Exit

> 1
Doctor added: Doctor{id=1, name='Doc2', age=40, specialization=CARDIOLOGY, fee=800.0}

> 2
Patient added: Patient{id=3, name='Patient4', age=30, condition='Fever'}

> 3
DocID:
> 1
PatientID:
> 3
Appointment created: Appointment{id=5, doctor=Doc2, patient=Patient4, status=PENDING}

> 4
Listing all appointments:
Appointment{id=5, doctor=Doc2, patient=Patient4, status=PENDING}

> 5
ApptID:
> 5
Payment of ₹960.0 processed via UPI
[NOTIFICATION] Appointment #5 confirmed for Patient4 with Doc2
BillSummary 960.0

> 6
Patient data saved to patient.csv
```

### Example 2: Error Handling

```
> 3
DocID:
> 999
PatientID:
> 1
Error: Doctor not found with ID: 999

> 5
ApptID:
> 999
Error: Appointment not found with ID: 999
```

### Example 3: Bill Calculation

```
Doctor Fee: ₹800
Tax (20%): ₹160
Total Bill: ₹960
Payment Method: UPI
Status: CONFIRMED
```

## 🧪 Testing

### Manual Testing

Run the test suite using:
```bash
javac TestRunner.java
java TestRunner
```

The TestRunner validates:
- ✅ Doctor creation and management
- ✅ Patient registration
- ✅ Appointment booking
- ✅ Bill generation and calculation
- ✅ Payment processing
- ✅ Observer notifications
- ✅ CSV export functionality
- ✅ Error handling

### Test Coverage
- Entity creation and validation
- Service layer operations
- Design pattern implementations
- Exception handling
- Data persistence

## 📚 Documentation

- **[JVM_Report.md](docs/JVM_Report.md)**: Deep dive into JVM concepts used
- **[Setup_Instructions.md](docs/Setup_Instructions.md)**: Detailed installation guide
- **[Design_Decisions.md](docs/Design_Decisions.md)**: Architecture and pattern choices

## 🏗️ Architecture Highlights

### Layered Architecture
- **Entity Layer**: Domain models
- **Service Layer**: Business logic
- **Repository Layer**: Data access
- **Utility Layer**: Helper functions

### Key Components
- **Data Store**: In-memory storage with generic support
- **ID Generator**: Thread-safe singleton for unique IDs
- **Validation**: Input validation and error handling
- **Notification**: Observer-based event system

## 🔒 Error Handling

The application handles:
- Invalid user inputs
- Non-existent entity references
- Data validation failures
- File I/O errors

All errors are caught and displayed with helpful messages.

## 📝 CSV Export Format

```csv
id,name,age,condition
3,Patient4,30,Fever
```

## 🎯 Future Enhancements

- Database integration (replace in-memory storage)
- RESTful API endpoints
- User authentication and authorization
- Advanced appointment scheduling with time slots
- Prescription management
- Medical history tracking
- Report generation (PDF)
- Email/SMS notifications

## 👥 Contributors

- Project for Airtribe Java Development Course

## 📄 License

This project is for educational purposes.

---

For questions or issues, please refer to the documentation in the `docs/` folder.
