package com.project.back_end.controllers;

import com.project.back_end.models.Doctor;
import com.project.back_end.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    // Get all doctors
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Create new doctor
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Get doctor by ID
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    // Delete doctor
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
    }

    // ✅ New endpoint: Check doctor availability
    @GetMapping("/{id}/availability")
    public String checkDoctorAvailability(
            @PathVariable Long id,
            @RequestParam String date,
            @RequestParam String role,
            @RequestParam String token
    ) {
        // Simulate token validation
        if (!isValidToken(token)) {
            return "Invalid token";
        }

        // Fetch doctor
        Optional<Doctor> doctorOpt = doctorRepository.findById(id);
        if (doctorOpt.isEmpty()) {
            return "Doctor not found";
        }

        Doctor doctor = doctorOpt.get();

        // Example: allow only specific roles
        if (!role.equalsIgnoreCase("patient") && !role.equalsIgnoreCase("admin")) {
            return "Unauthorized role";
        }

        // Convert date string to LocalDate
        LocalDate requestedDate = LocalDate.parse(date);

        // In a real app, you’d check appointments, schedules, etc.
        // Here we simulate availability check
        boolean isAvailable = Math.random() > 0.5;  // Random mock availability

        return "Doctor " + doctor.getName() + " is " 
                + (isAvailable ? "available" : "not available") 
                + " on " + requestedDate;
    }

    // ✅ Simple token validation (mock)
    private boolean isValidToken(String token) {
        return token != null && token.startsWith("valid-");
    }
}
