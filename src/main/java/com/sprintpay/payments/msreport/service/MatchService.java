package com.sprintpay.payments.msreport.service;

import com.sprintpay.payments.msreport.domain.Match;
import com.sprintpay.payments.msreport.repository.MatchRepository;
import com.sprintpay.payments.msreport.service.dto.MatchDTO;
import com.sprintpay.payments.msreport.service.mapper.MatchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Match}.
 */
@Service
@Transactional
public class MatchService {

    private final Logger log = LoggerFactory.getLogger(MatchService.class);

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    public MatchService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    /**
     * Save a match.
     *
     * @param matchDTO the entity to save.
     * @return the persisted entity.
     */
    public MatchDTO save(MatchDTO matchDTO) {
        log.debug("Request to save Match : {}", matchDTO);
        Match match = matchMapper.toEntity(matchDTO);
        match = matchRepository.save(match);
        return matchMapper.toDto(match);
    }

    /**
     * Get all the matches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Matches");
        return matchRepository.findAll(pageable)
            .map(matchMapper::toDto);
    }


    /**
     * Get one match by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MatchDTO> findOne(Long id) {
        log.debug("Request to get Match : {}", id);
        return matchRepository.findById(id)
            .map(matchMapper::toDto);
    }

    /**
     * Delete the match by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Match : {}", id);
        matchRepository.deleteById(id);
    }
}
