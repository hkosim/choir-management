package com.hk.personal.choir_management.business.service.impl;

import com.hk.personal.choir_management.business.service.ChoirManagementBusinessService;
import com.hk.personal.choir_management.dto.attendance.MemberAttendanceDto;
import com.hk.personal.choir_management.dto.member.*;
import com.hk.personal.choir_management.entity.Member;
import com.hk.personal.choir_management.entity.Song;
import com.hk.personal.choir_management.service.AttendanceService;
import com.hk.personal.choir_management.service.MemberService;
import com.hk.personal.choir_management.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoirManagementBusinessServiceImpl implements ChoirManagementBusinessService {

    private final MemberService memberService;
    private final AttendanceService attendanceService;
    private final SongService songService;

    public ChoirManagementBusinessServiceImpl(MemberService memberService, AttendanceService attendanceService, SongService songService) {
        this.memberService = memberService;
        this.attendanceService = attendanceService;
        this.songService = songService;
    }

    // REGISTER
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        Member member = memberService.register(registerRequest);
        return new RegisterResponse(member.getUsername());
    }

    // LOGIN
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberService.login(loginRequest);
        return new LoginResponse(member.getUsername());
    }

    @Override
    public MemberProfileResponse getMemberProfile(String username) {
        Member member = memberService.findByUsername(username);

        return new MemberProfileResponse(
                member.getUsername(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getVoicePart(),
                member.getJoinDate()
        );
    }

    @Override
    public Page<Member> findAllMembers(Pageable pageable) {
        return memberService.findAll(pageable);
    }

    @Override
    public List<MemberAttendanceDto> findAllAttendances() {
        return attendanceService.findAllGroupedByMembers();
    }


    @Override
    public Page<Song> findAllSongs(Pageable pageable) {
        return songService.findAll(pageable);
    }
}
