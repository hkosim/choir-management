package com.hk.personal.choir_management.repository;

import com.hk.personal.choir_management.model.entity.Appointment;
import com.hk.personal.choir_management.model.enums.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByIdAndAppointmentType(Long id, AppointmentType appointmentType);

}
