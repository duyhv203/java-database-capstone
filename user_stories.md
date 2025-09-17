# User Stories

## Doctor User Stories

### User Story: Doctor views patient list  
As a doctor,  
I want to view a list of my assigned patients,  
So that I can track their treatment and history.  

**Acceptance Criteria:**  
- The list displays patient names, appointment dates, and status.  
- The doctor can filter patients by status (active/inactive).  
- The doctor can click to view patient details.  

**Priority:** High  
**Story Points:** 3  

---

### User Story: Doctor updates patient notes  
As a doctor,  
I want to update patient medical notes after each appointment,  
So that treatment history is always up to date.  

**Acceptance Criteria:**  
- The doctor can add or edit notes after an appointment.  
- Notes are saved and visible in the patient’s history.  
- Only the assigned doctor can edit their own notes.  

**Priority:** High  
**Story Points:** 4  

---

### User Story: Doctor views daily appointments  
As a doctor,  
I want to see my schedule for the day,  
So that I can prepare for upcoming appointments.  

**Acceptance Criteria:**  
- The schedule displays all appointments for the current day.  
- Each entry shows patient name, time, and reason for visit.  
- The doctor can mark an appointment as completed.  

**Priority:** Medium  
**Story Points:** 3  

---

## Patient User Stories

### User Story: Patient books appointment  
As a patient,  
I want to book an appointment with a doctor,  
So that I can receive timely medical care.  

**Acceptance Criteria:**  
- The patient can select a doctor by specialty.  
- The patient can choose an available date and time.  
- The system confirms the booking and shows appointment details.  

**Priority:** High  
**Story Points:** 5  

---

### User Story: Patient views medical history  
As a patient,  
I want to view my past appointments and doctor notes,  
So that I can track my treatment history.  

**Acceptance Criteria:**  
- The patient can see a list of past appointments.  
- Each record shows the doctor’s notes and prescriptions.  
- Records cannot be edited by the patient.  

**Priority:** Medium  
**Story Points:** 4  

---

### User Story: Patient cancels appointment  
As a patient,  
I want to cancel an appointment,  
So that I can free up my schedule if I cannot attend.  

**Acceptance Criteria:**  
- The patient can cancel an appointment before the scheduled time.  
- The doctor is notified of the cancellation.  
- The system confirms cancellation to the patient.  

**Priority:** Low  
**Story Points:** 2  

---

## Admin User Stories

### User Story: Admin manages doctor accounts  
As an admin,  
I want to add, update, or remove doctor accounts,  
So that the system always has correct and up-to-date information.  

**Acceptance Criteria:**  
- The admin can create a new doctor profile with name, specialty, and contact info.  
- The admin can edit doctor details.  
- The admin can deactivate or delete a doctor account.  

**Priority:** Medium  
**Story Points:** 4  

---

### User Story: Admin manages patient accounts  
As an admin,  
I want to manage patient accounts,  
So that I can support users with login or profile issues.  

**Acceptance Criteria:**  
- The admin can reset a patient’s password.  
- The admin can deactivate or delete patient accounts.  
- The admin can update patient contact details if requested.  

**Priority:** Medium  
**Story Points:** 3  

---

### User Story: Admin generates system reports  
As an admin,  
I want to generate reports about doctors and patients,  
So that I can monitor system usage and performance.  

**Acceptance Criteria:**  
- The admin can view total number of doctors and patients.  
- The admin can generate appointment statistics by month.  
- The report can be exported to CSV/PDF.  

**Priority:** High  
**Story Points:** 5  
