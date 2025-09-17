package com.project.back_end.controllers;

import com.project.back_end.models.Prescription;
import com.project.back_end.repositories.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // Get all prescriptions
    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    // Get a prescription by ID
    @GetMapping("/{id}")
    public Prescription getPrescriptionById(@PathVariable Long id) {
        Optional<Prescription> optional = prescriptionRepository.findById(id);
        return optional.orElse(null);
    }

    // Create a new prescription
    @PostMapping
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    // Update a prescription
    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable Long id, @RequestBody Prescription updatedPrescription) {
        Optional<Prescription> optional = prescriptionRepository.findById(id);
        if (optional.isPresent()) {
            Prescription existing = optional.get();
            existing.setPatientId(updatedPrescription.getPatientId());
            existing.setDoctorId(updatedPrescription.getDoctorId());
            existing.setMedication(updatedPrescription.getMedication());
            existing.setDosage(updatedPrescription.getDosage());
            existing.setInstructions(updatedPrescription.getInstructions());
            return prescriptionRepository.save(existing);
        } else {
            return null;
        }
    }

    // Delete a prescription
    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable Long id) {
        prescriptionRepository.deleteById(id);
    }
}
