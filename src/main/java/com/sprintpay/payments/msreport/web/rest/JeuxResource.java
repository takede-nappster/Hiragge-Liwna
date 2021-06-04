package com.sprintpay.payments.msreport.web.rest;

import com.sprintpay.payments.msreport.service.JeuxService;
import com.sprintpay.payments.msreport.web.rest.errors.BadRequestAlertException;
import com.sprintpay.payments.msreport.service.dto.JeuxDTO;
import com.sprintpay.payments.msreport.service.dto.JeuxCriteria;
import com.sprintpay.payments.msreport.service.JeuxQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sprintpay.payments.msreport.domain.Jeux}.
 */
@RestController
@RequestMapping("/api")
public class JeuxResource {

    private final Logger log = LoggerFactory.getLogger(JeuxResource.class);

    private static final String ENTITY_NAME = "jeux";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JeuxService jeuxService;

    private final JeuxQueryService jeuxQueryService;

    public JeuxResource(JeuxService jeuxService, JeuxQueryService jeuxQueryService) {
        this.jeuxService = jeuxService;
        this.jeuxQueryService = jeuxQueryService;
    }

    /**
     * {@code POST  /jeuxes} : Create a new jeux.
     *
     * @param jeuxDTO the jeuxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jeuxDTO, or with status {@code 400 (Bad Request)} if the jeux has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jeuxes")
    public ResponseEntity<JeuxDTO> createJeux(@Valid @RequestBody JeuxDTO jeuxDTO) throws URISyntaxException {
        log.debug("REST request to save Jeux : {}", jeuxDTO);
        if (jeuxDTO.getId() != null) {
            throw new BadRequestAlertException("A new jeux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JeuxDTO result = jeuxService.save(jeuxDTO);
        return ResponseEntity.created(new URI("/api/jeuxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jeuxes} : Updates an existing jeux.
     *
     * @param jeuxDTO the jeuxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jeuxDTO,
     * or with status {@code 400 (Bad Request)} if the jeuxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jeuxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jeuxes")
    public ResponseEntity<JeuxDTO> updateJeux(@Valid @RequestBody JeuxDTO jeuxDTO) throws URISyntaxException {
        log.debug("REST request to update Jeux : {}", jeuxDTO);
        if (jeuxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JeuxDTO result = jeuxService.save(jeuxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jeuxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jeuxes} : get all the jeuxes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jeuxes in body.
     */
    @GetMapping("/jeuxes")
    public ResponseEntity<List<JeuxDTO>> getAllJeuxes(JeuxCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Jeuxes by criteria: {}", criteria);
        Page<JeuxDTO> page = jeuxQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jeuxes/count} : count all the jeuxes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/jeuxes/count")
    public ResponseEntity<Long> countJeuxes(JeuxCriteria criteria) {
        log.debug("REST request to count Jeuxes by criteria: {}", criteria);
        return ResponseEntity.ok().body(jeuxQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /jeuxes/:id} : get the "id" jeux.
     *
     * @param id the id of the jeuxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jeuxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jeuxes/{id}")
    public ResponseEntity<JeuxDTO> getJeux(@PathVariable Long id) {
        log.debug("REST request to get Jeux : {}", id);
        Optional<JeuxDTO> jeuxDTO = jeuxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jeuxDTO);
    }

    /**
     * {@code DELETE  /jeuxes/:id} : delete the "id" jeux.
     *
     * @param id the id of the jeuxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jeuxes/{id}")
    public ResponseEntity<Void> deleteJeux(@PathVariable Long id) {
        log.debug("REST request to delete Jeux : {}", id);
        jeuxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
