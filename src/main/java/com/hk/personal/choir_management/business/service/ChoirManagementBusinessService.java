package com.hk.personal.choir_management.business.service;


import com.hk.personal.choir_management.dto.attendance.MemberAttendanceDto;
import com.hk.personal.choir_management.dto.member.*;
import com.hk.personal.choir_management.entity.Member;
import com.hk.personal.choir_management.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChoirManagementBusinessService {
    // MEMBERS
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    MemberProfileResponse getMemberProfile(String username);

    Page<Member> findAllMembers(Pageable pageable);
    List<MemberAttendanceDto> findAllAttendances();


    Page<Song> findAllSongs(Pageable pageable);
}
