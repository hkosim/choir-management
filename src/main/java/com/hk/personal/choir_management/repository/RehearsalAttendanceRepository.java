package com.hk.personal.choir_management.repository;

import com.hk.personal.choir_management.entity.RehearsalAttendance;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RehearsalAttendanceRepository extends ListCrudRepository<RehearsalAttendance, Long> {

    List<RehearsalAttendance> findByMemberUsername(String username);
}
