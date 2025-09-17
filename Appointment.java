package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    @NotNull
    @Future
    private LocalDateTime appointmentTime;

    private String status;

    // Getters and setters
}
