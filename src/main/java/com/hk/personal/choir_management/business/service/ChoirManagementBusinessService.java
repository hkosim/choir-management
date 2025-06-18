package com.hk.personal.choir_management.business.service;


import com.hk.personal.choir_management.dto.AppointmentView;
import com.hk.personal.choir_management.dto.appointment.AppointmentAttendanceDto;
import com.hk.personal.choir_management.dto.appointment.AppointmentAttendanceRequestDto;
import com.hk.personal.choir_management.dto.attendance.MemberAttendanceDto;
import com.hk.personal.choir_management.dto.member.*;
import com.hk.personal.choir_management.entity.Member;
import com.hk.personal.choir_management.entity.Rehearsal;
import com.hk.personal.choir_management.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChoirManagementBusinessService {
    // MEMBERS
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    List<String> getRoles(String username);
    MemberProfileResponse getMemberProfile(String username);

    Page<Member> getAllMembers(Pageable pageable);
    List<MemberAttendanceDto> getAllAttendances();


    Page<Song> getSongs(Pageable pageable);

    // APPOINTMENTS
    Page<AppointmentAttendanceDto> getAppointments(String username, Pageable pageable);
    AppointmentView getAppointment(String type, Long id);
    AppointmentAttendanceDto updateAppointment(AppointmentAttendanceRequestDto appointmentAttendanceRequestDto);

    Rehearsal saveRehearsal(Rehearsal rehearsal);
}
