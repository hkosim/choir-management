package com.hk.personal.choir_management.service.impl;

import com.hk.personal.choir_management.dto.member.LoginRequest;
import com.hk.personal.choir_management.dto.member.LoginResponse;
import com.hk.personal.choir_management.dto.member.RegisterRequest;
import com.hk.personal.choir_management.entity.Authority;
import com.hk.personal.choir_management.entity.Member;
import com.hk.personal.choir_management.repository.MemberRepository;
import com.hk.personal.choir_management.service.MemberService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Page<Member> findAll(Pageable pageable) {
        return this.memberRepository.findAll(pageable);
    }

    @Override
    public List<Member> findAll() {
        return this.memberRepository.findAll();
    }

    @Override
    public Member findByUsername(String username) {
        Optional<Member> _member = memberRepository.findById(username);
        if (_member.isPresent()) {
            return _member.get();
        }
        throw new RuntimeException();
    }

    @Override
    public Member register(RegisterRequest registerRequest) {
        if (memberRepository.existsById(registerRequest.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.rawPassword());
        Member member = new Member();
        member.setUsername(registerRequest.username());
        member.setPassword(encodedPassword);
        member.setName(registerRequest.name());
        member.setEmail(registerRequest.email());
        member.setPhone(registerRequest.phone());
        member.setVoicePart(registerRequest.voicePart());
        member.setJoinDate(registerRequest.joinDate());
        member.setActive(true);

        Authority authority = new Authority(registerRequest.username(), "ROLE_USER");
        member.setAuthorities(Set.of(authority));

        return memberRepository.save(member);
    }

    @Override
    public Member login(LoginRequest loginRequest) {
        Optional<Member> member_ = memberRepository.findById(loginRequest.username());
        if (member_.isEmpty()) {
            throw new BadCredentialsException("Username not found");
        }
        Member member = member_.get();

        if (!member.isActive()) {
            throw new BadCredentialsException("User not active");
        }

        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        return member;
    }

}
