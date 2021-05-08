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

import com.sprintpay.payments.msreport.domain.Jeux;
import com.sprintpay.payments.msreport.domain.*; // for static metamodels
import com.sprintpay.payments.msreport.repository.JeuxRepository;
import com.sprintpay.payments.msreport.service.dto.JeuxCriteria;
import com.sprintpay.payments.msreport.service.dto.JeuxDTO;
import com.sprintpay.payments.msreport.service.mapper.JeuxMapper;

/**
 * Service for executing complex queries for {@link Jeux} entities in the database.
 * The main input is a {@link JeuxCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JeuxDTO} or a {@link Page} of {@link JeuxDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JeuxQueryService extends QueryService<Jeux> {

    private final Logger log = LoggerFactory.getLogger(JeuxQueryService.class);

    private final JeuxRepository jeuxRepository;

    private final JeuxMapper jeuxMapper;

    public JeuxQueryService(JeuxRepository jeuxRepository, JeuxMapper jeuxMapper) {
        this.jeuxRepository = jeuxRepository;
        this.jeuxMapper = jeuxMapper;
    }

    /**
     * Return a {@link List} of {@link JeuxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JeuxDTO> findByCriteria(JeuxCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Jeux> specification = createSpecification(criteria);
        return jeuxMapper.toDto(jeuxRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JeuxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JeuxDTO> findByCriteria(JeuxCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Jeux> specification = createSpecification(criteria);
        return jeuxRepository.findAll(specification, page)
            .map(jeuxMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JeuxCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Jeux> specification = createSpecification(criteria);
        return jeuxRepository.count(specification);
    }

    /**
     * Function to convert {@link JeuxCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Jeux> createSpecification(JeuxCriteria criteria) {
        Specification<Jeux> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Jeux_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Jeux_.nom));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Jeux_.description));
            }
            if (criteria.getDateCreation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateCreation(), Jeux_.dateCreation));
            }
            if (criteria.getConcepteur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConcepteur(), Jeux_.concepteur));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), Jeux_.prix));
            }
            if (criteria.getMeilleurScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMeilleurScore(), Jeux_.meilleurScore));
            }
            if (criteria.getLienTelechargement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLienTelechargement(), Jeux_.lienTelechargement));
            }
            if (criteria.getLienJouer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLienJouer(), Jeux_.lienJouer));
            }
            if (criteria.getCompetitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompetitionId(),
                    root -> root.join(Jeux_.competition, JoinType.LEFT).get(Competition_.id)));
            }
        }
        return specification;
    }
}
