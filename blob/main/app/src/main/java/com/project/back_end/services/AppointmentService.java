package com.project.back_end.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * AppointmentService chứa cả interface, implement và data giả lập trong 1 file
 * để thuận tiện cho việc nộp bài.
 */
public class AppointmentService {

    // ===== Model nhỏ gọn cho demo =====
    public static class Appointment {
        private Long id;
        private Long doctorId;
        private Long patientId;
        private LocalDateTime start;
        private LocalDateTime end;

        public Appointment(Long id, Long doctorId, Long patientId,
                           LocalDateTime start, LocalDateTime end) {
            this.id = id;
            this.doctorId = doctorId;
            this.patientId = patientId;
            this.start = start;
            this.end = end;
        }
        public Long getId() { return id; }
        public Long getDoctorId() { return doctorId; }
        public Long getPatientId() { return patientId; }
        public LocalDateTime getStart() { return start; }
        public LocalDateTime getEnd() { return end; }
    }

    // ===== DTO trả về khi check availability =====
    public static class AvailabilityResponse {
        public final boolean available;
        public final String message;
        public AvailabilityResponse(boolean available, String message) {
            this.available = available;
            this.message = message;
        }
    }

    // ===== Service Logic =====
    private final List<Appointment> appointmentStore = new ArrayList<>();

    /** Giả lập token validation */
    private boolean isValidToken(String token) {
        return token != null && token.startsWith("valid-");
    }

    /** Check doctor availability theo yêu cầu: doctorId, date, role, token */
    public AvailabilityResponse checkDoctorAvailability(Long doctorId,
                                                        LocalDate date,
                                                        String role,
                                                        String token) {
        if (!isValidToken(token)) {
            return new AvailabilityResponse(false, "Invalid token");
        }
        if (!"patient".equalsIgnoreCase(role) && !"admin".equalsIgnoreCase(role)) {
            return new AvailabilityResponse(false, "Unauthorized role");
        }

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        boolean hasAny = appointmentStore.stream()
                .anyMatch(a -> a.getDoctorId().equals(doctorId)
                        && !a.getStart().isAfter(end)
                        && !a.getEnd().isBefore(start));

        if (hasAny) {
            return new AvailabilityResponse(false,
                    "Doctor " + doctorId + " is not available on " + date);
        } else {
            return new AvailabilityResponse(true,
                    "Doctor " + doctorId + " is available on " + date);
        }
    }

    /** Lấy tất cả appointments của 1 bác sĩ trong 1 ngày */
    public List<Appointment> getAppointmentsForDoctor(Long doctorId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        List<Appointment> result = new ArrayList<>();
        for (Appointment a : appointmentStore) {
            if (a.getDoctorId().equals(doctorId)
                    && !a.getStart().isAfter(end)
                    && !a.getEnd().isBefore(start)) {
                result.add(a);
            }
        }
        return result;
    }

    /** Đặt lịch hẹn mới (book appointment) */
    public Appointment bookAppointment(Long doctorId, Long patientId,
                                       LocalDateTime start, LocalDateTime end, String token) {
        if (!isValidToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }
        // check overlap
        boolean overlaps = appointmentStore.stream()
                .anyMatch(a -> a.getDoctorId().equals(doctorId)
                        && a.getStart().isBefore(end)
                        && a.getEnd().isAfter(start));
        if (overlaps) {
            throw new IllegalStateException("Time slot not available");
        }
        Appointment a = new Appointment((long) (appointmentStore.size() + 1),
                doctorId, patientId, start, end);
        appointmentStore.add(a);
        return a;
    }
}
