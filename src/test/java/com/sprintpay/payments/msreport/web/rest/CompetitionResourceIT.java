package com.sprintpay.payments.msreport.web.rest;

import com.sprintpay.payments.msreport.TestApp;
import com.sprintpay.payments.msreport.domain.Competition;
import com.sprintpay.payments.msreport.domain.Jeux;
import com.sprintpay.payments.msreport.domain.Match;
import com.sprintpay.payments.msreport.repository.CompetitionRepository;
import com.sprintpay.payments.msreport.service.CompetitionService;
import com.sprintpay.payments.msreport.service.dto.CompetitionDTO;
import com.sprintpay.payments.msreport.service.mapper.CompetitionMapper;
import com.sprintpay.payments.msreport.service.dto.CompetitionCriteria;
import com.sprintpay.payments.msreport.service.CompetitionQueryService;

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
 * Integration tests for the {@link CompetitionResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompetitionResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_DEBUT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_FIN = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_NOMBRE_PARTICIPANT = 1;
    private static final Integer UPDATED_NOMBRE_PARTICIPANT = 2;
    private static final Integer SMALLER_NOMBRE_PARTICIPANT = 1 - 1;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private CompetitionQueryService competitionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitionMockMvc;

    private Competition competition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competition createEntity(EntityManager em) {
        Competition competition = new Competition()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .nombreParticipant(DEFAULT_NOMBRE_PARTICIPANT);
        return competition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competition createUpdatedEntity(EntityManager em) {
        Competition competition = new Competition()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nombreParticipant(UPDATED_NOMBRE_PARTICIPANT);
        return competition;
    }

    @BeforeEach
    public void initTest() {
        competition = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetition() throws Exception {
        int databaseSizeBeforeCreate = competitionRepository.findAll().size();
        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);
        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isCreated());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeCreate + 1);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCompetition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetition.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testCompetition.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testCompetition.getNombreParticipant()).isEqualTo(DEFAULT_NOMBRE_PARTICIPANT);
    }

    @Test
    @Transactional
    public void createCompetitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitionRepository.findAll().size();

        // Create the Competition with an existing ID
        competition.setId(1L);
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitionMockMvc.perform(post("/api/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompetitions() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList
        restCompetitionMockMvc.perform(get("/api/competitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competition.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].nombreParticipant").value(hasItem(DEFAULT_NOMBRE_PARTICIPANT)));
    }
    
    @Test
    @Transactional
    public void getCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get the competition
        restCompetitionMockMvc.perform(get("/api/competitions/{id}", competition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competition.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.nombreParticipant").value(DEFAULT_NOMBRE_PARTICIPANT));
    }


    @Test
    @Transactional
    public void getCompetitionsByIdFiltering() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        Long id = competition.getId();

        defaultCompetitionShouldBeFound("id.equals=" + id);
        defaultCompetitionShouldNotBeFound("id.notEquals=" + id);

        defaultCompetitionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompetitionShouldNotBeFound("id.greaterThan=" + id);

        defaultCompetitionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompetitionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompetitionsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nom equals to DEFAULT_NOM
        defaultCompetitionShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the competitionList where nom equals to UPDATED_NOM
        defaultCompetitionShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nom not equals to DEFAULT_NOM
        defaultCompetitionShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the competitionList where nom not equals to UPDATED_NOM
        defaultCompetitionShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultCompetitionShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the competitionList where nom equals to UPDATED_NOM
        defaultCompetitionShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nom is not null
        defaultCompetitionShouldBeFound("nom.specified=true");

        // Get all the competitionList where nom is null
        defaultCompetitionShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompetitionsByNomContainsSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nom contains DEFAULT_NOM
        defaultCompetitionShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the competitionList where nom contains UPDATED_NOM
        defaultCompetitionShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nom does not contain DEFAULT_NOM
        defaultCompetitionShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the competitionList where nom does not contain UPDATED_NOM
        defaultCompetitionShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllCompetitionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where description equals to DEFAULT_DESCRIPTION
        defaultCompetitionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the competitionList where description equals to UPDATED_DESCRIPTION
        defaultCompetitionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where description not equals to DEFAULT_DESCRIPTION
        defaultCompetitionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the competitionList where description not equals to UPDATED_DESCRIPTION
        defaultCompetitionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCompetitionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the competitionList where description equals to UPDATED_DESCRIPTION
        defaultCompetitionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where description is not null
        defaultCompetitionShouldBeFound("description.specified=true");

        // Get all the competitionList where description is null
        defaultCompetitionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompetitionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where description contains DEFAULT_DESCRIPTION
        defaultCompetitionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the competitionList where description contains UPDATED_DESCRIPTION
        defaultCompetitionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where description does not contain DEFAULT_DESCRIPTION
        defaultCompetitionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the competitionList where description does not contain UPDATED_DESCRIPTION
        defaultCompetitionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCompetitionsByDateDebutIsEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateDebut equals to DEFAULT_DATE_DEBUT
        defaultCompetitionShouldBeFound("dateDebut.equals=" + DEFAULT_DATE_DEBUT);

        // Get all the competitionList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultCompetitionShouldNotBeFound("dateDebut.equals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateDebutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateDebut not equals to DEFAULT_DATE_DEBUT
        defaultCompetitionShouldNotBeFound("dateDebut.notEquals=" + DEFAULT_DATE_DEBUT);

        // Get all the competitionList where dateDebut not equals to UPDATED_DATE_DEBUT
        defaultCompetitionShouldBeFound("dateDebut.notEquals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateDebutIsInShouldWork() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateDebut in DEFAULT_DATE_DEBUT or UPDATED_DATE_DEBUT
        defaultCompetitionShouldBeFound("dateDebut.in=" + DEFAULT_DATE_DEBUT + "," + UPDATED_DATE_DEBUT);

        // Get all the competitionList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultCompetitionShouldNotBeFound("dateDebut.in=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateDebutIsNullOrNotNull() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateDebut is not null
        defaultCompetitionShouldBeFound("dateDebut.specified=true");

        // Get all the competitionList where dateDebut is null
        defaultCompetitionShouldNotBeFound("dateDebut.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateDebutIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateDebut is greater than or equal to DEFAULT_DATE_DEBUT
        defaultCompetitionShouldBeFound("dateDebut.greaterThanOrEqual=" + DEFAULT_DATE_DEBUT);

        // Get all the competitionList where dateDebut is greater than or equal to UPDATED_DATE_DEBUT
        defaultCompetitionShouldNotBeFound("dateDebut.greaterThanOrEqual=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateDebutIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateDebut is less than or equal to DEFAULT_DATE_DEBUT
        defaultCompetitionShouldBeFound("dateDebut.lessThanOrEqual=" + DEFAULT_DATE_DEBUT);

        // Get all the competitionList where dateDebut is less than or equal to SMALLER_DATE_DEBUT
        defaultCompetitionShouldNotBeFound("dateDebut.lessThanOrEqual=" + SMALLER_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateDebutIsLessThanSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateDebut is less than DEFAULT_DATE_DEBUT
        defaultCompetitionShouldNotBeFound("dateDebut.lessThan=" + DEFAULT_DATE_DEBUT);

        // Get all the competitionList where dateDebut is less than UPDATED_DATE_DEBUT
        defaultCompetitionShouldBeFound("dateDebut.lessThan=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateDebutIsGreaterThanSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateDebut is greater than DEFAULT_DATE_DEBUT
        defaultCompetitionShouldNotBeFound("dateDebut.greaterThan=" + DEFAULT_DATE_DEBUT);

        // Get all the competitionList where dateDebut is greater than SMALLER_DATE_DEBUT
        defaultCompetitionShouldBeFound("dateDebut.greaterThan=" + SMALLER_DATE_DEBUT);
    }


    @Test
    @Transactional
    public void getAllCompetitionsByDateFinIsEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateFin equals to DEFAULT_DATE_FIN
        defaultCompetitionShouldBeFound("dateFin.equals=" + DEFAULT_DATE_FIN);

        // Get all the competitionList where dateFin equals to UPDATED_DATE_FIN
        defaultCompetitionShouldNotBeFound("dateFin.equals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateFinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateFin not equals to DEFAULT_DATE_FIN
        defaultCompetitionShouldNotBeFound("dateFin.notEquals=" + DEFAULT_DATE_FIN);

        // Get all the competitionList where dateFin not equals to UPDATED_DATE_FIN
        defaultCompetitionShouldBeFound("dateFin.notEquals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateFinIsInShouldWork() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateFin in DEFAULT_DATE_FIN or UPDATED_DATE_FIN
        defaultCompetitionShouldBeFound("dateFin.in=" + DEFAULT_DATE_FIN + "," + UPDATED_DATE_FIN);

        // Get all the competitionList where dateFin equals to UPDATED_DATE_FIN
        defaultCompetitionShouldNotBeFound("dateFin.in=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateFin is not null
        defaultCompetitionShouldBeFound("dateFin.specified=true");

        // Get all the competitionList where dateFin is null
        defaultCompetitionShouldNotBeFound("dateFin.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateFinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateFin is greater than or equal to DEFAULT_DATE_FIN
        defaultCompetitionShouldBeFound("dateFin.greaterThanOrEqual=" + DEFAULT_DATE_FIN);

        // Get all the competitionList where dateFin is greater than or equal to UPDATED_DATE_FIN
        defaultCompetitionShouldNotBeFound("dateFin.greaterThanOrEqual=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateFinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateFin is less than or equal to DEFAULT_DATE_FIN
        defaultCompetitionShouldBeFound("dateFin.lessThanOrEqual=" + DEFAULT_DATE_FIN);

        // Get all the competitionList where dateFin is less than or equal to SMALLER_DATE_FIN
        defaultCompetitionShouldNotBeFound("dateFin.lessThanOrEqual=" + SMALLER_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateFinIsLessThanSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateFin is less than DEFAULT_DATE_FIN
        defaultCompetitionShouldNotBeFound("dateFin.lessThan=" + DEFAULT_DATE_FIN);

        // Get all the competitionList where dateFin is less than UPDATED_DATE_FIN
        defaultCompetitionShouldBeFound("dateFin.lessThan=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByDateFinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where dateFin is greater than DEFAULT_DATE_FIN
        defaultCompetitionShouldNotBeFound("dateFin.greaterThan=" + DEFAULT_DATE_FIN);

        // Get all the competitionList where dateFin is greater than SMALLER_DATE_FIN
        defaultCompetitionShouldBeFound("dateFin.greaterThan=" + SMALLER_DATE_FIN);
    }


    @Test
    @Transactional
    public void getAllCompetitionsByNombreParticipantIsEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nombreParticipant equals to DEFAULT_NOMBRE_PARTICIPANT
        defaultCompetitionShouldBeFound("nombreParticipant.equals=" + DEFAULT_NOMBRE_PARTICIPANT);

        // Get all the competitionList where nombreParticipant equals to UPDATED_NOMBRE_PARTICIPANT
        defaultCompetitionShouldNotBeFound("nombreParticipant.equals=" + UPDATED_NOMBRE_PARTICIPANT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNombreParticipantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nombreParticipant not equals to DEFAULT_NOMBRE_PARTICIPANT
        defaultCompetitionShouldNotBeFound("nombreParticipant.notEquals=" + DEFAULT_NOMBRE_PARTICIPANT);

        // Get all the competitionList where nombreParticipant not equals to UPDATED_NOMBRE_PARTICIPANT
        defaultCompetitionShouldBeFound("nombreParticipant.notEquals=" + UPDATED_NOMBRE_PARTICIPANT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNombreParticipantIsInShouldWork() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nombreParticipant in DEFAULT_NOMBRE_PARTICIPANT or UPDATED_NOMBRE_PARTICIPANT
        defaultCompetitionShouldBeFound("nombreParticipant.in=" + DEFAULT_NOMBRE_PARTICIPANT + "," + UPDATED_NOMBRE_PARTICIPANT);

        // Get all the competitionList where nombreParticipant equals to UPDATED_NOMBRE_PARTICIPANT
        defaultCompetitionShouldNotBeFound("nombreParticipant.in=" + UPDATED_NOMBRE_PARTICIPANT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNombreParticipantIsNullOrNotNull() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nombreParticipant is not null
        defaultCompetitionShouldBeFound("nombreParticipant.specified=true");

        // Get all the competitionList where nombreParticipant is null
        defaultCompetitionShouldNotBeFound("nombreParticipant.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNombreParticipantIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nombreParticipant is greater than or equal to DEFAULT_NOMBRE_PARTICIPANT
        defaultCompetitionShouldBeFound("nombreParticipant.greaterThanOrEqual=" + DEFAULT_NOMBRE_PARTICIPANT);

        // Get all the competitionList where nombreParticipant is greater than or equal to UPDATED_NOMBRE_PARTICIPANT
        defaultCompetitionShouldNotBeFound("nombreParticipant.greaterThanOrEqual=" + UPDATED_NOMBRE_PARTICIPANT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNombreParticipantIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nombreParticipant is less than or equal to DEFAULT_NOMBRE_PARTICIPANT
        defaultCompetitionShouldBeFound("nombreParticipant.lessThanOrEqual=" + DEFAULT_NOMBRE_PARTICIPANT);

        // Get all the competitionList where nombreParticipant is less than or equal to SMALLER_NOMBRE_PARTICIPANT
        defaultCompetitionShouldNotBeFound("nombreParticipant.lessThanOrEqual=" + SMALLER_NOMBRE_PARTICIPANT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNombreParticipantIsLessThanSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nombreParticipant is less than DEFAULT_NOMBRE_PARTICIPANT
        defaultCompetitionShouldNotBeFound("nombreParticipant.lessThan=" + DEFAULT_NOMBRE_PARTICIPANT);

        // Get all the competitionList where nombreParticipant is less than UPDATED_NOMBRE_PARTICIPANT
        defaultCompetitionShouldBeFound("nombreParticipant.lessThan=" + UPDATED_NOMBRE_PARTICIPANT);
    }

    @Test
    @Transactional
    public void getAllCompetitionsByNombreParticipantIsGreaterThanSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        // Get all the competitionList where nombreParticipant is greater than DEFAULT_NOMBRE_PARTICIPANT
        defaultCompetitionShouldNotBeFound("nombreParticipant.greaterThan=" + DEFAULT_NOMBRE_PARTICIPANT);

        // Get all the competitionList where nombreParticipant is greater than SMALLER_NOMBRE_PARTICIPANT
        defaultCompetitionShouldBeFound("nombreParticipant.greaterThan=" + SMALLER_NOMBRE_PARTICIPANT);
    }


    @Test
    @Transactional
    public void getAllCompetitionsByJeuxIsEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);
        Jeux jeux = JeuxResourceIT.createEntity(em);
        em.persist(jeux);
        em.flush();
        competition.addJeux(jeux);
        competitionRepository.saveAndFlush(competition);
        Long jeuxId = jeux.getId();

        // Get all the competitionList where jeux equals to jeuxId
        defaultCompetitionShouldBeFound("jeuxId.equals=" + jeuxId);

        // Get all the competitionList where jeux equals to jeuxId + 1
        defaultCompetitionShouldNotBeFound("jeuxId.equals=" + (jeuxId + 1));
    }


    @Test
    @Transactional
    public void getAllCompetitionsByMatchIsEqualToSomething() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);
        Match match = MatchResourceIT.createEntity(em);
        em.persist(match);
        em.flush();
        competition.addMatch(match);
        competitionRepository.saveAndFlush(competition);
        Long matchId = match.getId();

        // Get all the competitionList where match equals to matchId
        defaultCompetitionShouldBeFound("matchId.equals=" + matchId);

        // Get all the competitionList where match equals to matchId + 1
        defaultCompetitionShouldNotBeFound("matchId.equals=" + (matchId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompetitionShouldBeFound(String filter) throws Exception {
        restCompetitionMockMvc.perform(get("/api/competitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competition.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].nombreParticipant").value(hasItem(DEFAULT_NOMBRE_PARTICIPANT)));

        // Check, that the count call also returns 1
        restCompetitionMockMvc.perform(get("/api/competitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompetitionShouldNotBeFound(String filter) throws Exception {
        restCompetitionMockMvc.perform(get("/api/competitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompetitionMockMvc.perform(get("/api/competitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCompetition() throws Exception {
        // Get the competition
        restCompetitionMockMvc.perform(get("/api/competitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();

        // Update the competition
        Competition updatedCompetition = competitionRepository.findById(competition.getId()).get();
        // Disconnect from session so that the updates on updatedCompetition are not directly saved in db
        em.detach(updatedCompetition);
        updatedCompetition
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .nombreParticipant(UPDATED_NOMBRE_PARTICIPANT);
        CompetitionDTO competitionDTO = competitionMapper.toDto(updatedCompetition);

        restCompetitionMockMvc.perform(put("/api/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isOk());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
        Competition testCompetition = competitionList.get(competitionList.size() - 1);
        assertThat(testCompetition.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCompetition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetition.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testCompetition.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testCompetition.getNombreParticipant()).isEqualTo(UPDATED_NOMBRE_PARTICIPANT);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetition() throws Exception {
        int databaseSizeBeforeUpdate = competitionRepository.findAll().size();

        // Create the Competition
        CompetitionDTO competitionDTO = competitionMapper.toDto(competition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitionMockMvc.perform(put("/api/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competition in the database
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetition() throws Exception {
        // Initialize the database
        competitionRepository.saveAndFlush(competition);

        int databaseSizeBeforeDelete = competitionRepository.findAll().size();

        // Delete the competition
        restCompetitionMockMvc.perform(delete("/api/competitions/{id}", competition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competition> competitionList = competitionRepository.findAll();
        assertThat(competitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
