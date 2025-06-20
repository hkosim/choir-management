package com.hk.personal.choir_management.model.dto.appointment;

import com.hk.personal.choir_management.model.enums.AttendanceStatus;

public record AppointmentAttendanceRequestDto(
        String username,
        Long id,
        AttendanceStatus attendanceStatus
) {}
