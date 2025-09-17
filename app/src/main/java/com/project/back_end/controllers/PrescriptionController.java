package com.project.back_end.controllers;

import com.project.back_end.models.Prescription;
import com.project.back_end.repositories.PrescriptionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // ✅ Get all prescriptions
    @GetMapping("/{token}")
    public ResponseEntity<List<Prescription>> getAllPrescriptions(@PathVariable String token) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(prescriptionRepository.findAll());
    }

    // ✅ Get a prescription by ID
    @GetMapping("/{id}/{token}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id, @PathVariable String token) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return prescriptionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Create a new prescription (with @Valid)
    @PostMapping("/{token}")
    public ResponseEntity<Prescription> createPrescription(
            @PathVariable String token,
            @Valid @RequestBody Prescription prescription) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Prescription saved = prescriptionRepository.save(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ✅ Update a prescription
    @PutMapping("/{id}/{token}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable Long id,
            @PathVariable String token,
            @Valid @RequestBody Prescription updatedPrescription) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Prescription> optional = prescriptionRepository.findById(id);
        if (optional.isPresent()) {
            Prescription existing = optional.get();
            existing.setPatientId(updatedPrescription.getPatientId());
            existing.setDoctorId(updatedPrescription.getDoctorId());
            existing.setMedication(updatedPrescription.getMedication());
            existing.setDosage(updatedPrescription.getDosage());
            existing.setInstructions(updatedPrescription.getInstructions());
            return ResponseEntity.ok(prescriptionRepository.save(existing));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Delete a prescription
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id, @PathVariable String token) {
        if (!isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!prescriptionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        prescriptionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Simple token validation (mock)
    private boolean isValidToken(String token) {
        return token != null && token.startsWith("valid-");
    }
}
