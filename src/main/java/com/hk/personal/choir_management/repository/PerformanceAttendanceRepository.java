package com.hk.personal.choir_management.repository;

import com.hk.personal.choir_management.entity.Member;
import com.hk.personal.choir_management.entity.Performance;
import com.hk.personal.choir_management.entity.PerformanceAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PerformanceAttendanceRepository extends JpaRepository<PerformanceAttendance, Long> {

    List<PerformanceAttendance> findByMemberUsername(String username);

    List<PerformanceAttendance> findByMemberUsernameAndPerformanceDateAfter(
            String username,
            LocalDate date);

    Optional<PerformanceAttendance> findByMemberAndPerformance(Member member, Performance performance);
}
