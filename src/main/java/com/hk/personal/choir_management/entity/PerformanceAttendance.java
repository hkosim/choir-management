package com.hk.personal.choir_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "performance_attendances")
public class PerformanceAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Performance performance;

    private boolean present;

    public PerformanceAttendance() {
    }

    public PerformanceAttendance(Long id, Member member, Performance performance, boolean present) {
        this.id = id;
        this.member = member;
        this.performance = performance;
        this.present = present;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Override
    public String toString() {
        return "PerformanceAttendance{" +
                "performance=" + performance +
                ", id=" + id +
                ", member=" + member +
                ", present=" + present +
                '}';
    }
}