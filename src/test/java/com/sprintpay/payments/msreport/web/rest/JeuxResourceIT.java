package com.sprintpay.payments.msreport.web.rest;

import com.sprintpay.payments.msreport.TestApp;
import com.sprintpay.payments.msreport.domain.Jeux;
import com.sprintpay.payments.msreport.domain.Competition;
import com.sprintpay.payments.msreport.repository.JeuxRepository;
import com.sprintpay.payments.msreport.service.JeuxService;
import com.sprintpay.payments.msreport.service.dto.JeuxDTO;
import com.sprintpay.payments.msreport.service.mapper.JeuxMapper;
import com.sprintpay.payments.msreport.service.dto.JeuxCriteria;
import com.sprintpay.payments.msreport.service.JeuxQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JeuxResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JeuxResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_CREATION = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CONCEPTEUR = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPTEUR = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;
    private static final Double SMALLER_PRIX = 1D - 1D;

    private static final Integer DEFAULT_MEILLEUR_SCORE = 1;
    private static final Integer UPDATED_MEILLEUR_SCORE = 2;
    private static final Integer SMALLER_MEILLEUR_SCORE = 1 - 1;

    private static final String DEFAULT_LIEN_JOUER = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_JOUER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SETUP_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SETUP_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SETUP_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SETUP_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private JeuxRepository jeuxRepository;

    @Autowired
    private JeuxMapper jeuxMapper;

    @Autowired
    private JeuxService jeuxService;

    @Autowired
    private JeuxQueryService jeuxQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJeuxMockMvc;

    private Jeux jeux;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jeux createEntity(EntityManager em) {
        Jeux jeux = new Jeux()
            .nom(DEFAULT_NOM)
            .dateCreation(DEFAULT_DATE_CREATION)
            .concepteur(DEFAULT_CONCEPTEUR)
            .prix(DEFAULT_PRIX)
            .meilleurScore(DEFAULT_MEILLEUR_SCORE)
            .lienJouer(DEFAULT_LIEN_JOUER)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .setupFile(DEFAULT_SETUP_FILE)
            .setupFileContentType(DEFAULT_SETUP_FILE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return jeux;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jeux createUpdatedEntity(EntityManager em) {
        Jeux jeux = new Jeux()
            .nom(UPDATED_NOM)
            .dateCreation(UPDATED_DATE_CREATION)
            .concepteur(UPDATED_CONCEPTEUR)
            .prix(UPDATED_PRIX)
            .meilleurScore(UPDATED_MEILLEUR_SCORE)
            .lienJouer(UPDATED_LIEN_JOUER)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .setupFile(UPDATED_SETUP_FILE)
            .setupFileContentType(UPDATED_SETUP_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        return jeux;
    }

    @BeforeEach
    public void initTest() {
        jeux = createEntity(em);
    }

    @Test
    @Transactional
    public void createJeux() throws Exception {
        int databaseSizeBeforeCreate = jeuxRepository.findAll().size();
        // Create the Jeux
        JeuxDTO jeuxDTO = jeuxMapper.toDto(jeux);
        restJeuxMockMvc.perform(post("/api/jeuxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jeuxDTO)))
            .andExpect(status().isCreated());

        // Validate the Jeux in the database
        List<Jeux> jeuxList = jeuxRepository.findAll();
        assertThat(jeuxList).hasSize(databaseSizeBeforeCreate + 1);
        Jeux testJeux = jeuxList.get(jeuxList.size() - 1);
        assertThat(testJeux.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testJeux.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testJeux.getConcepteur()).isEqualTo(DEFAULT_CONCEPTEUR);
        assertThat(testJeux.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testJeux.getMeilleurScore()).isEqualTo(DEFAULT_MEILLEUR_SCORE);
        assertThat(testJeux.getLienJouer()).isEqualTo(DEFAULT_LIEN_JOUER);
        assertThat(testJeux.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testJeux.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testJeux.getSetupFile()).isEqualTo(DEFAULT_SETUP_FILE);
        assertThat(testJeux.getSetupFileContentType()).isEqualTo(DEFAULT_SETUP_FILE_CONTENT_TYPE);
        assertThat(testJeux.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createJeuxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jeuxRepository.findAll().size();

        // Create the Jeux with an existing ID
        jeux.setId(1L);
        JeuxDTO jeuxDTO = jeuxMapper.toDto(jeux);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJeuxMockMvc.perform(post("/api/jeuxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jeuxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jeux in the database
        List<Jeux> jeuxList = jeuxRepository.findAll();
        assertThat(jeuxList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jeuxRepository.findAll().size();
        // set the field null
        jeux.setDescription(null);

        // Create the Jeux, which fails.
        JeuxDTO jeuxDTO = jeuxMapper.toDto(jeux);


        restJeuxMockMvc.perform(post("/api/jeuxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jeuxDTO)))
            .andExpect(status().isBadRequest());

        List<Jeux> jeuxList = jeuxRepository.findAll();
        assertThat(jeuxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJeuxes() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList
        restJeuxMockMvc.perform(get("/api/jeuxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jeux.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].concepteur").value(hasItem(DEFAULT_CONCEPTEUR)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].meilleurScore").value(hasItem(DEFAULT_MEILLEUR_SCORE)))
            .andExpect(jsonPath("$.[*].lienJouer").value(hasItem(DEFAULT_LIEN_JOUER)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].setupFileContentType").value(hasItem(DEFAULT_SETUP_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].setupFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_SETUP_FILE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getJeux() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get the jeux
        restJeuxMockMvc.perform(get("/api/jeuxes/{id}", jeux.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jeux.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.concepteur").value(DEFAULT_CONCEPTEUR))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.meilleurScore").value(DEFAULT_MEILLEUR_SCORE))
            .andExpect(jsonPath("$.lienJouer").value(DEFAULT_LIEN_JOUER))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.setupFileContentType").value(DEFAULT_SETUP_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.setupFile").value(Base64Utils.encodeToString(DEFAULT_SETUP_FILE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getJeuxesByIdFiltering() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        Long id = jeux.getId();

        defaultJeuxShouldBeFound("id.equals=" + id);
        defaultJeuxShouldNotBeFound("id.notEquals=" + id);

        defaultJeuxShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJeuxShouldNotBeFound("id.greaterThan=" + id);

        defaultJeuxShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJeuxShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJeuxesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where nom equals to DEFAULT_NOM
        defaultJeuxShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the jeuxList where nom equals to UPDATED_NOM
        defaultJeuxShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllJeuxesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where nom not equals to DEFAULT_NOM
        defaultJeuxShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the jeuxList where nom not equals to UPDATED_NOM
        defaultJeuxShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllJeuxesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultJeuxShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the jeuxList where nom equals to UPDATED_NOM
        defaultJeuxShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllJeuxesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where nom is not null
        defaultJeuxShouldBeFound("nom.specified=true");

        // Get all the jeuxList where nom is null
        defaultJeuxShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllJeuxesByNomContainsSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where nom contains DEFAULT_NOM
        defaultJeuxShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the jeuxList where nom contains UPDATED_NOM
        defaultJeuxShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllJeuxesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where nom does not contain DEFAULT_NOM
        defaultJeuxShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the jeuxList where nom does not contain UPDATED_NOM
        defaultJeuxShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllJeuxesByDateCreationIsEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where dateCreation equals to DEFAULT_DATE_CREATION
        defaultJeuxShouldBeFound("dateCreation.equals=" + DEFAULT_DATE_CREATION);

        // Get all the jeuxList where dateCreation equals to UPDATED_DATE_CREATION
        defaultJeuxShouldNotBeFound("dateCreation.equals=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDateCreationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where dateCreation not equals to DEFAULT_DATE_CREATION
        defaultJeuxShouldNotBeFound("dateCreation.notEquals=" + DEFAULT_DATE_CREATION);

        // Get all the jeuxList where dateCreation not equals to UPDATED_DATE_CREATION
        defaultJeuxShouldBeFound("dateCreation.notEquals=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDateCreationIsInShouldWork() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where dateCreation in DEFAULT_DATE_CREATION or UPDATED_DATE_CREATION
        defaultJeuxShouldBeFound("dateCreation.in=" + DEFAULT_DATE_CREATION + "," + UPDATED_DATE_CREATION);

        // Get all the jeuxList where dateCreation equals to UPDATED_DATE_CREATION
        defaultJeuxShouldNotBeFound("dateCreation.in=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDateCreationIsNullOrNotNull() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where dateCreation is not null
        defaultJeuxShouldBeFound("dateCreation.specified=true");

        // Get all the jeuxList where dateCreation is null
        defaultJeuxShouldNotBeFound("dateCreation.specified=false");
    }

    @Test
    @Transactional
    public void getAllJeuxesByDateCreationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where dateCreation is greater than or equal to DEFAULT_DATE_CREATION
        defaultJeuxShouldBeFound("dateCreation.greaterThanOrEqual=" + DEFAULT_DATE_CREATION);

        // Get all the jeuxList where dateCreation is greater than or equal to UPDATED_DATE_CREATION
        defaultJeuxShouldNotBeFound("dateCreation.greaterThanOrEqual=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDateCreationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where dateCreation is less than or equal to DEFAULT_DATE_CREATION
        defaultJeuxShouldBeFound("dateCreation.lessThanOrEqual=" + DEFAULT_DATE_CREATION);

        // Get all the jeuxList where dateCreation is less than or equal to SMALLER_DATE_CREATION
        defaultJeuxShouldNotBeFound("dateCreation.lessThanOrEqual=" + SMALLER_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDateCreationIsLessThanSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where dateCreation is less than DEFAULT_DATE_CREATION
        defaultJeuxShouldNotBeFound("dateCreation.lessThan=" + DEFAULT_DATE_CREATION);

        // Get all the jeuxList where dateCreation is less than UPDATED_DATE_CREATION
        defaultJeuxShouldBeFound("dateCreation.lessThan=" + UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDateCreationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where dateCreation is greater than DEFAULT_DATE_CREATION
        defaultJeuxShouldNotBeFound("dateCreation.greaterThan=" + DEFAULT_DATE_CREATION);

        // Get all the jeuxList where dateCreation is greater than SMALLER_DATE_CREATION
        defaultJeuxShouldBeFound("dateCreation.greaterThan=" + SMALLER_DATE_CREATION);
    }


    @Test
    @Transactional
    public void getAllJeuxesByConcepteurIsEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where concepteur equals to DEFAULT_CONCEPTEUR
        defaultJeuxShouldBeFound("concepteur.equals=" + DEFAULT_CONCEPTEUR);

        // Get all the jeuxList where concepteur equals to UPDATED_CONCEPTEUR
        defaultJeuxShouldNotBeFound("concepteur.equals=" + UPDATED_CONCEPTEUR);
    }

    @Test
    @Transactional
    public void getAllJeuxesByConcepteurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where concepteur not equals to DEFAULT_CONCEPTEUR
        defaultJeuxShouldNotBeFound("concepteur.notEquals=" + DEFAULT_CONCEPTEUR);

        // Get all the jeuxList where concepteur not equals to UPDATED_CONCEPTEUR
        defaultJeuxShouldBeFound("concepteur.notEquals=" + UPDATED_CONCEPTEUR);
    }

    @Test
    @Transactional
    public void getAllJeuxesByConcepteurIsInShouldWork() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where concepteur in DEFAULT_CONCEPTEUR or UPDATED_CONCEPTEUR
        defaultJeuxShouldBeFound("concepteur.in=" + DEFAULT_CONCEPTEUR + "," + UPDATED_CONCEPTEUR);

        // Get all the jeuxList where concepteur equals to UPDATED_CONCEPTEUR
        defaultJeuxShouldNotBeFound("concepteur.in=" + UPDATED_CONCEPTEUR);
    }

    @Test
    @Transactional
    public void getAllJeuxesByConcepteurIsNullOrNotNull() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where concepteur is not null
        defaultJeuxShouldBeFound("concepteur.specified=true");

        // Get all the jeuxList where concepteur is null
        defaultJeuxShouldNotBeFound("concepteur.specified=false");
    }
                @Test
    @Transactional
    public void getAllJeuxesByConcepteurContainsSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where concepteur contains DEFAULT_CONCEPTEUR
        defaultJeuxShouldBeFound("concepteur.contains=" + DEFAULT_CONCEPTEUR);

        // Get all the jeuxList where concepteur contains UPDATED_CONCEPTEUR
        defaultJeuxShouldNotBeFound("concepteur.contains=" + UPDATED_CONCEPTEUR);
    }

    @Test
    @Transactional
    public void getAllJeuxesByConcepteurNotContainsSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where concepteur does not contain DEFAULT_CONCEPTEUR
        defaultJeuxShouldNotBeFound("concepteur.doesNotContain=" + DEFAULT_CONCEPTEUR);

        // Get all the jeuxList where concepteur does not contain UPDATED_CONCEPTEUR
        defaultJeuxShouldBeFound("concepteur.doesNotContain=" + UPDATED_CONCEPTEUR);
    }


    @Test
    @Transactional
    public void getAllJeuxesByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where prix equals to DEFAULT_PRIX
        defaultJeuxShouldBeFound("prix.equals=" + DEFAULT_PRIX);

        // Get all the jeuxList where prix equals to UPDATED_PRIX
        defaultJeuxShouldNotBeFound("prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllJeuxesByPrixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where prix not equals to DEFAULT_PRIX
        defaultJeuxShouldNotBeFound("prix.notEquals=" + DEFAULT_PRIX);

        // Get all the jeuxList where prix not equals to UPDATED_PRIX
        defaultJeuxShouldBeFound("prix.notEquals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllJeuxesByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where prix in DEFAULT_PRIX or UPDATED_PRIX
        defaultJeuxShouldBeFound("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX);

        // Get all the jeuxList where prix equals to UPDATED_PRIX
        defaultJeuxShouldNotBeFound("prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllJeuxesByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where prix is not null
        defaultJeuxShouldBeFound("prix.specified=true");

        // Get all the jeuxList where prix is null
        defaultJeuxShouldNotBeFound("prix.specified=false");
    }

    @Test
    @Transactional
    public void getAllJeuxesByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where prix is greater than or equal to DEFAULT_PRIX
        defaultJeuxShouldBeFound("prix.greaterThanOrEqual=" + DEFAULT_PRIX);

        // Get all the jeuxList where prix is greater than or equal to UPDATED_PRIX
        defaultJeuxShouldNotBeFound("prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllJeuxesByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where prix is less than or equal to DEFAULT_PRIX
        defaultJeuxShouldBeFound("prix.lessThanOrEqual=" + DEFAULT_PRIX);

        // Get all the jeuxList where prix is less than or equal to SMALLER_PRIX
        defaultJeuxShouldNotBeFound("prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    public void getAllJeuxesByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where prix is less than DEFAULT_PRIX
        defaultJeuxShouldNotBeFound("prix.lessThan=" + DEFAULT_PRIX);

        // Get all the jeuxList where prix is less than UPDATED_PRIX
        defaultJeuxShouldBeFound("prix.lessThan=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void getAllJeuxesByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where prix is greater than DEFAULT_PRIX
        defaultJeuxShouldNotBeFound("prix.greaterThan=" + DEFAULT_PRIX);

        // Get all the jeuxList where prix is greater than SMALLER_PRIX
        defaultJeuxShouldBeFound("prix.greaterThan=" + SMALLER_PRIX);
    }


    @Test
    @Transactional
    public void getAllJeuxesByMeilleurScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where meilleurScore equals to DEFAULT_MEILLEUR_SCORE
        defaultJeuxShouldBeFound("meilleurScore.equals=" + DEFAULT_MEILLEUR_SCORE);

        // Get all the jeuxList where meilleurScore equals to UPDATED_MEILLEUR_SCORE
        defaultJeuxShouldNotBeFound("meilleurScore.equals=" + UPDATED_MEILLEUR_SCORE);
    }

    @Test
    @Transactional
    public void getAllJeuxesByMeilleurScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where meilleurScore not equals to DEFAULT_MEILLEUR_SCORE
        defaultJeuxShouldNotBeFound("meilleurScore.notEquals=" + DEFAULT_MEILLEUR_SCORE);

        // Get all the jeuxList where meilleurScore not equals to UPDATED_MEILLEUR_SCORE
        defaultJeuxShouldBeFound("meilleurScore.notEquals=" + UPDATED_MEILLEUR_SCORE);
    }

    @Test
    @Transactional
    public void getAllJeuxesByMeilleurScoreIsInShouldWork() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where meilleurScore in DEFAULT_MEILLEUR_SCORE or UPDATED_MEILLEUR_SCORE
        defaultJeuxShouldBeFound("meilleurScore.in=" + DEFAULT_MEILLEUR_SCORE + "," + UPDATED_MEILLEUR_SCORE);

        // Get all the jeuxList where meilleurScore equals to UPDATED_MEILLEUR_SCORE
        defaultJeuxShouldNotBeFound("meilleurScore.in=" + UPDATED_MEILLEUR_SCORE);
    }

    @Test
    @Transactional
    public void getAllJeuxesByMeilleurScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where meilleurScore is not null
        defaultJeuxShouldBeFound("meilleurScore.specified=true");

        // Get all the jeuxList where meilleurScore is null
        defaultJeuxShouldNotBeFound("meilleurScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllJeuxesByMeilleurScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where meilleurScore is greater than or equal to DEFAULT_MEILLEUR_SCORE
        defaultJeuxShouldBeFound("meilleurScore.greaterThanOrEqual=" + DEFAULT_MEILLEUR_SCORE);

        // Get all the jeuxList where meilleurScore is greater than or equal to UPDATED_MEILLEUR_SCORE
        defaultJeuxShouldNotBeFound("meilleurScore.greaterThanOrEqual=" + UPDATED_MEILLEUR_SCORE);
    }

    @Test
    @Transactional
    public void getAllJeuxesByMeilleurScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where meilleurScore is less than or equal to DEFAULT_MEILLEUR_SCORE
        defaultJeuxShouldBeFound("meilleurScore.lessThanOrEqual=" + DEFAULT_MEILLEUR_SCORE);

        // Get all the jeuxList where meilleurScore is less than or equal to SMALLER_MEILLEUR_SCORE
        defaultJeuxShouldNotBeFound("meilleurScore.lessThanOrEqual=" + SMALLER_MEILLEUR_SCORE);
    }

    @Test
    @Transactional
    public void getAllJeuxesByMeilleurScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where meilleurScore is less than DEFAULT_MEILLEUR_SCORE
        defaultJeuxShouldNotBeFound("meilleurScore.lessThan=" + DEFAULT_MEILLEUR_SCORE);

        // Get all the jeuxList where meilleurScore is less than UPDATED_MEILLEUR_SCORE
        defaultJeuxShouldBeFound("meilleurScore.lessThan=" + UPDATED_MEILLEUR_SCORE);
    }

    @Test
    @Transactional
    public void getAllJeuxesByMeilleurScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where meilleurScore is greater than DEFAULT_MEILLEUR_SCORE
        defaultJeuxShouldNotBeFound("meilleurScore.greaterThan=" + DEFAULT_MEILLEUR_SCORE);

        // Get all the jeuxList where meilleurScore is greater than SMALLER_MEILLEUR_SCORE
        defaultJeuxShouldBeFound("meilleurScore.greaterThan=" + SMALLER_MEILLEUR_SCORE);
    }


    @Test
    @Transactional
    public void getAllJeuxesByLienJouerIsEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where lienJouer equals to DEFAULT_LIEN_JOUER
        defaultJeuxShouldBeFound("lienJouer.equals=" + DEFAULT_LIEN_JOUER);

        // Get all the jeuxList where lienJouer equals to UPDATED_LIEN_JOUER
        defaultJeuxShouldNotBeFound("lienJouer.equals=" + UPDATED_LIEN_JOUER);
    }

    @Test
    @Transactional
    public void getAllJeuxesByLienJouerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where lienJouer not equals to DEFAULT_LIEN_JOUER
        defaultJeuxShouldNotBeFound("lienJouer.notEquals=" + DEFAULT_LIEN_JOUER);

        // Get all the jeuxList where lienJouer not equals to UPDATED_LIEN_JOUER
        defaultJeuxShouldBeFound("lienJouer.notEquals=" + UPDATED_LIEN_JOUER);
    }

    @Test
    @Transactional
    public void getAllJeuxesByLienJouerIsInShouldWork() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where lienJouer in DEFAULT_LIEN_JOUER or UPDATED_LIEN_JOUER
        defaultJeuxShouldBeFound("lienJouer.in=" + DEFAULT_LIEN_JOUER + "," + UPDATED_LIEN_JOUER);

        // Get all the jeuxList where lienJouer equals to UPDATED_LIEN_JOUER
        defaultJeuxShouldNotBeFound("lienJouer.in=" + UPDATED_LIEN_JOUER);
    }

    @Test
    @Transactional
    public void getAllJeuxesByLienJouerIsNullOrNotNull() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where lienJouer is not null
        defaultJeuxShouldBeFound("lienJouer.specified=true");

        // Get all the jeuxList where lienJouer is null
        defaultJeuxShouldNotBeFound("lienJouer.specified=false");
    }
                @Test
    @Transactional
    public void getAllJeuxesByLienJouerContainsSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where lienJouer contains DEFAULT_LIEN_JOUER
        defaultJeuxShouldBeFound("lienJouer.contains=" + DEFAULT_LIEN_JOUER);

        // Get all the jeuxList where lienJouer contains UPDATED_LIEN_JOUER
        defaultJeuxShouldNotBeFound("lienJouer.contains=" + UPDATED_LIEN_JOUER);
    }

    @Test
    @Transactional
    public void getAllJeuxesByLienJouerNotContainsSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where lienJouer does not contain DEFAULT_LIEN_JOUER
        defaultJeuxShouldNotBeFound("lienJouer.doesNotContain=" + DEFAULT_LIEN_JOUER);

        // Get all the jeuxList where lienJouer does not contain UPDATED_LIEN_JOUER
        defaultJeuxShouldBeFound("lienJouer.doesNotContain=" + UPDATED_LIEN_JOUER);
    }


    @Test
    @Transactional
    public void getAllJeuxesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where description equals to DEFAULT_DESCRIPTION
        defaultJeuxShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the jeuxList where description equals to UPDATED_DESCRIPTION
        defaultJeuxShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where description not equals to DEFAULT_DESCRIPTION
        defaultJeuxShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the jeuxList where description not equals to UPDATED_DESCRIPTION
        defaultJeuxShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultJeuxShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the jeuxList where description equals to UPDATED_DESCRIPTION
        defaultJeuxShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where description is not null
        defaultJeuxShouldBeFound("description.specified=true");

        // Get all the jeuxList where description is null
        defaultJeuxShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllJeuxesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where description contains DEFAULT_DESCRIPTION
        defaultJeuxShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the jeuxList where description contains UPDATED_DESCRIPTION
        defaultJeuxShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllJeuxesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        // Get all the jeuxList where description does not contain DEFAULT_DESCRIPTION
        defaultJeuxShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the jeuxList where description does not contain UPDATED_DESCRIPTION
        defaultJeuxShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllJeuxesByCompetitionIsEqualToSomething() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);
        Competition competition = CompetitionResourceIT.createEntity(em);
        em.persist(competition);
        em.flush();
        jeux.setCompetition(competition);
        jeuxRepository.saveAndFlush(jeux);
        Long competitionId = competition.getId();

        // Get all the jeuxList where competition equals to competitionId
        defaultJeuxShouldBeFound("competitionId.equals=" + competitionId);

        // Get all the jeuxList where competition equals to competitionId + 1
        defaultJeuxShouldNotBeFound("competitionId.equals=" + (competitionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJeuxShouldBeFound(String filter) throws Exception {
        restJeuxMockMvc.perform(get("/api/jeuxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jeux.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].concepteur").value(hasItem(DEFAULT_CONCEPTEUR)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].meilleurScore").value(hasItem(DEFAULT_MEILLEUR_SCORE)))
            .andExpect(jsonPath("$.[*].lienJouer").value(hasItem(DEFAULT_LIEN_JOUER)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].setupFileContentType").value(hasItem(DEFAULT_SETUP_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].setupFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_SETUP_FILE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restJeuxMockMvc.perform(get("/api/jeuxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJeuxShouldNotBeFound(String filter) throws Exception {
        restJeuxMockMvc.perform(get("/api/jeuxes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJeuxMockMvc.perform(get("/api/jeuxes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingJeux() throws Exception {
        // Get the jeux
        restJeuxMockMvc.perform(get("/api/jeuxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJeux() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        int databaseSizeBeforeUpdate = jeuxRepository.findAll().size();

        // Update the jeux
        Jeux updatedJeux = jeuxRepository.findById(jeux.getId()).get();
        // Disconnect from session so that the updates on updatedJeux are not directly saved in db
        em.detach(updatedJeux);
        updatedJeux
            .nom(UPDATED_NOM)
            .dateCreation(UPDATED_DATE_CREATION)
            .concepteur(UPDATED_CONCEPTEUR)
            .prix(UPDATED_PRIX)
            .meilleurScore(UPDATED_MEILLEUR_SCORE)
            .lienJouer(UPDATED_LIEN_JOUER)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .setupFile(UPDATED_SETUP_FILE)
            .setupFileContentType(UPDATED_SETUP_FILE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION);
        JeuxDTO jeuxDTO = jeuxMapper.toDto(updatedJeux);

        restJeuxMockMvc.perform(put("/api/jeuxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jeuxDTO)))
            .andExpect(status().isOk());

        // Validate the Jeux in the database
        List<Jeux> jeuxList = jeuxRepository.findAll();
        assertThat(jeuxList).hasSize(databaseSizeBeforeUpdate);
        Jeux testJeux = jeuxList.get(jeuxList.size() - 1);
        assertThat(testJeux.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testJeux.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testJeux.getConcepteur()).isEqualTo(UPDATED_CONCEPTEUR);
        assertThat(testJeux.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testJeux.getMeilleurScore()).isEqualTo(UPDATED_MEILLEUR_SCORE);
        assertThat(testJeux.getLienJouer()).isEqualTo(UPDATED_LIEN_JOUER);
        assertThat(testJeux.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testJeux.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testJeux.getSetupFile()).isEqualTo(UPDATED_SETUP_FILE);
        assertThat(testJeux.getSetupFileContentType()).isEqualTo(UPDATED_SETUP_FILE_CONTENT_TYPE);
        assertThat(testJeux.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingJeux() throws Exception {
        int databaseSizeBeforeUpdate = jeuxRepository.findAll().size();

        // Create the Jeux
        JeuxDTO jeuxDTO = jeuxMapper.toDto(jeux);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJeuxMockMvc.perform(put("/api/jeuxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jeuxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jeux in the database
        List<Jeux> jeuxList = jeuxRepository.findAll();
        assertThat(jeuxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJeux() throws Exception {
        // Initialize the database
        jeuxRepository.saveAndFlush(jeux);

        int databaseSizeBeforeDelete = jeuxRepository.findAll().size();

        // Delete the jeux
        restJeuxMockMvc.perform(delete("/api/jeuxes/{id}", jeux.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jeux> jeuxList = jeuxRepository.findAll();
        assertThat(jeuxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
