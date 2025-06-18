package com.hk.personal.choir_management.repository;

import com.hk.personal.choir_management.entity.Member;
import com.hk.personal.choir_management.entity.Rehearsal;
import com.hk.personal.choir_management.entity.RehearsalAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RehearsalAttendanceRepository extends JpaRepository<RehearsalAttendance, Long> {

    List<RehearsalAttendance> findByMemberUsername(String username);

    // Fetch all future rehearsals with attendance
    List<RehearsalAttendance> findByMemberUsernameAndRehearsalDateAfter(
            String username,
            LocalDate date);

    Optional<RehearsalAttendance> findByMemberAndRehearsal(Member member, Rehearsal rehearsal);
}
