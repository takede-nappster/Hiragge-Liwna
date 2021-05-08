package com.sprintpay.payments.msreport.service;

import com.sprintpay.payments.msreport.domain.Jeux;
import com.sprintpay.payments.msreport.repository.JeuxRepository;
import com.sprintpay.payments.msreport.service.dto.JeuxDTO;
import com.sprintpay.payments.msreport.service.mapper.JeuxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Jeux}.
 */
@Service
@Transactional
public class JeuxService {

    private final Logger log = LoggerFactory.getLogger(JeuxService.class);

    private final JeuxRepository jeuxRepository;

    private final JeuxMapper jeuxMapper;

    public JeuxService(JeuxRepository jeuxRepository, JeuxMapper jeuxMapper) {
        this.jeuxRepository = jeuxRepository;
        this.jeuxMapper = jeuxMapper;
    }

    /**
     * Save a jeux.
     *
     * @param jeuxDTO the entity to save.
     * @return the persisted entity.
     */
    public JeuxDTO save(JeuxDTO jeuxDTO) {
        log.debug("Request to save Jeux : {}", jeuxDTO);
        Jeux jeux = jeuxMapper.toEntity(jeuxDTO);
        jeux = jeuxRepository.save(jeux);
        return jeuxMapper.toDto(jeux);
    }

    /**
     * Get all the jeuxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JeuxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Jeuxes");
        return jeuxRepository.findAll(pageable)
            .map(jeuxMapper::toDto);
    }


    /**
     * Get one jeux by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JeuxDTO> findOne(Long id) {
        log.debug("Request to get Jeux : {}", id);
        return jeuxRepository.findById(id)
            .map(jeuxMapper::toDto);
    }

    /**
     * Delete the jeux by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Jeux : {}", id);
        jeuxRepository.deleteById(id);
    }
}
