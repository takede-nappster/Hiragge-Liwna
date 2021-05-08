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

import com.sprintpay.payments.msreport.domain.Match;
import com.sprintpay.payments.msreport.domain.*; // for static metamodels
import com.sprintpay.payments.msreport.repository.MatchRepository;
import com.sprintpay.payments.msreport.service.dto.MatchCriteria;
import com.sprintpay.payments.msreport.service.dto.MatchDTO;
import com.sprintpay.payments.msreport.service.mapper.MatchMapper;

/**
 * Service for executing complex queries for {@link Match} entities in the database.
 * The main input is a {@link MatchCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MatchDTO} or a {@link Page} of {@link MatchDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MatchQueryService extends QueryService<Match> {

    private final Logger log = LoggerFactory.getLogger(MatchQueryService.class);

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    public MatchQueryService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    /**
     * Return a {@link List} of {@link MatchDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MatchDTO> findByCriteria(MatchCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Match> specification = createSpecification(criteria);
        return matchMapper.toDto(matchRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MatchDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchDTO> findByCriteria(MatchCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Match> specification = createSpecification(criteria);
        return matchRepository.findAll(specification, page)
            .map(matchMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MatchCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Match> specification = createSpecification(criteria);
        return matchRepository.count(specification);
    }

    /**
     * Function to convert {@link MatchCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Match> createSpecification(MatchCriteria criteria) {
        Specification<Match> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Match_.id));
            }
            if (criteria.getDateMatch() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateMatch(), Match_.dateMatch));
            }
            if (criteria.getCompetitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompetitionId(),
                    root -> root.join(Match_.competition, JoinType.LEFT).get(Competition_.id)));
            }
            if (criteria.getUtilisateurId() != null) {
                specification = specification.and(buildSpecification(criteria.getUtilisateurId(),
                    root -> root.join(Match_.utilisateurs, JoinType.LEFT).get(Utilisateur_.id)));
            }
        }
        return specification;
    }
}
