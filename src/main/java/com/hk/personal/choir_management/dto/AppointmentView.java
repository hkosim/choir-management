package com.hk.personal.choir_management.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentView {
    Long id();
    String type();
    String title();
    String description();
    LocalDate date();
    String location();
    LocalTime time();
}