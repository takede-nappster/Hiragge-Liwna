package com.sprintpay.payments.msreport.service;

import com.sprintpay.payments.msreport.domain.Competition;
import com.sprintpay.payments.msreport.repository.CompetitionRepository;
import com.sprintpay.payments.msreport.service.dto.CompetitionDTO;
import com.sprintpay.payments.msreport.service.mapper.CompetitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Competition}.
 */
@Service
@Transactional
public class CompetitionService {

    private final Logger log = LoggerFactory.getLogger(CompetitionService.class);

    private final CompetitionRepository competitionRepository;

    private final CompetitionMapper competitionMapper;

    public CompetitionService(CompetitionRepository competitionRepository, CompetitionMapper competitionMapper) {
        this.competitionRepository = competitionRepository;
        this.competitionMapper = competitionMapper;
    }

    /**
     * Save a competition.
     *
     * @param competitionDTO the entity to save.
     * @return the persisted entity.
     */
    public CompetitionDTO save(CompetitionDTO competitionDTO) {
        log.debug("Request to save Competition : {}", competitionDTO);
        Competition competition = competitionMapper.toEntity(competitionDTO);
        competition = competitionRepository.save(competition);
        return competitionMapper.toDto(competition);
    }

    /**
     * Get all the competitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompetitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Competitions");
        return competitionRepository.findAll(pageable)
            .map(competitionMapper::toDto);
    }


    /**
     * Get one competition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompetitionDTO> findOne(Long id) {
        log.debug("Request to get Competition : {}", id);
        return competitionRepository.findById(id)
            .map(competitionMapper::toDto);
    }

    /**
     * Delete the competition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Competition : {}", id);
        competitionRepository.deleteById(id);
    }
}
