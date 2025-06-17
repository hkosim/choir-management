package com.hk.personal.choir_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rehearsal_attendances")
public class RehearsalAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Rehearsal rehearsal;

    private boolean present;

    public RehearsalAttendance() {
    }

    public RehearsalAttendance(Long id, Member member, Rehearsal rehearsal, boolean present) {
        this.id = id;
        this.member = member;
        this.rehearsal = rehearsal;
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

    public Rehearsal getRehearsal() {
        return rehearsal;
    }

    public void setRehearsal(Rehearsal rehearsal) {
        this.rehearsal = rehearsal;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Override
    public String toString() {
        return "RehearsalAttendance{" +
                "id=" + id +
                ", member=" + member +
                ", rehearsal=" + rehearsal +
                ", present=" + present +
                '}';
    }
}