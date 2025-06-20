package com.hk.personal.choir_management.model.dto;

import com.hk.personal.choir_management.model.enums.AppointmentType;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentView {
    Long id();
    AppointmentType type();
    String title();
    String description();
    LocalDate date();
    String location();
    LocalTime time();
}