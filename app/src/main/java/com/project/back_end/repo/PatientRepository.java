package com.project.back_end.repo;

import com.project.back_end.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // --- Exact lookups (preferred for login/unique fields) ---
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByEmailIgnoreCase(String email);
    Optional<Patient> findByPhone(String phone);

    // --- Existence checks (useful for validation before create/update) ---
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    // --- Simple keyword search by email or name (optional) ---
    List<Patient> findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(String emailKeyword,
                                                                                String nameKeyword);

    // --- Custom JPQL: lookup by email or phone in one call (optional) ---
    @Query("""
           select p from Patient p
           where lower(p.email) = lower(:identifier)
              or p.phone = :identifier
           """)
    Optional<Patient> findByEmailIgnoreCaseOrPhone(String identifier);
}
