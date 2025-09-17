# MySQL Schema Design

## Table: Doctor
- doctor_id (INT, PRIMARY KEY, AUTO_INCREMENT)
- name (VARCHAR(100))
- specialty (VARCHAR(100))
- available_times (TEXT)

## Table: Patient
- patient_id (INT, PRIMARY KEY, AUTO_INCREMENT)
- name (VARCHAR(100))
- email (VARCHAR(100))
- phone (VARCHAR(15))

## Table: Appointment
- appointment_id (INT, PRIMARY KEY, AUTO_INCREMENT)
- doctor_id (INT, FOREIGN KEY REFERENCES Doctor(doctor_id))
- patient_id (INT, FOREIGN KEY REFERENCES Patient(patient_id))
- appointment_time (DATETIME)
- status (VARCHAR(20))

## Table: Admin
- admin_id (INT, PRIMARY KEY, AUTO_INCREMENT)
- name (VARCHAR(100))
- email (VARCHAR(100))
