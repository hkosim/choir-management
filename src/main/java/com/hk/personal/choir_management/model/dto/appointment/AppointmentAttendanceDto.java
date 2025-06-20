package com.hk.personal.choir_management.model.dto.appointment;

import com.hk.personal.choir_management.model.entity.Appointment;
import com.hk.personal.choir_management.model.enums.AppointmentType;
import com.hk.personal.choir_management.model.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentAttendanceDto(
        Appointment appointment,
        AttendanceStatus attendanceStatus
) {}