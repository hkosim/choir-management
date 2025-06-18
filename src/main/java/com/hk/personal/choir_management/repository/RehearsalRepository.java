package com.hk.personal.choir_management.repository;

import com.hk.personal.choir_management.entity.Rehearsal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RehearsalRepository extends JpaRepository<Rehearsal, Long> {
}
