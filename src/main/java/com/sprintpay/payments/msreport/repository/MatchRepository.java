package com.sprintpay.payments.msreport.repository;

import com.sprintpay.payments.msreport.domain.Match;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Match entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchRepository extends JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {
}
