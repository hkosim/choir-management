package com.hk.personal.choir_management.entity;

import com.hk.personal.choir_management.entity.superclass.Appointment;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "rehearsals")
public class Rehearsal extends Appointment {

    public Rehearsal() {
    }

    public Rehearsal(Long id, String title, String description, LocalDate date, LocalTime time, String location) {
        super(id, title, description, date, time, location);
    }


}
