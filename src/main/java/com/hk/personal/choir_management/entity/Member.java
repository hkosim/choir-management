package com.hk.personal.choir_management.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "members",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        }
)
public class Member {
    @Id
    @Column(nullable = false, length = 50)
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(68)")
    private String password;

    private String name;
    private String email;
    private String phone;
    private String voicePart;
    private LocalDate joinDate;
    private boolean active;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private Set<Authority> authorities = new HashSet<>();

    public Member() {
    }

    public Member(String username, String password, String name, String email, String phone, String voicePart, LocalDate joinDate, boolean active) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.voicePart = voicePart;
        this.joinDate = joinDate;
        this.active = active;
        this.authorities = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVoicePart() {
        return voicePart;
    }

    public void setVoicePart(String voicePart) {
        this.voicePart = voicePart;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Member{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", voicePart='" + voicePart + '\'' +
                ", joinDate=" + joinDate +
                ", active=" + active +
                '}';
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}