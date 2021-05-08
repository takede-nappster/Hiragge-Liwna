package com.sprintpay.payments.msreport.web.rest;

import com.sprintpay.payments.msreport.TestApp;
import com.sprintpay.payments.msreport.domain.Match;
import com.sprintpay.payments.msreport.domain.Competition;
import com.sprintpay.payments.msreport.domain.Utilisateur;
import com.sprintpay.payments.msreport.repository.MatchRepository;
import com.sprintpay.payments.msreport.service.MatchService;
import com.sprintpay.payments.msreport.service.dto.MatchDTO;
import com.sprintpay.payments.msreport.service.mapper.MatchMapper;
import com.sprintpay.payments.msreport.service.dto.MatchCriteria;
import com.sprintpay.payments.msreport.service.MatchQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MatchResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MatchResourceIT {

    private static final LocalDate DEFAULT_DATE_MATCH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MATCH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_MATCH = LocalDate.ofEpochDay(-1L);

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchMapper matchMapper;

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchQueryService matchQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchMockMvc;

    private Match match;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Match createEntity(EntityManager em) {
        Match match = new Match()
            .dateMatch(DEFAULT_DATE_MATCH);
        return match;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Match createUpdatedEntity(EntityManager em) {
        Match match = new Match()
            .dateMatch(UPDATED_DATE_MATCH);
        return match;
    }

    @BeforeEach
    public void initTest() {
        match = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatch() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();
        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);
        restMatchMockMvc.perform(post("/api/matches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isCreated());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate + 1);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getDateMatch()).isEqualTo(DEFAULT_DATE_MATCH);
    }

    @Test
    @Transactional
    public void createMatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();

        // Create the Match with an existing ID
        match.setId(1L);
        MatchDTO matchDTO = matchMapper.toDto(match);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchMockMvc.perform(post("/api/matches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMatches() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList
        restMatchMockMvc.perform(get("/api/matches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateMatch").value(hasItem(DEFAULT_DATE_MATCH.toString())));
    }
    
    @Test
    @Transactional
    public void getMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", match.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(match.getId().intValue()))
            .andExpect(jsonPath("$.dateMatch").value(DEFAULT_DATE_MATCH.toString()));
    }


    @Test
    @Transactional
    public void getMatchesByIdFiltering() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        Long id = match.getId();

        defaultMatchShouldBeFound("id.equals=" + id);
        defaultMatchShouldNotBeFound("id.notEquals=" + id);

        defaultMatchShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMatchShouldNotBeFound("id.greaterThan=" + id);

        defaultMatchShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMatchShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMatchesByDateMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where dateMatch equals to DEFAULT_DATE_MATCH
        defaultMatchShouldBeFound("dateMatch.equals=" + DEFAULT_DATE_MATCH);

        // Get all the matchList where dateMatch equals to UPDATED_DATE_MATCH
        defaultMatchShouldNotBeFound("dateMatch.equals=" + UPDATED_DATE_MATCH);
    }

    @Test
    @Transactional
    public void getAllMatchesByDateMatchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where dateMatch not equals to DEFAULT_DATE_MATCH
        defaultMatchShouldNotBeFound("dateMatch.notEquals=" + DEFAULT_DATE_MATCH);

        // Get all the matchList where dateMatch not equals to UPDATED_DATE_MATCH
        defaultMatchShouldBeFound("dateMatch.notEquals=" + UPDATED_DATE_MATCH);
    }

    @Test
    @Transactional
    public void getAllMatchesByDateMatchIsInShouldWork() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where dateMatch in DEFAULT_DATE_MATCH or UPDATED_DATE_MATCH
        defaultMatchShouldBeFound("dateMatch.in=" + DEFAULT_DATE_MATCH + "," + UPDATED_DATE_MATCH);

        // Get all the matchList where dateMatch equals to UPDATED_DATE_MATCH
        defaultMatchShouldNotBeFound("dateMatch.in=" + UPDATED_DATE_MATCH);
    }

    @Test
    @Transactional
    public void getAllMatchesByDateMatchIsNullOrNotNull() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where dateMatch is not null
        defaultMatchShouldBeFound("dateMatch.specified=true");

        // Get all the matchList where dateMatch is null
        defaultMatchShouldNotBeFound("dateMatch.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatchesByDateMatchIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where dateMatch is greater than or equal to DEFAULT_DATE_MATCH
        defaultMatchShouldBeFound("dateMatch.greaterThanOrEqual=" + DEFAULT_DATE_MATCH);

        // Get all the matchList where dateMatch is greater than or equal to UPDATED_DATE_MATCH
        defaultMatchShouldNotBeFound("dateMatch.greaterThanOrEqual=" + UPDATED_DATE_MATCH);
    }

    @Test
    @Transactional
    public void getAllMatchesByDateMatchIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where dateMatch is less than or equal to DEFAULT_DATE_MATCH
        defaultMatchShouldBeFound("dateMatch.lessThanOrEqual=" + DEFAULT_DATE_MATCH);

        // Get all the matchList where dateMatch is less than or equal to SMALLER_DATE_MATCH
        defaultMatchShouldNotBeFound("dateMatch.lessThanOrEqual=" + SMALLER_DATE_MATCH);
    }

    @Test
    @Transactional
    public void getAllMatchesByDateMatchIsLessThanSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where dateMatch is less than DEFAULT_DATE_MATCH
        defaultMatchShouldNotBeFound("dateMatch.lessThan=" + DEFAULT_DATE_MATCH);

        // Get all the matchList where dateMatch is less than UPDATED_DATE_MATCH
        defaultMatchShouldBeFound("dateMatch.lessThan=" + UPDATED_DATE_MATCH);
    }

    @Test
    @Transactional
    public void getAllMatchesByDateMatchIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList where dateMatch is greater than DEFAULT_DATE_MATCH
        defaultMatchShouldNotBeFound("dateMatch.greaterThan=" + DEFAULT_DATE_MATCH);

        // Get all the matchList where dateMatch is greater than SMALLER_DATE_MATCH
        defaultMatchShouldBeFound("dateMatch.greaterThan=" + SMALLER_DATE_MATCH);
    }


    @Test
    @Transactional
    public void getAllMatchesByCompetitionIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);
        Competition competition = CompetitionResourceIT.createEntity(em);
        em.persist(competition);
        em.flush();
        match.setCompetition(competition);
        matchRepository.saveAndFlush(match);
        Long competitionId = competition.getId();

        // Get all the matchList where competition equals to competitionId
        defaultMatchShouldBeFound("competitionId.equals=" + competitionId);

        // Get all the matchList where competition equals to competitionId + 1
        defaultMatchShouldNotBeFound("competitionId.equals=" + (competitionId + 1));
    }


    @Test
    @Transactional
    public void getAllMatchesByUtilisateurIsEqualToSomething() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);
        Utilisateur utilisateur = UtilisateurResourceIT.createEntity(em);
        em.persist(utilisateur);
        em.flush();
        match.addUtilisateur(utilisateur);
        matchRepository.saveAndFlush(match);
        Long utilisateurId = utilisateur.getId();

        // Get all the matchList where utilisateur equals to utilisateurId
        defaultMatchShouldBeFound("utilisateurId.equals=" + utilisateurId);

        // Get all the matchList where utilisateur equals to utilisateurId + 1
        defaultMatchShouldNotBeFound("utilisateurId.equals=" + (utilisateurId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMatchShouldBeFound(String filter) throws Exception {
        restMatchMockMvc.perform(get("/api/matches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateMatch").value(hasItem(DEFAULT_DATE_MATCH.toString())));

        // Check, that the count call also returns 1
        restMatchMockMvc.perform(get("/api/matches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMatchShouldNotBeFound(String filter) throws Exception {
        restMatchMockMvc.perform(get("/api/matches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMatchMockMvc.perform(get("/api/matches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMatch() throws Exception {
        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Update the match
        Match updatedMatch = matchRepository.findById(match.getId()).get();
        // Disconnect from session so that the updates on updatedMatch are not directly saved in db
        em.detach(updatedMatch);
        updatedMatch
            .dateMatch(UPDATED_DATE_MATCH);
        MatchDTO matchDTO = matchMapper.toDto(updatedMatch);

        restMatchMockMvc.perform(put("/api/matches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isOk());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getDateMatch()).isEqualTo(UPDATED_DATE_MATCH);
    }

    @Test
    @Transactional
    public void updateNonExistingMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Create the Match
        MatchDTO matchDTO = matchMapper.toDto(match);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchMockMvc.perform(put("/api/matches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        int databaseSizeBeforeDelete = matchRepository.findAll().size();

        // Delete the match
        restMatchMockMvc.perform(delete("/api/matches/{id}", match.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
