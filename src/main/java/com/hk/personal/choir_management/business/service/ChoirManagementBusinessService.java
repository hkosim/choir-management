package com.hk.personal.choir_management.business.service;


import com.hk.personal.choir_management.model.dto.AppointmentView;
import com.hk.personal.choir_management.model.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.model.dto.appointment.AppointmentAttendanceRequestDto;
import com.hk.personal.choir_management.model.dto.attendance.MemberAttendanceDto;
import com.hk.personal.choir_management.model.dto.member.*;
import com.hk.personal.choir_management.model.entity.*;
import com.hk.personal.choir_management.model.enums.AppointmentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChoirManagementBusinessService {
    // MEMBERS
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    List<String> getRoles(String username);
    MemberProfileDto getMemberProfile(String username);

    Page<Member> getAllMembers(Pageable pageable);
    List<MemberAttendanceDto> getAllAttendances();


    Page<Song> getSongs(Pageable pageable);

    // APPOINTMENTS
    Page<AppointmentAttendanceDto> getMemberAttendances(String username, Pageable pageable);
    Appointment getAppointment(Long id);
    Appointment saveAppointment(Appointment appointment);
    AppointmentAttendanceDto updateAttendance(AppointmentAttendanceRequestDto appointmentAttendanceRequestDto);


}
