package com.hk.personal.choir_management.service;

import com.hk.personal.choir_management.model.dto.member.LoginRequest;
import com.hk.personal.choir_management.model.dto.member.RegisterRequest;
import com.hk.personal.choir_management.model.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    Page<Member> findAll(Pageable pageable);

    List<Member> findAll();
    Member findByUsername(String username);
    List<String> findRoles(String username);

    Member register(RegisterRequest registerRequest);
    Member login(LoginRequest loginRequest);
}
