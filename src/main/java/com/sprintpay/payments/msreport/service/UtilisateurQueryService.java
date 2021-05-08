package com.sprintpay.payments.msreport.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.sprintpay.payments.msreport.domain.Utilisateur;
import com.sprintpay.payments.msreport.domain.*; // for static metamodels
import com.sprintpay.payments.msreport.repository.UtilisateurRepository;
import com.sprintpay.payments.msreport.service.dto.UtilisateurCriteria;
import com.sprintpay.payments.msreport.service.dto.UtilisateurDTO;
import com.sprintpay.payments.msreport.service.mapper.UtilisateurMapper;

/**
 * Service for executing complex queries for {@link Utilisateur} entities in the database.
 * The main input is a {@link UtilisateurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UtilisateurDTO} or a {@link Page} of {@link UtilisateurDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UtilisateurQueryService extends QueryService<Utilisateur> {

    private final Logger log = LoggerFactory.getLogger(UtilisateurQueryService.class);

    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurMapper utilisateurMapper;

    public UtilisateurQueryService(UtilisateurRepository utilisateurRepository, UtilisateurMapper utilisateurMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
    }

    /**
     * Return a {@link List} of {@link UtilisateurDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UtilisateurDTO> findByCriteria(UtilisateurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Utilisateur> specification = createSpecification(criteria);
        return utilisateurMapper.toDto(utilisateurRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UtilisateurDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UtilisateurDTO> findByCriteria(UtilisateurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Utilisateur> specification = createSpecification(criteria);
        return utilisateurRepository.findAll(specification, page)
            .map(utilisateurMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UtilisateurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Utilisateur> specification = createSpecification(criteria);
        return utilisateurRepository.count(specification);
    }

    /**
     * Function to convert {@link UtilisateurCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Utilisateur> createSpecification(UtilisateurCriteria criteria) {
        Specification<Utilisateur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Utilisateur_.id));
            }
            if (criteria.getUserType() != null) {
                specification = specification.and(buildSpecification(criteria.getUserType(), Utilisateur_.userType));
            }
            if (criteria.getMatchId() != null) {
                specification = specification.and(buildSpecification(criteria.getMatchId(),
                    root -> root.join(Utilisateur_.matches, JoinType.LEFT).get(Match_.id)));
            }
        }
        return specification;
    }
}
