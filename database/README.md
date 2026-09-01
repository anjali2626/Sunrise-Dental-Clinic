# Database

This folder contains the MySQL database files for the Sunrise Dental Clinic Patient Management System.


## Database Name

sunrise_dental_clinic


## Database Files

```text
schema.sql
```

The schema.sql file contains the SQL statements required to create the database and all required tables for the Sunrise Dental Clinic Patient Management System.


## The database includes the following tables:


- users
- patients
- dentists
- treatments
- appointments
- bills
- Database Structure
- Users

## The users table stores system user authentication information.

Main fields include:

- User ID
- Username
- Password hash
- Full name
- Role
- Account creation date
- Patients

## The patients table stores patient information.

Main fields include:

- Patient ID
- Patient name
- Address
- Contact number
- Record creation date
- Dentists

## The dentists table stores dentist information.

Main fields include:

- Dentist ID
- Dentist name
- Specialization
- Contact number
- Active status
- Treatments

## The treatments table stores information about dental treatments.

Main fields include:

- Treatment ID
- Treatment name
- Treatment cost
- Active status
- Appointments

## The appointments table stores patient appointment information.

Main fields include:

- Appointment ID
- Appointment number
- Patient ID
- Dentist ID
- Treatment ID
- Appointment date
- Appointment time
- Appointment status
- Record creation date
- Bills

## The bills table stores billing information related to appointments.

Main fields include:

- Bill ID
- Appointment ID
- Consultation fee
- Treatment cost
- Total amount
- Bill date
- Database Relationships


## The database uses foreign key relationships to maintain data consistency.

Patients
    │
    └── Appointments ─── Bills
           │
           ├── Dentists
           │
           └── Treatments


### The relationships are:

- Each appointment belongs to one patient.
- Each appointment is assigned to one dentist.
- Each appointment is associated with one treatment.
- Each bill belongs to one appointment.
- Each appointment can have only one bill.
- Database Constraints

### The database includes several constraints to maintain data integrity.

Unique Constraints

- Usernames must be unique.
- Treatment names must be unique.
- Appointment numbers must be unique.
- A dentist cannot have multiple appointments at the same date and time.
- Each appointment can only have one bill.

Foreign Key Constraints

Appointments are connected to:

- Patients
- Dentists
- Treatments

Bills are connected to:

- Appointments

These relationships help prevent invalid or inconsistent data.


## Database Setup

### Follow these steps to set up the database.

- Step 1: Start WAMP Server - Start WAMP Server and ensure that the MySQL service is running.

- Step 2: Open phpMyAdmin - Open phpMyAdmin from the WAMP Server dashboard.

- Step 3: Import the Database Schema - Import the schema.sql file

- Step 4: Verify the Database - After importing the schema, verify that the following database has been created: sunrise_dental_clinic

- Step 5: Verify the Tables - Confirm that the following tables are available:

```text
users
patients
dentists
treatments
appointments
bills
```


## Database Connection

The Java backend connects to the MySQL database using JDBC.

The database connection configuration is located in:

```text
src/com/sunrisedental/util/DatabaseConnection.java
```

Ensure that the database connection details match your local MySQL configuration before running the application.


## Required Technologies

The database setup requires:

- MySQL
- WAMP Server or another MySQL server environment
- phpMyAdmin
- MySQL Connector/J
- Java JDBC
- Data Flow

The main database flow of the system is:

```text
Patient
   ↓
Appointment
   ↓
Treatment and Dentist Assignment
   ↓
Billing
   ↓
Reports
```

The reports are generated using appointment, treatment, and billing information stored in the MySQL database.

## Notes

- The database is used as the central data storage for the system.
- JDBC is used to connect the Java backend with MySQL.
- Repository classes are responsible for performing database operations.
- Foreign key constraints are used to maintain relationships between tables.
- The database supports patient management, appointment scheduling, billing, and report generation.

## Project

This database is part of the:

```text
Sunrise Dental Clinic Patient Management System
```
