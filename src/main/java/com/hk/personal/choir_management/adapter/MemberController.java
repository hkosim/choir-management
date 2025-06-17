package com.hk.personal.choir_management.adapter;

import com.hk.personal.choir_management.business.service.ChoirManagementBusinessService;
import com.hk.personal.choir_management.dto.member.*;
import com.hk.personal.choir_management.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final ChoirManagementBusinessService choirManagementBusinessService;

    public MemberController(ChoirManagementBusinessService choirManagementBusinessService) {
        this.choirManagementBusinessService = choirManagementBusinessService;
    }

    // REGISTER A MEMBER
    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('ADMIN')")
    public RegisterResponse register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return choirManagementBusinessService.register(registerRequest);
    }

    // LOGIN
    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest
    ) {
        return choirManagementBusinessService.login(loginRequest);
    }

    // Get User Result By Id
    @GetMapping(value = "/profile/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public MemberProfileResponse getUserResultById(
            @PathVariable String username
    ) {
        return choirManagementBusinessService.getMemberProfile(username);
    }

    // GET ALL MEMBERS
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Member> getAllMembers(
            @PageableDefault(sort = "username") Pageable pageable
    ) {
        return choirManagementBusinessService.findAllMembers(pageable);
    }



}
