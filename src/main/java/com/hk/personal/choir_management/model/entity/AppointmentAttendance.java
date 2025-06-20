package com.hk.personal.choir_management.model.entity;

import com.hk.personal.choir_management.model.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "appointment_attendances")
public class AppointmentAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Appointment appointment;

    private AttendanceStatus attendanceStatus;

    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private Instant lastModifiedAt;

    public AppointmentAttendance(Member member, Appointment appointment, AttendanceStatus attendanceStatus) {
        this.member = member;
        this.appointment = appointment;
        this.attendanceStatus = attendanceStatus;
    }
}