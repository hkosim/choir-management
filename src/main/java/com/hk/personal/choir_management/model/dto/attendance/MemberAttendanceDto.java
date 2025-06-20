package com.hk.personal.choir_management.model.dto.attendance;

import com.hk.personal.choir_management.model.entity.AppointmentAttendance;

import java.util.List;

public record MemberAttendanceDto(
        String username,
        String name,
        String voicePart,
        List<AppointmentAttendance> appointmentAttendances
) {}