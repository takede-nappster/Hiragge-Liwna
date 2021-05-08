package com.sprintpay.payments.msreport.web.rest;

import com.sprintpay.payments.msreport.service.MatchService;
import com.sprintpay.payments.msreport.web.rest.errors.BadRequestAlertException;
import com.sprintpay.payments.msreport.service.dto.MatchDTO;
import com.sprintpay.payments.msreport.service.dto.MatchCriteria;
import com.sprintpay.payments.msreport.service.MatchQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sprintpay.payments.msreport.domain.Match}.
 */
@RestController
@RequestMapping("/api")
public class MatchResource {

    private final Logger log = LoggerFactory.getLogger(MatchResource.class);

    private static final String ENTITY_NAME = "match";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatchService matchService;

    private final MatchQueryService matchQueryService;

    public MatchResource(MatchService matchService, MatchQueryService matchQueryService) {
        this.matchService = matchService;
        this.matchQueryService = matchQueryService;
    }

    /**
     * {@code POST  /matches} : Create a new match.
     *
     * @param matchDTO the matchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matchDTO, or with status {@code 400 (Bad Request)} if the match has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matches")
    public ResponseEntity<MatchDTO> createMatch(@RequestBody MatchDTO matchDTO) throws URISyntaxException {
        log.debug("REST request to save Match : {}", matchDTO);
        if (matchDTO.getId() != null) {
            throw new BadRequestAlertException("A new match cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchDTO result = matchService.save(matchDTO);
        return ResponseEntity.created(new URI("/api/matches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matches} : Updates an existing match.
     *
     * @param matchDTO the matchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matchDTO,
     * or with status {@code 400 (Bad Request)} if the matchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matches")
    public ResponseEntity<MatchDTO> updateMatch(@RequestBody MatchDTO matchDTO) throws URISyntaxException {
        log.debug("REST request to update Match : {}", matchDTO);
        if (matchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchDTO result = matchService.save(matchDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /matches} : get all the matches.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matches in body.
     */
    @GetMapping("/matches")
    public ResponseEntity<List<MatchDTO>> getAllMatches(MatchCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Matches by criteria: {}", criteria);
        Page<MatchDTO> page = matchQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matches/count} : count all the matches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/matches/count")
    public ResponseEntity<Long> countMatches(MatchCriteria criteria) {
        log.debug("REST request to count Matches by criteria: {}", criteria);
        return ResponseEntity.ok().body(matchQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /matches/:id} : get the "id" match.
     *
     * @param id the id of the matchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matches/{id}")
    public ResponseEntity<MatchDTO> getMatch(@PathVariable Long id) {
        log.debug("REST request to get Match : {}", id);
        Optional<MatchDTO> matchDTO = matchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchDTO);
    }

    /**
     * {@code DELETE  /matches/:id} : delete the "id" match.
     *
     * @param id the id of the matchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        log.debug("REST request to delete Match : {}", id);
        matchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
