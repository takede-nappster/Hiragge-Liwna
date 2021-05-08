package com.sprintpay.payments.msreport.repository;

import com.sprintpay.payments.msreport.domain.Jeux;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Jeux entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JeuxRepository extends JpaRepository<Jeux, Long>, JpaSpecificationExecutor<Jeux> {
}
