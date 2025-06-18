package com.hk.personal.choir_management.dto.appointment;

import com.hk.personal.choir_management.dto.AppointmentView;

import java.time.LocalDate;
import java.time.LocalTime;

public record RehearsalDto(
    Long id,
    String type,
    String title,
    String description,
    LocalDate date,
    LocalTime time,
    String location) implements AppointmentView {
}
