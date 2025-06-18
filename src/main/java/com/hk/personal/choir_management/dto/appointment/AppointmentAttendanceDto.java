package com.hk.personal.choir_management.dto.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentAttendanceDto(
        Long id,
        String type,    // REHEARSAL / PERFORMANCE
        String title,
        String description,
        LocalDate date,
        LocalTime time,
        String location,
        boolean present
) {}