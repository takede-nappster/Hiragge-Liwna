package com.sprintpay.payments.msreport.repository;

import com.sprintpay.payments.msreport.domain.Utilisateur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Utilisateur entity.
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>, JpaSpecificationExecutor<Utilisateur> {

    @Query(value = "select distinct utilisateur from Utilisateur utilisateur left join fetch utilisateur.matches",
        countQuery = "select count(distinct utilisateur) from Utilisateur utilisateur")
    Page<Utilisateur> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct utilisateur from Utilisateur utilisateur left join fetch utilisateur.matches")
    List<Utilisateur> findAllWithEagerRelationships();

    @Query("select utilisateur from Utilisateur utilisateur left join fetch utilisateur.matches where utilisateur.id =:id")
    Optional<Utilisateur> findOneWithEagerRelationships(@Param("id") Long id);
}
