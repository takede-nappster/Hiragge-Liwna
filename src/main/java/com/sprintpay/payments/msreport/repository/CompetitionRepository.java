package com.sprintpay.payments.msreport.repository;

import com.sprintpay.payments.msreport.domain.Competition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Competition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long>, JpaSpecificationExecutor<Competition> {
}
