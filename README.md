
# Sunrise Dental Clinic Patient Management System

A web-based Patient and Appointment Management System developed for Sunrise Dental Clinic.

The system is designed to replace manual paper-based processes by providing a centralized platform for managing patients, appointments, billing, and clinic reports.

## Features

### User Authentication

- User login authentication
- Username and password validation
- Role-based user information

### Patient Management

- Add new patients
- View patient records
- Search patients
- Store patient information in MySQL

### Appointment Management

- Create appointments
- View upcoming appointments
- Search appointments
- Assign patients, dentists, and treatments
- Validate appointment information
- Prevent duplicate appointment scheduling

### Billing Management

- Generate patient bills
- Calculate consultation fees
- Retrieve treatment costs
- Automatically calculate total amounts
- Display recent bills
- Store billing information in MySQL
- Prevent multiple bills for the same appointment

### Reports

- Daily appointment reports
- Treatment and revenue reports
- Generate reports using data stored in MySQL

## Technologies Used

### Frontend

- HTML
- CSS
- JavaScript

### Backend

- Java
- Java HttpServer
- RESTful APIs

### Database

- MySQL
- JDBC

### Development Tools

- Eclipse IDE
- WAMP Server
- phpMyAdmin
- Git
- GitHub

## System Architecture

The system follows a layered architecture.

```text
HTML / CSS / JavaScript
        ↓
RESTful API
        ↓
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
MySQL Database
```
## Design Patterns

The project applies several software design patterns and architectural approaches:

- MVC Pattern
- Repository Pattern
- Service Layer Pattern
- DTO Pattern
- Singleton Pattern
  
## REST API Endpoints

- Authentication
```text
POST /api/auth/login
```
- Patients
```text
GET /api/patients
POST /api/patients
PUT /api/patients/{id}
DELETE /api/patients/{id}
```
- Dentists
```text
GET /api/dentists
```
- Treatments
```text
GET /api/treatments
```
- Appointments
```text
GET /api/appointments
GET /api/appointments/{appointmentNumber}
POST /api/appointments
PUT /api/appointments/{id}
DELETE /api/appointments/{id}
```
- Bills
```text
GET /api/bills
GET /api/bills/{id}
POST /api/bills
```
- Reports
```text
GET /api/reports/daily-appointments?date=YYYY-MM-DD
GET /api/reports/treatment-revenue?period=today
```

## Project Structure

```text
Sunrise-Dental-Clinic/
│
├── database/
│   ├── schema.sql
│   └── README.md
│
├── src/
│   └── com/
│       └── sunrisedental/
│           ├── controller/
│           ├── model/
│           ├── repository/
│           ├── service/
│           ├── util/
│           └── server/
│
├── web/
│   ├── css/
│   ├── js/
│   └── pages/
│
├── lib/
│
├── .gitignore
└── README.md
```

## Database Setup

- Start WAMP Server.
- Start the MySQL service.
- Open phpMyAdmin.
- Import the following file:
```text
database/schema.sql
```
- Confirm that the following database has been created:
```text
sunrise_dental_clinic
```
- Configure the database connection details in:
```text
src/com/sunrisedental/util/DatabaseConnection.java
```
- Running the Application


## Backend

- Run the following Java class:
```text
com.sunrisedental.server.DentalClinicServer
```
- The backend server runs at:
```text
http://localhost:8080
```

## Frontend

- Open the frontend pages through your local web environment.
- The main application pages are located in:
```text
web/pages/
```

## Testing

- The system was tested to verify the functionality of:
```text
Authentication
Patient management
Appointment management
Billing
Reports
Database integration
REST API communication
Input validation
Error handling
```
- Final system testing was completed successfully.

## Version Control

- Git and GitHub were used throughout the development process to manage source code versions and project changes.

- The project development followed a commit-based workflow, with changes being tested before being committed and pushed to the GitHub repository.

### Author

Anjali Aravinthan

BSc (Hons) Software Engineering

Cardiff Metropolitan University

### Academic Project

This project was developed as part of an academic software engineering assignment.
```text
© 2026 Sunrise Dental Clinic Patient Management System
```
