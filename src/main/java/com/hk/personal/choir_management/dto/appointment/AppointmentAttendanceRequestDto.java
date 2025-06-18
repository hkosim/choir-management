package com.hk.personal.choir_management.dto.appointment;

public record AppointmentAttendanceRequestDto(
        String username,
        Long id,
        String type,
        Boolean present
) {}
