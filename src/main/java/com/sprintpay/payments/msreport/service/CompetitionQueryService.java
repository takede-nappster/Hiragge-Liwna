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

import com.sprintpay.payments.msreport.domain.Competition;
import com.sprintpay.payments.msreport.domain.*; // for static metamodels
import com.sprintpay.payments.msreport.repository.CompetitionRepository;
import com.sprintpay.payments.msreport.service.dto.CompetitionCriteria;
import com.sprintpay.payments.msreport.service.dto.CompetitionDTO;
import com.sprintpay.payments.msreport.service.mapper.CompetitionMapper;

/**
 * Service for executing complex queries for {@link Competition} entities in the database.
 * The main input is a {@link CompetitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompetitionDTO} or a {@link Page} of {@link CompetitionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompetitionQueryService extends QueryService<Competition> {

    private final Logger log = LoggerFactory.getLogger(CompetitionQueryService.class);

    private final CompetitionRepository competitionRepository;

    private final CompetitionMapper competitionMapper;

    public CompetitionQueryService(CompetitionRepository competitionRepository, CompetitionMapper competitionMapper) {
        this.competitionRepository = competitionRepository;
        this.competitionMapper = competitionMapper;
    }

    /**
     * Return a {@link List} of {@link CompetitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompetitionDTO> findByCriteria(CompetitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Competition> specification = createSpecification(criteria);
        return competitionMapper.toDto(competitionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompetitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompetitionDTO> findByCriteria(CompetitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Competition> specification = createSpecification(criteria);
        return competitionRepository.findAll(specification, page)
            .map(competitionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompetitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Competition> specification = createSpecification(criteria);
        return competitionRepository.count(specification);
    }

    /**
     * Function to convert {@link CompetitionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Competition> createSpecification(CompetitionCriteria criteria) {
        Specification<Competition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Competition_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Competition_.nom));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Competition_.description));
            }
            if (criteria.getDateDebut() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDebut(), Competition_.dateDebut));
            }
            if (criteria.getDateFin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFin(), Competition_.dateFin));
            }
            if (criteria.getNombreParticipant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNombreParticipant(), Competition_.nombreParticipant));
            }
            if (criteria.getJeuxId() != null) {
                specification = specification.and(buildSpecification(criteria.getJeuxId(),
                    root -> root.join(Competition_.jeuxes, JoinType.LEFT).get(Jeux_.id)));
            }
            if (criteria.getMatchId() != null) {
                specification = specification.and(buildSpecification(criteria.getMatchId(),
                    root -> root.join(Competition_.matches, JoinType.LEFT).get(Match_.id)));
            }
        }
        return specification;
    }
}
